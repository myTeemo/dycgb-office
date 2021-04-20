package com.dycgb.office.common.service.impl;

import com.alibaba.excel.EasyExcel;
import com.dycgb.office.common.exception.ResourceNotFoundException;
import com.dycgb.office.common.model.Category;
import com.dycgb.office.common.model.Company;
import com.dycgb.office.common.model.Invoice;
import com.dycgb.office.common.model.User;
import com.dycgb.office.common.model.excel.ExcelInvoice;
import com.dycgb.office.common.repository.InvoiceRepository;
import com.dycgb.office.common.service.CategoryService;
import com.dycgb.office.common.service.CompanyService;
import com.dycgb.office.common.service.InvoiceService;
import com.dycgb.office.common.service.UserService;
import com.dycgb.office.common.utils.ErrorCodeEnum;
import com.dycgb.office.common.utils.excel.InvoiceOutExcelListener;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 发票管理实现类
 * @Author myhe
 * @Date 2021/4/11 上午11:00
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Resource
    private InvoiceRepository invoiceRepository;
    @Resource
    private UserService userService;
    @Resource
    private CompanyService companyService;
    @Resource
    private CategoryService categoryService;

    private final Map<String, User> usersMap = new HashMap<>();
    private final Map<String, Category> categoriesMap = new HashMap<>();
    private final Map<String, Company> companiesMap = new HashMap<>();


    /**
     * 对外开出发票 Excel 文件上传
     *
     * @param file Excel文件
     */
    @Override
    public void outExcelUpload(MultipartFile file) throws IOException {
        if (file == null)
            throw new ResourceNotFoundException(ErrorCodeEnum.INVOICE_OUT_EXCEL_UPLOAD_FAILED_FILE_NOT_FOUND);
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        if (!fileName.endsWith("xls") && !fileName.endsWith("xlsx")) {
            throw new ResourceNotFoundException(ErrorCodeEnum.INVOICE_OUT_EXCEL_UPLOAD_FAILED_EXCEL_NOT_FOUND);
        }
        EasyExcel.read(file.getInputStream(), ExcelInvoice.class, new InvoiceOutExcelListener(this)).sheet().doRead();
    }

    /**
     * 根据Excel内容批量创建 对外开出发票记录
     *
     * @param excelInvoiceList 开出发票记录
     */
    @Override
    public List<Invoice> createInvoices(List<ExcelInvoice> excelInvoiceList) {
        List<User> users = userService.findAllUsers();
        List<Company> companies = companyService.findAllCompanies();
        for (User user : users) {
            usersMap.put(user.getName(), user);
        }

        for (Company company : companies) {
            companiesMap.put(company.getName(), company);
        }
        List<Invoice> invoices = new ArrayList<>();
        for (ExcelInvoice excelInvoice : excelInvoiceList) {
            Invoice invoice = excelInvoice.converter();
            String userName = invoice.getUser().getName();
            String buyerName = invoice.getBuyer().getName();
            String sellerName = invoice.getSeller().getName();

            if (usersMap.containsKey(userName)) {
                invoice.setUser(usersMap.get(userName));
            } else {
                User user = invoice.getUser();
                if (!categoriesMap.containsKey(user.getCategory().getName())) {
                    Category createdCategory = categoryService.createCategory(user.getCategory());
                    categoriesMap.put(createdCategory.getName(), createdCategory);
                }
                user.setCategory(categoriesMap.get(user.getCategory().getName()));
                User createdUser = userService.createUser(user);
                usersMap.put(user.getName(), createdUser);
            }

            if (companiesMap.containsKey(buyerName)) {
                invoice.setBuyer(companiesMap.get(buyerName));
            } else {
                Company buyer = companyService.createCompany(invoice.getBuyer());
                companiesMap.put(buyer.getName(), buyer);
            }

            if (companiesMap.containsKey(sellerName)) {
                invoice.setSeller(companiesMap.get(sellerName));
            } else {
                Company seller = companyService.createCompany(invoice.getSeller());
                companiesMap.put(seller.getName(), seller);
            }
            invoices.add(invoice);
        }

        usersMap.clear();
        companiesMap.clear();
        categoriesMap.clear();

        return invoiceRepository.saveAll(invoices);
    }
}
