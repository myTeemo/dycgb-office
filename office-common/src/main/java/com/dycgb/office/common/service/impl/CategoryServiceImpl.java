package com.dycgb.office.common.service.impl;

import com.dycgb.office.common.exception.ResourceAlreadyExistException;
import com.dycgb.office.common.exception.ResourceNotFoundException;
import com.dycgb.office.common.model.Category;
import com.dycgb.office.common.repository.CategoryRepository;
import com.dycgb.office.common.service.CategoryService;
import com.dycgb.office.common.utils.ErrorCodeEnum;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @Description 类别处理实现类
 * @Author myhe
 * @Date 2021/3/29 下午6:43
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryRepository categoryRepository;

    /**
     * 更新类别信息
     *
     * @param category
     */
    @Override
    public Category updateCategory(Category category) {
        if (category.getId() == null) {
            throw new NullPointerException(ErrorCodeEnum.CATEGORY_UPDATE_FAILED_ID_NULL.getMessage());
        }
        Optional<Category> oldCategory = categoryRepository.findCategoryById(category.getId());

        if (oldCategory.isPresent()) {
            List<Category> categories = categoryRepository.findCategoryByNameLike("%" + category.getName() + "%");
            if (categories.size() > 0) {
                throw new ResourceAlreadyExistException(ErrorCodeEnum.CATEGORY_UPDATE_FAILED_ALREADY_EXIST.getCode(), ErrorCodeEnum.CATEGORY_UPDATE_FAILED_ALREADY_EXIST.getMessage());
            }
            return categoryRepository.save(category);
        }
        throw new ResourceNotFoundException(ErrorCodeEnum.CATEGORY_UPDATE_FAILED_NOT_FOUND.getCode(), ErrorCodeEnum.CATEGORY_UPDATE_FAILED_NOT_FOUND.getMessage());
    }

    /**
     * 新增一个类别
     *
     * @param category 类别信息
     */
    @Override
    public Category createCategory(Category category) {
        assert category.getName() != null;

        List<Category> categories = categoryRepository.findCategoryByNameLike("%" + category.getName() + "%");
        if (categories.size() > 0) {
            throw new ResourceAlreadyExistException(ErrorCodeEnum.CATEGORY_CREATE_FAILED_ALREADY_EXIST.getCode(), ErrorCodeEnum.CATEGORY_CREATE_FAILED_ALREADY_EXIST.getMessage());
        }
        return categoryRepository.save(category);
    }

    /**
     * 查找所有类别信息
     *
     * @return 类别列表
     */
    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * 根据ID查询类别
     *
     * @param id 类别ID
     * @return 类别信息
     */
    @Override
    public Category findCategoryById(@NonNull Long id) {
        Optional<Category> categoryById = categoryRepository.findCategoryById(id);
        if (categoryById.isPresent()) {
            return categoryById.get();
        }
        throw new ResourceNotFoundException(ErrorCodeEnum.CATEGORY_QUERY_FAILED_NOT_FOUND.getCode(), ErrorCodeEnum.CATEGORY_QUERY_FAILED_NOT_FOUND.getMessage());
    }

    /**
     * 根据ID删除类别信息
     *
     * @param id 类别ID
     * @return 删除的类别
     */
    @Override
    public boolean deleteCategoryById(@NonNull Long id) {
        if (categoryRepository.deleteCategoryById(id) > 0) {
            return true;
        }
        throw new ResourceNotFoundException(ErrorCodeEnum.CATEGORY_DELETE_FAILED_NOT_FOUND.getCode(), ErrorCodeEnum.CATEGORY_DELETE_FAILED_NOT_FOUND.getMessage());
    }
}
