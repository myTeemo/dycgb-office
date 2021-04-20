package com.dycgb.office.common.model;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

/**
 * @Description 用户实体类
 * @Author myhe
 * @Date 2021/4/8 上午10:14
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User extends AbstractBaseEntity {

    /**
     * 姓名
     */
    @Column(nullable = false)
    private String name;

    /**
     * 用户所属类别
     */
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    /**
     * 年龄
     */
    @Column
    private Integer age;

    /**
     * 性别
     */
    @Column
    private String sex;

    /**
     * 用户所在区域
     */
    @Column
    private String region;

    /**
     * 手机号
     */
    @Column
    private String phone;

}
