package com.dycgb.office.admin.controller;

import com.dycgb.office.common.exception.ResourceNotFoundException;
import com.dycgb.office.common.model.MaterialType;
import com.dycgb.office.common.service.MaterialTypeService;
import com.dycgb.office.common.utils.CustomResponse;
import com.dycgb.office.common.utils.ErrorCodeEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description 材料类别控制器
 * @Author myhe
 * @Date 2021/4/14 上午10:27
 */
@Controller
@RequestMapping("/material/type")
public class MaterialTypeController {
    @Resource
    private MaterialTypeService materialTypeService;

    /**
     * 创建一条材料类别
     *
     * @param materialType 材料类别
     */
    @PostMapping
    @ResponseBody
    public CustomResponse createMaterialType(MaterialType materialType) {
        MaterialType createdMaterialType = materialTypeService.createMaterialType(materialType);
        return CustomResponse.OK(ErrorCodeEnum.MATERIAL_TYPE_CREATE_OK, createdMaterialType);
    }

    /**
     * 根据ID查询材料类别
     *
     * @param id 材料类别ID
     */
    @GetMapping("/{id}")
    @ResponseBody
    public CustomResponse findMaterialTypeById(@PathVariable("id") Long id) {
        try {
            MaterialType materialType = materialTypeService.findMaterialTypeById(id);
            return CustomResponse.OK(ErrorCodeEnum.MATERIAL_TYPE_QUERY_OK, materialType);
        } catch (ResourceNotFoundException re) {
            return CustomResponse.FAILED(re.getCode(), re.getMessage());
        }
    }

    /**
     * 批量创建材料类别
     *
     * @param materialTypes 材料类别列表
     */
    @PostMapping("/list")
    @ResponseBody
    public CustomResponse createMaterialTypes(@RequestBody List<MaterialType> materialTypes) {
        List<MaterialType> createdMaterialTypes = materialTypeService.createMaterialTypes(materialTypes);
        return CustomResponse.OK(ErrorCodeEnum.MATERIAL_TYPE_CREATE_OK, createdMaterialTypes);
    }
}
