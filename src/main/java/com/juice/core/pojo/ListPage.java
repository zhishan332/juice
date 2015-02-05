package com.juice.core.pojo;

import java.util.List;

/**
 * To change this template use File | Settings | File Templates.
 *
 * @author wangqing
 * @since 15-2-4 上午11:04
 */
public class ListPage {
    private List<String> urls;
    private String nextPageUrl = "";

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ListPage{");
        sb.append("urls=").append(urls);
        sb.append(", nextPageUrl='").append(nextPageUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
