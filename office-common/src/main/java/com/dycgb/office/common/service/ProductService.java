package com.dycgb.office.common.service;

import com.dycgb.office.common.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAllProducts();

    Product createProduct(Product product);
}
