package com.dycgb.office.common.service.impl;

import com.dycgb.office.common.model.Product;
import com.dycgb.office.common.repository.ProductRepository;
import com.dycgb.office.common.service.ProductService;
import com.dycgb.office.common.utils.SortUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 产品业务实现类
 * @Author myhe
 * @Date 2021/4/12 下午3:33
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductRepository productRepository;

    @Override
    public List<Product> findAllProducts() {
        List<Product> products = productRepository.findAll();
        return SortUtils.streamSortByChinese(products, "name");
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
}
