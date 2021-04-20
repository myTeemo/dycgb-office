package com.dycgb.office.common.service.impl;

import com.dycgb.office.common.model.Company;
import com.dycgb.office.common.repository.CompanyRepository;
import com.dycgb.office.common.service.CompanyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 发票企业业务实现类
 * @Author myhe
 * @Date 2021/4/12 上午9:11
 */
@Service
public class CompanyServiceImpl implements CompanyService {
    @Resource
    private CompanyRepository companyRepository;
    @Override
    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }
}
