package com.dycgb.office.common.model;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 流水总发货
 *
 * @author MY-HE
 * @date 2019-11-30 11:30
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "total_goods")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TotalGoods extends AbstractBaseEntity {

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
     * 录入凭证日期
     */
    @Column
    private String documentDate;

    /**
     * 发货客户
     */
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    /**
     * 产品
     */
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    /**
     * 产品数量
     */
    @Column(scale = 4, length = 12)
    private BigDecimal count;

    /**
     * 发货目的地
     */
    @Column
    private String address;

    /**
     * 凭证金额
     */
    @Column(scale = 2, precision = 20)
    private BigDecimal money;

    /**
     * 备注
     */
    @Column
    private String remark;

    /**
     * 凭证文件名称
     */
    @Column
    private String fileName;
}
