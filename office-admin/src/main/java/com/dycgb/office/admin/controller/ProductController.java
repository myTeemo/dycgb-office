package com.dycgb.office.admin.controller;

import com.dycgb.office.common.model.Product;
import com.dycgb.office.common.service.ProductService;
import com.dycgb.office.common.utils.CustomResponse;
import com.dycgb.office.common.utils.ErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description 产品控制器
 * @Date 2021-12-10 10:39
 * @Created by myhe
 */
@Controller
@RequestMapping
public class ProductController {
    public final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    @ResponseBody
    public CustomResponse findAllProducts() {
        List<Product> allProducts = productService.findAllProducts();
        return CustomResponse.OK(ErrorCodeEnum.PRODUCT_QUERY_OK, allProducts);
    }
}
