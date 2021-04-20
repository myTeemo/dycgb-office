package com.dycgb.office.common.service.impl;

import com.alibaba.excel.EasyExcel;
import com.dycgb.office.common.exception.ResourceNotFoundException;
import com.dycgb.office.common.model.Category;
import com.dycgb.office.common.model.Product;
import com.dycgb.office.common.model.TotalGoods;
import com.dycgb.office.common.model.User;
import com.dycgb.office.common.model.excel.ExcelTotalGoods;
import com.dycgb.office.common.repository.TotalGoodsRepository;
import com.dycgb.office.common.service.CategoryService;
import com.dycgb.office.common.service.ProductService;
import com.dycgb.office.common.service.TotalGoodsService;
import com.dycgb.office.common.service.UserService;
import com.dycgb.office.common.utils.ErrorCodeEnum;
import com.dycgb.office.common.utils.excel.TotalGoodsExcelListener;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 流水发货业务实现类
 * @Author myhe
 * @Date 2021/4/12 下午3:34
 */

@Service
public class TotalGoodsServiceImpl implements TotalGoodsService {
    @Resource
    private TotalGoodsRepository totalGoodsRepository;

    @Resource
    private UserService userService;
    @Resource
    private ProductService productService;
    @Resource
    private CategoryService categoryService;

    private final Map<String, User> usersMap = new HashMap<>();
    private final Map<String, Product> productsMap = new HashMap<>();
    private final Map<String, Category> categoriesMap = new HashMap<>();

    /**
     * 上传流水发货Excel文件
     *
     * @param file EXCEL
     */
    @Override
    public void excelUpload(MultipartFile file) throws IOException {
        if (file == null)
            throw new ResourceNotFoundException(ErrorCodeEnum.TOTAL_GOODS_EXCEL_UPLOAD_FAILED_FILE_NOT_FOUND);
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        if (!fileName.endsWith("xls") && !fileName.endsWith("xlsx")) {
            throw new ResourceNotFoundException(ErrorCodeEnum.TOTAL_GOODS_EXCEL_UPLOAD_FAILED_EXCEL_NOT_FOUND);
        }
        EasyExcel.read(file.getInputStream(), ExcelTotalGoods.class, new TotalGoodsExcelListener(this)).sheet().doRead();
    }

    /**
     * 根据流水发货Excel文件批量创建发货记录
     *
     * @param excelTotalGoodsList Excel解析出来的发货记录列表
     */
    @Override
    public List<TotalGoods> createTotalGoodsByExcel(List<ExcelTotalGoods> excelTotalGoodsList) {
        List<User> users = userService.findAllUsers();
        List<Product> products = productService.findAllProducts();
        List<Category> categories = categoryService.findAllCategories();

        for (User user : users) {
            usersMap.put(user.getName(), user);
        }

        for (Product product : products) {
            productsMap.put(product.getName(), product);
        }

        for (Category category : categories) {
            categoriesMap.put(category.getName(), category);
        }

        List<TotalGoods> totalGoodsList = new ArrayList<>();

        for (ExcelTotalGoods excelTotalGoods : excelTotalGoodsList) {
            TotalGoods totalGoods = excelTotalGoods.converter();
            String userName = totalGoods.getUser().getName();
            String productName = totalGoods.getProduct().getName();

            if (usersMap.containsKey(userName)) {
                totalGoods.setUser(usersMap.get(userName));
            } else {
                User user = totalGoods.getUser();
                if (!categoriesMap.containsKey(user.getCategory().getName())) {
                    Category createdCategory = categoryService.createCategory(user.getCategory());
                    categoriesMap.put(createdCategory.getName(), createdCategory);
                }
                user.setCategory(categoriesMap.get(user.getCategory().getName()));
                User createdUser = userService.createUser(user);
                usersMap.put(user.getName(), createdUser);
            }

            if (productsMap.containsKey(productName)) {
                totalGoods.setProduct(productsMap.get(productName));
            } else {
                Product product = productService.createProduct(totalGoods.getProduct());
                productsMap.put(product.getName(), product);
            }

            totalGoodsList.add(totalGoods);
        }
        usersMap.clear();
        productsMap.clear();
        categoriesMap.clear();

        return totalGoodsRepository.saveAll(totalGoodsList);
    }
}
