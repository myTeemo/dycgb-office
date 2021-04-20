package com.dycgb.office.common.service;

import com.dycgb.office.common.model.MaterialType;

import java.util.List;

public interface MaterialTypeService {
    MaterialType findMaterialTypeById(Long id);

    MaterialType createMaterialType(MaterialType materialType);

    List<MaterialType> createMaterialTypes(List<MaterialType> materialTypes);
}
