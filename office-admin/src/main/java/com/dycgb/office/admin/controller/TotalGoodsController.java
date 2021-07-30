package com.dycgb.office.admin.controller;

import com.dycgb.office.common.exception.ResourceNotFoundException;
import com.dycgb.office.common.model.TotalGoods;
import com.dycgb.office.common.service.TotalGoodsService;
import com.dycgb.office.common.utils.CustomResponse;
import com.dycgb.office.common.utils.ErrorCodeEnum;
import com.dycgb.office.common.utils.Pager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Description 流水发货控制器
 * @Author myhe
 * @Date 2021/4/12 下午2:28
 */
@Controller
@RequestMapping("/goods")
public class TotalGoodsController {
    @Resource
    private TotalGoodsService totalGoodsService;

    @GetMapping("/page")
    public String page() {
        return "total-goods";
    }

    /**
     * 分页查询流水发货信息
     *
     * @param page     第 page 页
     * @param pageSize 页大小
     */
    @GetMapping
    @ResponseBody
    public CustomResponse findTotalGoodsByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                               @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
        Pager<TotalGoods> totalGoodsPager = totalGoodsService.findTotalGoodsByPage(page, pageSize);
        return CustomResponse.OK(ErrorCodeEnum.TOTAL_GOODS_QUERY_BY_PAGE_OK, totalGoodsPager);
    }


    @PostMapping("/upload")
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
}
