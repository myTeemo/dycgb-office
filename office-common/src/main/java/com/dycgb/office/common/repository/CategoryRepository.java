package com.dycgb.office.common.repository;

import com.dycgb.office.common.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 类别仓库
 */
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findCategoryById(Long id);

    @Modifying
    @Transactional
    Integer deleteCategoryById(Long id);

    List<Category> findCategoryByNameLike(String name);
}
