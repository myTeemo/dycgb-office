package com.dycgb.office.admin.controller;

import com.dycgb.office.common.exception.ResourceNotFoundException;
import com.dycgb.office.common.model.MaterialOverview;
import com.dycgb.office.common.service.MaterialOverviewService;
import com.dycgb.office.common.utils.CustomResponse;
import com.dycgb.office.common.utils.ErrorCodeEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Description 材料单总览
 * @Author myhe
 * @Date 2021/4/13 上午10:42
 */
@Controller
@RequestMapping("/material/overview")
public class MaterialOverviewController {
    @Resource
    private MaterialOverviewService materialOverviewService;

    /**
     * 创建一条材料单
     *
     * @param materialOverview 材料单概览
     */
    @PostMapping
    @ResponseBody
    public CustomResponse createMaterial(@RequestBody MaterialOverview materialOverview) {
        try {
            MaterialOverview createdMaterialOverview = materialOverviewService.createMaterial(materialOverview);
            return CustomResponse.OK(ErrorCodeEnum.MATERIAL_OVERVIEW_CREATE_OK, createdMaterialOverview);
        } catch (ResourceNotFoundException re) {
            return CustomResponse.FAILED(re.getCode(), re.getMessage());
        }
    }
}
