package com.dycgb.office.common.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Description 企业生产的产品类
 * @Author myhe
 * @Date 2021/4/12 下午3:24
 */

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends AbstractBaseEntity {
    /**
     * 产品名称
     */
    @Column
    private String name;

    /**
     * 单位
     */
    @Column
    private String unit;

    /**
     * 规格
     */
    @Column
    private String specification;
}
