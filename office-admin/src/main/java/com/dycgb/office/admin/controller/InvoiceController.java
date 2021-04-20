package com.dycgb.office.admin.controller;

import com.dycgb.office.common.exception.ResourceNotFoundException;
import com.dycgb.office.common.service.InvoiceService;
import com.dycgb.office.common.utils.CustomResponse;
import com.dycgb.office.common.utils.ErrorCodeEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Description 流水发票控制器
 * @Author myhe
 * @Date 2021/4/10 下午9:34
 */
@Controller
@RequestMapping("/invoice")
public class InvoiceController {
    @Resource
    private InvoiceService invoiceService;

    @PostMapping("/out/upload")
    @ResponseBody
    public CustomResponse outExcelUpload(@RequestParam("file") MultipartFile file) {
        try {
            invoiceService.outExcelUpload(file);
            return CustomResponse.OK(ErrorCodeEnum.INVOICE_OUT_EXCEL_UPLOAD_OK);
        } catch (IOException ioe) {
            return CustomResponse.FAILED(ErrorCodeEnum.INVOICE_OUT_EXCEL_UPLOAD_FAILED_IO_EXCEPTION);
        } catch (ResourceNotFoundException re){
            return CustomResponse.FAILED(re.getCode(),re.getMessage());
        }
    }
}
