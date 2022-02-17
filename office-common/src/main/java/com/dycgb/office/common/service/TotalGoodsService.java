package com.dycgb.office.common.service;

import com.dycgb.office.common.model.TotalGoods;
import com.dycgb.office.common.model.excel.ExcelTotalGoods;
import com.dycgb.office.common.model.vo.TotalGoodsVo;
import com.dycgb.office.common.utils.Pager;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TotalGoodsService {
    void excelUpload(MultipartFile file) throws IOException;

    List<TotalGoods> createTotalGoodsByExcel(List<ExcelTotalGoods> excelTotalGoodsList);

    Pager<TotalGoods> findTotalGoodsByPage(Integer page, Integer pageSize, TotalGoodsVo totalGoodsVo);

    TotalGoods findTotalGoodsById(Long id);

    TotalGoods updateGoods(TotalGoods totalGoods);

    TotalGoods imageUpload(MultipartFile file, Long id, String documentNo) throws IOException;

    boolean deleteTotalGoodsById(Long id);
}
