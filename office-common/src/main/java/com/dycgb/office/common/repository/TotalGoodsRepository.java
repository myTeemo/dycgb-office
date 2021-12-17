package com.dycgb.office.common.repository;

import com.dycgb.office.common.model.TotalGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

/**
 * 流水发货总账数据库访问类
 *
 * @author MY-HE
 * @date 2019-11-30 15:26
 */
public interface TotalGoodsRepository extends JpaRepository<TotalGoods, Long> {

    @Transactional
    @Modifying
    Integer deleteTotalGoodsById(Long id);
}
