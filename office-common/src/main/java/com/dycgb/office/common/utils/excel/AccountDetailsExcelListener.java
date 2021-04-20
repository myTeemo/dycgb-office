package com.dycgb.office.common.utils.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.dycgb.office.common.model.excel.ExcelAccountDetails;
import com.dycgb.office.common.service.AccountDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 苏州农商行（公账）流水明细EXCEL监听器
 * @Author myhe
 * @Date 2021/4/8 上午11:43
 */
public class AccountDetailsExcelListener extends AnalysisEventListener<ExcelAccountDetails> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountDetailsExcelListener.class);

    private final AccountDetailsService accountDetailsService;
    private final List<ExcelAccountDetails> excelAccountDetailsList = new ArrayList<>();
    private static final int BATCH_COUNT = 2000;

    public AccountDetailsExcelListener(AccountDetailsService accountDetailsService) {
        this.accountDetailsService = accountDetailsService;
    }


    /**
     * 对于每一条数据都会调用此方法
     *
     * @param excelAccountDetails EXCEL中每一行数据
     */
    @Override
    public void invoke(ExcelAccountDetails excelAccountDetails, AnalysisContext analysisContext) {
        LOGGER.info("解析到一条数据：{}", excelAccountDetails.toString());
        excelAccountDetailsList.add(excelAccountDetails);
        if (excelAccountDetailsList.size() >= BATCH_COUNT) {
            accountDetailsService.createAccountDetails(excelAccountDetailsList);
            excelAccountDetailsList.clear();
        }
    }

    /**
     * 所有数据解析完毕最终调用的方法
     *
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (excelAccountDetailsList.size() > 0) {
            accountDetailsService.createAccountDetails(excelAccountDetailsList);
            excelAccountDetailsList.clear();
        }
        LOGGER.info("所有数据解析完成！");
    }
}
