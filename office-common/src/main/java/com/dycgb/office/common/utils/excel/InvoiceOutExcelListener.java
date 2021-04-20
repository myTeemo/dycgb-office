package com.dycgb.office.common.utils.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.dycgb.office.common.model.excel.ExcelInvoice;
import com.dycgb.office.common.service.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 对外开出发票Excel文件处理类
 * @Author myhe
 * @Date 2021/4/11 上午10:56
 */
public class InvoiceOutExcelListener extends AnalysisEventListener<ExcelInvoice> {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceOutExcelListener.class);

    private final InvoiceService invoiceService;
    private final List<ExcelInvoice> excelInvoiceList = new ArrayList<>();
    private static final int BATCH_COUNT = 2000;


    public InvoiceOutExcelListener(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Override
    public void invoke(ExcelInvoice excelInvoice, AnalysisContext analysisContext) {
        LOGGER.info("解析到一条数据：{}", excelInvoice.toString());
        excelInvoiceList.add(excelInvoice);
        if(excelInvoiceList.size()> BATCH_COUNT){
            invoiceService.createInvoices(excelInvoiceList);
            excelInvoiceList.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if(excelInvoiceList.size()>0){
            invoiceService.createInvoices(excelInvoiceList);
            excelInvoiceList.clear();
        }
        LOGGER.info("解析完成！");
    }
}
