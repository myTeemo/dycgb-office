package com.dycgb.office.admin.filter;

import com.dycgb.office.admin.sitemesh.CustomTagRuleBundle;
import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

/**
 * 页面装饰过滤器
 *
 * @author MY-HE
 * @date 2019-11-15 11:29
 */
public class SiteMeshFilter extends ConfigurableSiteMeshFilter {

    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        builder.addDecoratorPath("/*", "/decorators/decorator")
                .addExcludedPath("/static/assets/**")
                .addExcludedPath("/static/css/**")
                .addExcludedPath("/static/img/**")
                .addExcludedPath("/static/media/**")
                .addExcludedPath("/security/login")
                .addExcludedPath("/security/activate")
                // 自定义标签
                .addTagRuleBundle(new CustomTagRuleBundle());

    }
}
