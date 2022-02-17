package com.dycgb.office.admin.controller;

import com.dycgb.office.common.exception.ParametersIllegalException;
import com.dycgb.office.common.exception.ResourceNotFoundException;
import com.dycgb.office.common.model.Category;
import com.dycgb.office.common.model.Product;
import com.dycgb.office.common.model.TotalGoods;
import com.dycgb.office.common.model.User;
import com.dycgb.office.common.model.vo.TotalGoodsVo;
import com.dycgb.office.common.service.CategoryService;
import com.dycgb.office.common.service.ProductService;
import com.dycgb.office.common.service.TotalGoodsService;
import com.dycgb.office.common.service.UserService;
import com.dycgb.office.common.utils.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Description 流水发货控制器
 * @Author myhe
 * @Date 2021/4/12 下午2:28
 */
@Controller
@RequestMapping("/goods")
@RequiredArgsConstructor
public class TotalGoodsController {
    final TotalGoodsService totalGoodsService;
    final ImageUtils imageUtils;
    final FileConstants fileConstants;
    final UserService userService;
    final CategoryService categoryService;
    final ProductService productService;

    @GetMapping("/page")
    public String page(Model model) {
        Optional<Category> category = categoryService.findCategoryByName("客户");
        if (category.isPresent()) {
            List<User> users = userService.findUsersByCategory(category.get());
            model.addAttribute("users", users);
        } else {
            model.addAttribute("users", new ArrayList<User>());
        }
        List<Product> products = productService.findAllProducts();
        model.addAttribute("products", products);

        return "total-goods";
    }

    /**
     * 分页查询流水发货信息
     *
     * @param page     第 page 页
     * @param pageSize 页大小
     */
    @PostMapping("/page")
    @ResponseBody
    public CustomResponse findTotalGoodsByPage(Integer page, Integer pageSize, TotalGoodsVo totalGoodsVo) {
        Pager<TotalGoods> totalGoodsPager = totalGoodsService.findTotalGoodsByPage(page, pageSize, totalGoodsVo);
        return CustomResponse.OK(ErrorCodeEnum.TOTAL_GOODS_QUERY_BY_PAGE_OK, totalGoodsPager);
    }


    @PostMapping("/excel/upload")
    @ResponseBody
    public CustomResponse excelUpload(@RequestParam("file") MultipartFile file) {
        try {
            totalGoodsService.excelUpload(file);
            return CustomResponse.OK(ErrorCodeEnum.TOTAL_GOODS_EXCEL_UPLOAD_OK);
        } catch (IOException ioe) {
            return CustomResponse.FAILED(ErrorCodeEnum.TOTAL_GOODS_EXCEL_UPLOAD_FAILED_IO_EXCEPTION);
        } catch (ResourceNotFoundException re) {
            return CustomResponse.FAILED(re.getCode(), re.getMessage());
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public CustomResponse findTotalGoodsById(@PathVariable("id") Long id) {
        try {
            TotalGoods totalGoods = totalGoodsService.findTotalGoodsById(id);
            return CustomResponse.OK(ErrorCodeEnum.TOTAL_GOODS_QUERY_OK, totalGoods);
        } catch (ResourceNotFoundException ntfe) {
            return CustomResponse.FAILED(ntfe.getCode(), ntfe.getMessage());
        }
    }

    /**
     * 获取发货单文件
     *
     * @param id       发货单ID
     * @param response HTTP响应
     * @throws IOException IO异常
     */
    @GetMapping("/file/{id}")
    public void getFile(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        TotalGoods totalGoods = totalGoodsService.findTotalGoodsById(id);
        String fileName = totalGoods.getFileName();
        if (fileName != null) {
            imageUtils.readImage(response.getOutputStream(), fileConstants.getGoodsPath() + fileName);
        }
    }

    @PostMapping("/img/upload")
    @ResponseBody
    public CustomResponse imageUpload(@RequestParam("img") MultipartFile img, @RequestParam("id") Long id, @RequestParam("documentNo") String documentNo) {
        return null;
    }

    @PutMapping
    @ResponseBody
    public CustomResponse updateGoods(TotalGoods totalGoods, MultipartFile file) {
        try {
            TotalGoods updatedTotalGoods = totalGoodsService.updateGoods(totalGoods);
            if (file != null) {
                try {
                    TotalGoods nTotalGoods = totalGoodsService.imageUpload(file, updatedTotalGoods.getId(), updatedTotalGoods.getDocumentNo());
                    updatedTotalGoods.setFileName(nTotalGoods.getFileName());
                } catch (IOException ioe) {
                    return CustomResponse.FAILED(ErrorCodeEnum.TOTAL_GOODS_IMG_UPLOAD_FAILED_IO_EXCEPTION);
                } catch (ParametersIllegalException pie) {
                    return CustomResponse.FAILED(pie.getCode(), pie.getMessage());
                }
            }
            return CustomResponse.OK(ErrorCodeEnum.TOTAL_GOODS_UPLOAD_OK);

        } catch (ResourceNotFoundException re) {
            return CustomResponse.FAILED(re.getCode(), re.getMessage());
        }
    }

    /**
     * 根据发货单ID删除发货单
     *
     * @param id 发货单ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public CustomResponse deleteGoodsById(@PathVariable("id") Long id) {
        try {
            totalGoodsService.deleteTotalGoodsById(id);
            return CustomResponse.OK(ErrorCodeEnum.TOTAL_GOODS_DELETE_OK);
        } catch (ResourceNotFoundException rnfe) {
            return CustomResponse.FAILED(rnfe.getCode(), rnfe.getMessage());
        }
    }
}
