package com.dycgb.office.admin.sitemesh;

import org.sitemesh.SiteMeshContext;
import org.sitemesh.content.ContentProperty;
import org.sitemesh.content.tagrules.TagRuleBundle;
import org.sitemesh.content.tagrules.html.ExportTagToContentRule;
import org.sitemesh.tagprocessor.State;

/**
 * 自定义 sitemesh 标签
 *
 * @author MY-HE
 * @date 2019-11-19 20:16
 */
public class CustomTagRuleBundle implements TagRuleBundle {
    @Override
    public void install(State state, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {
        // bc 是 breadcrumbs 面包屑
        state.addRule("bc", new ExportTagToContentRule(siteMeshContext, contentProperty.getChild("bc"), false));
        // pc 为 page-content 页面内容
        state.addRule("pc", new ExportTagToContentRule(siteMeshContext, contentProperty.getChild("pc"), false));
        // hcss 为头部css
        state.addRule("hcss", new ExportTagToContentRule(siteMeshContext, contentProperty.getChild("hcss"), false));

        // hjs 为头部javascript
        state.addRule("hjs", new ExportTagToContentRule(siteMeshContext, contentProperty.getChild("hjs"), false));

        // body 下面的javascript
        state.addRule("bjs", new ExportTagToContentRule(siteMeshContext, contentProperty.getChild("bjs"), false));
    }

    @Override
    public void cleanUp(State state, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {
        if (!contentProperty.getChild("bc").hasValue()) {
            contentProperty.getChild("bc").setValue(contentProperty.getValue());
        }

        if (!contentProperty.getChild("pc").hasValue()) {
            contentProperty.getChild("pc").setValue(contentProperty.getValue());
        }

        if (!contentProperty.getChild("hcss").hasValue()) {
            contentProperty.getChild("hcss").setValue(contentProperty.getValue());
        }

        if (!contentProperty.getChild("hjs").hasValue()) {
            contentProperty.getChild("hjs").setValue(contentProperty.getValue());
        }

        if (!contentProperty.getChild("bjs").hasValue()) {
            contentProperty.getChild("bjs").setValue(contentProperty.getValue());
        }
    }
}
