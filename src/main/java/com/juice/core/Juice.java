package com.juice.core;


import com.juice.core.config.JuiceConfig;
import com.juice.core.config.Template;
import com.juice.core.http.DefalutHunter;
import com.juice.core.http.HttpProxy;
import com.juice.core.http.Hunter;
import com.juice.core.http.JuiceDownLoader;
import com.juice.core.pojo.DetailPage;
import com.juice.core.pojo.JuiceHtml;
import com.juice.core.pojo.ListPage;
import com.juice.parse.HtmlParser;

/**
 * Facade class for accessing Juice functionality. This class hides much of
 * the underlying complexity of the lower level Juice classes and provides
 * simple methods for many  operations.
 *
 * @author wangqing
 * @since 1.0.0
 */
public class Juice {
    private Hunter hunter;
    private JuiceConfig juiceConfig = JuiceConfig.getInstance();

    public Juice() {
        this.hunter = new DefalutHunter(new JuiceDownLoader(juiceConfig.getDownLoadThreadNum()));
    }

    public Juice(Hunter hunter) {
        this.hunter = hunter;
    }

    public JuiceHtml getHtml(String url) throws Exception {
        return new JuiceHtml(url, hunter.getHtml(url, juiceConfig.getDefaultCharset(), juiceConfig.getThreadTimeout()));
    }

    public JuiceHtml getHtml(String url, Template template) throws Exception {
        if (template.isUseProxy()) {
            HttpProxy httpProxy = new HttpProxy(juiceConfig.getProxyHostName(), juiceConfig.getProxyPort());
            return new JuiceHtml(url, hunter.getHtml(url, juiceConfig.getDefaultCharset(), juiceConfig.getThreadTimeout(), httpProxy));
        }
        return new JuiceHtml(url, hunter.getHtml(url, juiceConfig.getDefaultCharset(), juiceConfig.getThreadTimeout()));
    }

    public ListPage getListPage(String url, Template template, HtmlParser htmlParser, int deep) throws Exception {
        return htmlParser.parse(this.getHtml(url, template), template, deep);
    }

    public DetailPage getDetailPage(String url, Template template, HtmlParser htmlParser) throws Exception {
        return htmlParser.parse(this.getHtml(url, template), template);
    }

}
