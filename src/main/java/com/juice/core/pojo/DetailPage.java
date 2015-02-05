package com.juice.core.pojo;

import java.util.Date;
import java.util.List;

/**
 * 详情页
 *
 * @author wangqing
 * @since 15-2-4 上午11:00
 */
public class DetailPage {
    private String pageUrl = "";
    private String title = "";
    private String author = "";
    private String src = "";
    private Date publishTime;
    private List<String> imageList;
    private List<String> contentList;

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public List<String> getContentList() {
        return contentList;
    }

    public void setContentList(List<String> contentList) {
        this.contentList = contentList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DetailPage{");
        sb.append("pageUrl='").append(pageUrl).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", author='").append(author).append('\'');
        sb.append(", src='").append(src).append('\'');
        sb.append(", publishTime=").append(publishTime);
        sb.append(", imageList=").append(imageList);
        sb.append(", contentList=").append(contentList);
        sb.append('}');
        return sb.toString();
    }
}
