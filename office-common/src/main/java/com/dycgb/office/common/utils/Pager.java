package com.dycgb.office.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description 分页器
 * @Author myhe
 * @Date 2021/4/3 下午8:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pager<T> {
    private Integer page;
    private Integer pageSize;
    private Long offset;
    private Integer totalPages;
    private boolean hasNext;
    private List<T> content;
    private Long total;
}
