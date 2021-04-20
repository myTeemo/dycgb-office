package com.dycgb.office.common.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @Description 发票实体类
 * @Author myhe
 * @Date 2021/4/10 下午9:35
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "invoice")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Invoice extends AbstractBaseEntity {
    /**
     * 开票日期
     */
    @Column
    private String date;
    /**
     * 发票号码
     */
    @Column
    private String number;
    /**
     * 购买方信息
     */
    @ManyToOne
    @JoinColumn(name = "buyer_id", referencedColumnName = "id")
    private Company buyer;
    /**
     * 销售方信息
     */
    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    private Company seller;
    /**
     * 发票报送状态
     */
    @Column
    private String status;
    /**
     * 发票所述年月
     */
    @Column
    private String yearAndMonth;
    /**
     * 除税金额
     */
    @Column
    private String moneyWithoutTax;
    /**
     * 税率
     */
    @Column
    private String taxRate;
    /**
     * 税额
     */
    @Column
    private String taxMoney;
    /**
     * 产品名称
     */
    @Column
    private String productName;
    /**
     * 工程名称
     */
    @Column
    private String projectName;
    /**
     * 工程地址
     */
    @Column
    private String projectAddress;

    /**
     * 发票所有人
     */
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    /**
     * 发票类别 1 开出 2 开出
     */
    @Column
    private byte type;

    /**
     * 含税金额
     */
    @Column
    private BigDecimal moneyWithRate;

    /**
     * 收取税点
     */
    @Column
    private BigDecimal mTaxRate;

    /**
     * 收取税金 计算方式为收取税点*发票含税金额
     */
    @Column
    private BigDecimal mTaxMoney;

    /**
     * 备注
     */
    @Column
    private String remark;
}
