package com.dycgb.office.admin.config;

import com.dycgb.office.admin.filter.SiteMeshFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 注册sitemesh的过滤器
 *
 * @author MY-HE
 * @date 2019-11-19 09:31
 */

@Configuration
public class SiteMeshConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<SiteMeshFilter> siteMeshFilter() {
        FilterRegistrationBean<SiteMeshFilter> filter = new FilterRegistrationBean<>();
        SiteMeshFilter siteMeshFilter = new SiteMeshFilter();
        filter.setFilter(siteMeshFilter);
        return filter;
    }
}
