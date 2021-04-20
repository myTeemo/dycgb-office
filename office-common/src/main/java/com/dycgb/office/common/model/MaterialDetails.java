package com.dycgb.office.common.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @Description 材料明细实体类
 * @Author myhe
 * @Date 2021/4/13 上午10:09
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "material_details")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialDetails extends AbstractBaseEntity {

    /**
     * 材料明细对应的材料单
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_overview_id", referencedColumnName = "id")
    private MaterialOverview materialOverview;

    /**
     * 材料明细该条目内容
     */
    private String content;
    /**
     * 规格
     */
    private String specification;
    /**
     * 单位
     */
    private String unit;
    /**
     * 数量
     */
    @Column(scale = 4, precision = 20)
    private BigDecimal count;
    /**
     * 单价
     */
    @Column(scale = 2, precision = 20)
    private BigDecimal price;
    /**
     * 金额
     */
    @Column(scale = 2, precision = 20)
    private BigDecimal money;

    /**
     * 备注
     */
    private String remark;
}
