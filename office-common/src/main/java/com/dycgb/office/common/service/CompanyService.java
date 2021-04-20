package com.dycgb.office.common.service;

import com.dycgb.office.common.model.Company;

import java.util.List;

/**
 * @Description 发票企业业务接口
 * @Author myhe
 * @Date 2021/4/12 上午9:11
 */
public interface CompanyService {
    List<Company> findAllCompanies();

    Company createCompany(Company buyer);
}
