package com.juice.core.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 模板工厂
 *
 * @author wangqing
 * @since 15-2-3 下午6:01
 */
public class TemplateFactory {

    private static volatile TemplateFactory instance = null;
    private List<Template> templateList;

    private TemplateFactory() {
        templateList = new ArrayList<Template>();
    }

    /**
     * 获取ListTemplate对象
     *
     * @return instance
     */
    public static TemplateFactory getInstance() {
        if (instance == null) {
            synchronized (TemplateFactory.class) {
                if (instance == null)
                    instance = new TemplateFactory();
            }
        }
        return instance;
    }


    public void addTemplate(Template template){
        templateList.add(template);
    }


    public List<Template> getTemplateList() {
        return templateList;
    }

}
