package com.dycgb.office.common.model;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 流水材料概览
 * @Author myhe
 * @Date 2021/4/13 上午10:24
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "material_overview")
@Data
@Builder
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class MaterialOverview extends AbstractBaseEntity {

    /**
     * 材料所属用户（材料商）
     */
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    /**
     * 材料所属类别
     */
    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private MaterialType materialType;

    @OneToMany(mappedBy = "materialOverview", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MaterialDetails> materialDetailsList = new ArrayList<>();

    /**
     * 票据日期
     */
    private String documentDate;
    /**
     * 票据序号
     */
    private String documentNo;
    /**
     * 票据内容
     */
    private String content;

    /**
     * 票据合计金额
     */
    @Column(scale = 2, precision = 20)
    private BigDecimal money;

    /**
     * 备注
     */
    private String remark;
}
