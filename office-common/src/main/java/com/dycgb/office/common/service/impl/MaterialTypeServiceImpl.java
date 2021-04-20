package com.dycgb.office.common.service.impl;

import com.dycgb.office.common.exception.ResourceNotFoundException;
import com.dycgb.office.common.model.MaterialType;
import com.dycgb.office.common.repository.MaterialTypeRepository;
import com.dycgb.office.common.service.MaterialTypeService;
import com.dycgb.office.common.utils.ErrorCodeEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @Description 材料类别实现类
 * @Author myhe
 * @Date 2021/4/13 上午10:39
 */
@Service
public class MaterialTypeServiceImpl implements MaterialTypeService {
    @Resource
    private MaterialTypeRepository materialTypeRepository;

    /**
     * 根据 ID 查询材料类别
     *
     * @param id 类别ID
     */
    @Override
    public MaterialType findMaterialTypeById(Long id) {
        Optional<MaterialType> oMT = materialTypeRepository.findMaterialTypeById(id);
        if (oMT.isPresent()) {
            return oMT.get();
        }
        throw new ResourceNotFoundException(ErrorCodeEnum.MATERIAL_TYPE_QUERY_FAILED);
    }

    /**
     * 创建一条材料类别
     *
     * @param materialType 材料类别
     */
    @Override
    public MaterialType createMaterialType(MaterialType materialType) {
        return materialTypeRepository.save(materialType);
    }

    /**
     * 批量创建材料类别
     *
     * @param materialTypes 材料类别列表
     */
    @Override
    public List<MaterialType> createMaterialTypes(List<MaterialType> materialTypes) {
        return materialTypeRepository.saveAll(materialTypes);
    }
}
