package com.dycgb.office.common.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Description 类别
 * @Author myhe
 * @Date 2021/3/28 下午3:26
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "category")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category extends AbstractBaseEntity {
    /**
     * 类别名称
     */
    @Column(nullable = false)
    private String name;
}
