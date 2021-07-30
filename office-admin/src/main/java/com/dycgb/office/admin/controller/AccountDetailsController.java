package com.dycgb.office.admin.controller;

import com.dycgb.office.common.exception.ParametersIllegalException;
import com.dycgb.office.common.exception.ResourceAlreadyExistException;
import com.dycgb.office.common.exception.ResourceNotFoundException;
import com.dycgb.office.common.model.AccountDetails;
import com.dycgb.office.common.model.PaymentType;
import com.dycgb.office.common.service.AccountDetailsService;
import com.dycgb.office.common.service.PaymentTypeService;
import com.dycgb.office.common.utils.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description 账户流水明细管理控制器
 * @Author myhe
 * @Date 2021/4/6 下午5:59
 */
@Controller
@RequestMapping("/details")
public class AccountDetailsController {
    @Resource
    private AccountDetailsService accountDetailsService;
    @Resource
    private PaymentTypeService paymentTypeService;

    @Resource
    private PageConstants pageConstants;

    @Resource
    private FileConstants fileConstants;

    @Resource
    private ImageUtils imageUtils;

    /**
     * 流水明细查询页面
     */
    @GetMapping("/page")
    public String page(@RequestParam("pid") Long paymentTypeId, Model model) {

        if (paymentTypeId == 0) {
            model.addAttribute("paymentType", new PaymentType(paymentTypeId, "流水总账"));
        } else {
            PaymentType paymentType = paymentTypeService.findPaymentTypeById(paymentTypeId);
            model.addAttribute("paymentType", paymentType);
        }
        return "account-details";
    }

    /**
     * 根据ID查询账户流水明细
     *
     * @param id 流水明细ID
     */
    @GetMapping("/{id}")
    @ResponseBody
    public CustomResponse findAccountDetailsById(@PathVariable("id") Long id) {
        try {
            AccountDetails accountDetails = accountDetailsService.findAccountDetailsById(id);
            return CustomResponse.OK(ErrorCodeEnum.ACCOUNT_DETAILS_QUERY_OK, accountDetails);
        } catch (ResourceNotFoundException re) {
            return CustomResponse.FAILED(re.getCode(), re.getMessage());
        }
    }

    /**
     * 分页查询账户流水明细
     *
     * @param page     第 page 页
     * @param pageSize 每页数据大小
     */
    @GetMapping
    @ResponseBody
    public CustomResponse findAccountDetailsByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                   @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                                                   @RequestParam(value = "payId", defaultValue = "0") Long paymentTypeId) {
        if (pageSize == null) {
            pageSize = pageConstants.getPageSize();
        }

        Pager<AccountDetails> accountDetailsList = accountDetailsService.findAccountDetailsByPage(page, pageSize, paymentTypeId);
        return CustomResponse.OK(ErrorCodeEnum.ACCOUNT_DETAILS_QUERY_OK, accountDetailsList);
    }

    /**
     * 新增账户明细
     *
     * @param accountDetails 账户明细
     * @return
     */
    @PostMapping
    @ResponseBody
    public CustomResponse createAccountDetails(AccountDetails accountDetails) {
        try {
            AccountDetails createdAccountDetails = accountDetailsService.createAccountDetails(accountDetails);
            return CustomResponse.OK(ErrorCodeEnum.ACCOUNT_DETAILS_CREATE_OK, createdAccountDetails);
        } catch (ResourceAlreadyExistException re) {
            return CustomResponse.FAILED(re.getCode(), re.getMessage());
        }
    }

    /**
     * 更新账户明细
     *
     * @param accountDetails 账户明细
     */
    @PutMapping
    @ResponseBody
    public CustomResponse updateAccountDetails(AccountDetails accountDetails, MultipartFile file) {

        try {
            // 更新除 文件外其他信息
            AccountDetails updatedAccountDetails = accountDetailsService.updateAccountDetails(accountDetails);
            if (file != null) {
                try {
                    // 更新凭证图片
                    AccountDetails nAccountDetails = accountDetailsService.imageUpload(file, updatedAccountDetails.getId(), updatedAccountDetails.getDocumentNo());

                    updatedAccountDetails.setFileName(nAccountDetails.getFileName());
                } catch (IOException ioe) {
                    return CustomResponse.FAILED(ErrorCodeEnum.ACCOUNT_DETAILS_IMG_UPLOAD_FAILED_IO_EXCEPTION);
                } catch (ParametersIllegalException pie) {
                    return CustomResponse.FAILED(pie.getCode(), pie.getMessage());
                }
            }
            return CustomResponse.OK(ErrorCodeEnum.ACCOUNT_DETAILS_UPDATE_OK, updatedAccountDetails);
        } catch (ResourceNotFoundException re) {
            return CustomResponse.FAILED(re.getCode(), re.getMessage());
        }
    }

    /**
     * 根据ID删除账户明细
     *
     * @param id 账户明细ID
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public CustomResponse deleteAccountDetailsById(@PathVariable("id") Long id) {
        try {
            accountDetailsService.deleteAccountDetailsById(id);
            return CustomResponse.OK(ErrorCodeEnum.ACCOUNT_DETAILS_DELETE_OK);
        } catch (ResourceNotFoundException re) {
            return CustomResponse.FAILED(re.getCode(), re.getMessage());
        }
    }

    /**
     * 解析 流水总账Excel
     *
     * @param file excel文件
     */
    @PostMapping("/excel/upload")
    @ResponseBody
    public CustomResponse excelUpload(@RequestParam("file") MultipartFile file) {
        try {
            accountDetailsService.readAccountDetailsExcel(file);
            return CustomResponse.OK(ErrorCodeEnum.ACCOUNT_DETAILS_EXCEL_UPLOAD_OK);
        } catch (IOException ioe) {
            return CustomResponse.FAILED(ErrorCodeEnum.ACCOUNT_DETAILS_EXCEL_UPLOAD_FAILED_IO_EXCEPTION);
        } catch (ResourceNotFoundException re) {
            return CustomResponse.FAILED(re.getCode(), re.getMessage());
        }
    }

    /**
     * 上传凭证文件
     *
     * @param img        图片
     * @param id         记录ID
     * @param documentNo 凭证编号
     */
    @PostMapping("/img/upload")
    @ResponseBody
    public CustomResponse imageUpload(@RequestParam("img") MultipartFile img,
                                      @RequestParam("id") Long id,
                                      @RequestParam("documentNo") String documentNo) {
        try {
            AccountDetails accountDetails = accountDetailsService.imageUpload(img, id, documentNo);
            return CustomResponse.OK(ErrorCodeEnum.ACCOUNT_DETAILS_IMG_UPLOAD_OK, accountDetails.getFileName());
        } catch (IOException ioe) {
            return CustomResponse.FAILED(ErrorCodeEnum.ACCOUNT_DETAILS_IMG_UPLOAD_FAILED_IO_EXCEPTION);
        } catch (ParametersIllegalException pie) {
            return CustomResponse.FAILED(pie.getCode(), pie.getMessage());
        }
    }

    @GetMapping("/file/{id}")
    public void getFile(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        AccountDetails accountDetails = accountDetailsService.findAccountDetailsById(id);
        String fileName = accountDetails.getFileName();

        if (fileName != null) {
            imageUtils.readImage(response.getOutputStream(), fileConstants.getAccountDetailsPath() + fileName);
        }
    }
}
