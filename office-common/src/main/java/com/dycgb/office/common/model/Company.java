package com.dycgb.office.common.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Description 发票企业信息
 * @Author myhe
 * @Date 2021/4/10 下午9:48
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "company")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company extends AbstractBaseEntity {
    /**
     * 购方或销售方名称
     */
    @Column
    private String name;
    /**
     * 购方或销售方税号
     */
    @Column
    private String taxCode;
    /**
     * 购方或销售方地址
     */
    @Column
    private String address;
    /**
     * 购方或销售方电话
     */
    @Column
    private String tel;
    /**
     * 购方或销售方银行名称
     */
    @Column
    private String bankName;
    /**
     * 购方或销售方银行账户
     */
    @Column
    private String bankAccount;
}
