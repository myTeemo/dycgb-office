package com.dycgb.office.common.service;

import com.dycgb.office.common.model.Invoice;
import com.dycgb.office.common.model.excel.ExcelInvoice;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface InvoiceService {
    void outExcelUpload(MultipartFile file) throws IOException;

    List<Invoice> createInvoices(List<ExcelInvoice> excelInvoiceList);
}
