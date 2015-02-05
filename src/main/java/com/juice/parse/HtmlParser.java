package com.juice.parse;


import com.juice.core.config.Template;
import com.juice.core.pojo.DetailPage;
import com.juice.core.pojo.JuiceHtml;
import com.juice.core.pojo.ListPage;

/**
 * 网页分析器
 *
 * @author wangqing
 * @since 1.0.0
 */
public interface HtmlParser {

    ListPage parse(JuiceHtml html,Template template,int deep);

    DetailPage parse(JuiceHtml html,Template template);
}
