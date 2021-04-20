package com.dycgb.office.common.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description 分页配置
 * @Author myhe
 * @Date 2021/4/3 上午10:49
 */
@Component
@Data
public class PageConstants {
    @Value("${dycgb.office.page.pageSize}")
    private Integer pageSize;
}
