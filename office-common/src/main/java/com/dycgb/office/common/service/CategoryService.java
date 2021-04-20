package com.dycgb.office.common.service;

import com.dycgb.office.common.model.Category;
import org.springframework.lang.NonNull;

import java.util.List;

public interface CategoryService {
    Category updateCategory(Category category);

    Category createCategory(Category category);

    List<Category> findAllCategories();

    Category findCategoryById(@NonNull Long id);

    boolean deleteCategoryById(Long id);
}
