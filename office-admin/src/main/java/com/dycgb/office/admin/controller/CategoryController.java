package com.dycgb.office.admin.controller;

import com.dycgb.office.common.exception.ResourceAlreadyExistException;
import com.dycgb.office.common.exception.ResourceNotFoundException;
import com.dycgb.office.common.model.Category;
import com.dycgb.office.common.service.CategoryService;
import com.dycgb.office.common.utils.CustomResponse;
import com.dycgb.office.common.utils.ErrorCodeEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 类别控制器
 * @Author myhe
 * @Date 2021/4/2 下午4:47
 */
@Controller
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    /**
     * 根据ID查询类别信息
     *
     * @param id 类别ID
     */
    @GetMapping("/{id}")
    @ResponseBody
    public CustomResponse findCategoryById(@PathVariable("id") Long id) {

        try {
            Category category = categoryService.findCategoryById(id);
            return CustomResponse.OK(ErrorCodeEnum.CATEGORY_QUERY_OK.getCode(), ErrorCodeEnum.CATEGORY_QUERY_OK.getMessage(), category);
        } catch (ResourceNotFoundException e) {
            return CustomResponse.FAILED(e.getCode(), e.getMessage());
        }
    }

    /**
     * 查询所有类别信息
     */
    @GetMapping
    @ResponseBody
    public CustomResponse findCategories() {
        List<Category> categories = categoryService.findAllCategories();
        return CustomResponse.OK(ErrorCodeEnum.CATEGORY_QUERY_OK.getCode(), ErrorCodeEnum.CATEGORY_QUERY_OK.getMessage(), categories);
    }

    /**
     * 新增一个类别
     */
    @PostMapping
    @ResponseBody
    public CustomResponse createCategory(Category category) {
        try {
            Category savedCategory = categoryService.createCategory(category);
            return CustomResponse.OK(ErrorCodeEnum.CATEGORY_CREATE_OK.getCode(), ErrorCodeEnum.CATEGORY_CREATE_OK.getMessage(), savedCategory);
        } catch (ResourceAlreadyExistException e) {
            return CustomResponse.FAILED(e.getCode(), e.getMessage());
        }
    }

    /**
     * 更新类别
     */
    @PutMapping
    @ResponseBody
    public CustomResponse updateCategory(Category category) {
        try {
            Category updateCategory = categoryService.updateCategory(category);
            return CustomResponse.OK(ErrorCodeEnum.CATEGORY_UPDATE_OK.getCode(), ErrorCodeEnum.CATEGORY_UPDATE_OK.getMessage(), updateCategory);
        } catch (NullPointerException ne) {
            return CustomResponse.FAILED(ne.getMessage());
        } catch (ResourceNotFoundException | ResourceAlreadyExistException re) {
            return CustomResponse.FAILED(re.getCode(), re.getMessage());
        }
    }

    /**
     * 根据ID删除类别
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public CustomResponse deleteCategoryById(@PathVariable("id") Long id) {
        try {
            categoryService.deleteCategoryById(id);
            return CustomResponse.OK(ErrorCodeEnum.CATEGORY_DELETE_OK.getCode(), ErrorCodeEnum.CATEGORY_DELETE_OK.getMessage());
        } catch (ResourceNotFoundException re) {
            return CustomResponse.FAILED(re.getCode(), re.getMessage());
        }
    }
}
