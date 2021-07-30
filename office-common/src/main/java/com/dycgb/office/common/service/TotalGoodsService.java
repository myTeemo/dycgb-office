package com.dycgb.office.common.service;

import com.dycgb.office.common.model.TotalGoods;
import com.dycgb.office.common.model.excel.ExcelTotalGoods;
import com.dycgb.office.common.utils.Pager;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TotalGoodsService {
    void excelUpload(MultipartFile file) throws IOException;

    List<TotalGoods> createTotalGoodsByExcel(List<ExcelTotalGoods> excelTotalGoodsList);

    Pager<TotalGoods> findTotalGoodsByPage(Integer page, Integer pageSize);
}
