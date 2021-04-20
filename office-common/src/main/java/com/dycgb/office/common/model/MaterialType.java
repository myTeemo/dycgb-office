package com.dycgb.office.common.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Description 材料类别实体类
 * @Author myhe
 * @Date 2021/4/13 上午10:14
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "material_type")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialType extends AbstractBaseEntity {
    @Column(nullable = false)
    private String name;
}
