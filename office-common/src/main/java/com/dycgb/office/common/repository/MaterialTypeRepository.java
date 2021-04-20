package com.dycgb.office.common.repository;

import com.dycgb.office.common.model.MaterialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaterialTypeRepository extends JpaRepository<MaterialType, Long> {
    Optional<MaterialType> findMaterialTypeById(Long id);
}
