package com.dycgb.office.common.model;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

/**
 * @Description 付款方式
 * @Author myhe
 * @Date 2021/3/28 下午3:27
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "payment_type")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PaymentType extends AbstractBaseEntity {
    /**
     * 类别名称
     */
    @Column(nullable = false)
    private String name;

    public PaymentType(Long id, String name) {
        setId(id);
        this.name = name;
    }
}
