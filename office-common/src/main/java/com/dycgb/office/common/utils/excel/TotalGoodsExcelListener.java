package com.dycgb.office.common.utils.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.dycgb.office.common.model.excel.ExcelTotalGoods;
import com.dycgb.office.common.service.TotalGoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 流水发货 Excel文件处理类
 * @Author myhe
 * @Date 2021/4/12 下午3:54
 */
public class TotalGoodsExcelListener extends AnalysisEventListener<ExcelTotalGoods> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TotalGoodsExcelListener.class);

    private final TotalGoodsService totalGoodsService;
    private final List<ExcelTotalGoods> excelTotalGoodsList = new ArrayList<>();
    private static final int BATCH_COUNT = 2000;

    public TotalGoodsExcelListener(TotalGoodsService totalGoodsService) {
        this.totalGoodsService = totalGoodsService;
    }

    @Override
    public void invoke(ExcelTotalGoods excelTotalGoods, AnalysisContext analysisContext) {
        LOGGER.info("解析到一条数据：{}", excelTotalGoods.toString());
        excelTotalGoodsList.add(excelTotalGoods);
        if (excelTotalGoodsList.size() >= BATCH_COUNT) {
            totalGoodsService.createTotalGoodsByExcel(excelTotalGoodsList);
            excelTotalGoodsList.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (excelTotalGoodsList.size() > 0) {
            totalGoodsService.createTotalGoodsByExcel(excelTotalGoodsList);
            excelTotalGoodsList.clear();
        }
        LOGGER.info("解析完成！");
    }
}
