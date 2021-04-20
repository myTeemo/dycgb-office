package com.dycgb.office.common.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @Description 账户流水明细
 * @Author myhe
 * @Date 2021/3/28 下午3:10
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "account_details")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetails extends AbstractBaseEntity {

    /**
     * 凭证所在本数序号
     */
    @Column
    private String documentSeq;

    /**
     * 录入凭证编号
     */
    @Column
    private String documentNo;

    /**
     * 交易日期
     */
    @Column
    private String documentDate;

    /**
     * 分类
     */
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    /**
     * 凭证为何人ID
     */
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    /**
     * 凭证内容
     */
    @Column(nullable = false)
    private String content;

    /**
     * 收入
     */
    @Column(scale = 2, length = 15)
    private BigDecimal income;

    /**
     * 支出
     */
    @Column(scale = 2, precision = 20)
    private BigDecimal expense;

    /**
     * 对方账户名称
     */
    @Column
    private String counterpartyInformation;

    /**
     * 付款方式
     */
    @ManyToOne
    @JoinColumn(name = "paymentType_id", referencedColumnName = "id")
    private PaymentType paymentType;

    /**
     * 备注
     */
    @Column
    private String remark;

    /**
     * 凭证文件路径
     */
    @Column
    private String filePath;
}
