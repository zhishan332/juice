package com.juice.core.config;

/**
 * 解析使用的模板
 *
 * @author wangqing
 * @since 15-2-3 下午6:03
 */
public class Template {
    private String templateName;
    private String firstUrl;
    private boolean useProxy;
    private String charset;
    private String cronExpression;
    private String urlSelector;
    private String urlMatcher;
    private String nextPageRule;
    private String titleSelector;
    private String titleMatcher;
    private String createTimeSelector;
    private String createTimeMatcher;
    private String createTimeFormat;
    private String authorSelector;
    private String authorMatcher;
    private String sourceSelector;
    private String sourceMatcher;
    private String contentSelector;
    private String contentMatcher;
    private String imageSelector;
    private String imageMatcher;
    private int span=1;
    private int start=0;
    private int end=0;


    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public boolean isUseProxy() {
        return useProxy;
    }

    public void setUseProxy(boolean useProxy) {
        this.useProxy = useProxy;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getUrlSelector() {
        return urlSelector;
    }

    public void setUrlSelector(String urlSelector) {
        this.urlSelector = urlSelector;
    }

    public String getUrlMatcher() {
        return urlMatcher;
    }

    public void setUrlMatcher(String urlMatcher) {
        this.urlMatcher = urlMatcher;
    }

    public String getNextPageRule() {
        return nextPageRule;
    }

    public void setNextPageRule(String nextPageRule) {
        this.nextPageRule = nextPageRule;
    }

    public String getTitleSelector() {
        return titleSelector;
    }

    public void setTitleSelector(String titleSelector) {
        this.titleSelector = titleSelector;
    }

    public String getTitleMatcher() {
        return titleMatcher;
    }

    public void setTitleMatcher(String titleMatcher) {
        this.titleMatcher = titleMatcher;
    }

    public String getCreateTimeSelector() {
        return createTimeSelector;
    }

    public void setCreateTimeSelector(String createTimeSelector) {
        this.createTimeSelector = createTimeSelector;
    }

    public String getCreateTimeMatcher() {
        return createTimeMatcher;
    }

    public void setCreateTimeMatcher(String createTimeMatcher) {
        this.createTimeMatcher = createTimeMatcher;
    }

    public String getAuthorSelector() {
        return authorSelector;
    }

    public void setAuthorSelector(String authorSelector) {
        this.authorSelector = authorSelector;
    }

    public String getAuthorMatcher() {
        return authorMatcher;
    }

    public void setAuthorMatcher(String authorMatcher) {
        this.authorMatcher = authorMatcher;
    }

    public String getSourceSelector() {
        return sourceSelector;
    }

    public void setSourceSelector(String sourceSelector) {
        this.sourceSelector = sourceSelector;
    }

    public String getSourceMatcher() {
        return sourceMatcher;
    }

    public void setSourceMatcher(String sourceMatcher) {
        this.sourceMatcher = sourceMatcher;
    }

    public String getContentSelector() {
        return contentSelector;
    }

    public void setContentSelector(String contentSelector) {
        this.contentSelector = contentSelector;
    }

    public String getContentMatcher() {
        return contentMatcher;
    }

    public void setContentMatcher(String contentMatcher) {
        this.contentMatcher = contentMatcher;
    }

    public String getImageSelector() {
        return imageSelector;
    }

    public void setImageSelector(String imageSelector) {
        this.imageSelector = imageSelector;
    }

    public String getImageMatcher() {
        return imageMatcher;
    }

    public void setImageMatcher(String imageMatcher) {
        this.imageMatcher = imageMatcher;
    }

    public int getSpan() {
        return span;
    }

    public void setSpan(int span) {
        this.span = span;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getFirstUrl() {
        return firstUrl;
    }

    public void setFirstUrl(String firstUrl) {
        this.firstUrl = firstUrl;
    }

    public String getCreateTimeFormat() {
        return createTimeFormat;
    }

    public void setCreateTimeFormat(String createTimeFormat) {
        this.createTimeFormat = createTimeFormat;
    }
}
