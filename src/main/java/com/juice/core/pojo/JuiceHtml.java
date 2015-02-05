package com.juice.core.pojo;

/**
 * html页面
 *
 * @author wangqing
 * @since 15-2-4 上午11:11
 */
public class JuiceHtml {


    private String url;
    private String htmlStr;

    public JuiceHtml(String url, String htmlStr) {
        this.url = url;
        this.htmlStr = htmlStr;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtmlStr() {
        return htmlStr;
    }

    public void setHtmlStr(String htmlStr) {
        this.htmlStr = htmlStr;
    }
}
