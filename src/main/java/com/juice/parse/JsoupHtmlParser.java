package com.juice.parse;

import com.juice.core.config.Template;
import com.juice.core.pojo.DetailPage;
import com.juice.core.pojo.JuiceHtml;
import com.juice.core.pojo.ListPage;
import com.juice.parse.util.HtmlMatcher;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 使用Jsoup的分析器
 *
 * @author wangqing
 * @since 1.0.0
 */
public class JsoupHtmlParser implements HtmlParser {

    private static final Logger log = LoggerFactory.getLogger(JsoupHtmlParser.class);

    @Override
    public ListPage parse(JuiceHtml html,Template template, int deep) {
        if (StringUtils.isBlank(html.getHtmlStr()) || StringUtils.isBlank(html.getUrl())) return null;
        Document doc = this.getDoc(html.getHtmlStr(), html.getUrl());
//        System.out.println("urlsel:"+template.getUrlSelector());
        Elements urls = doc.select(template.getUrlSelector());
//        System.out.println("urls:"+urls);
        if (urls == null || urls.size() == 0) return null;

        ListPage listPage = new ListPage();
        if (!StringUtils.isBlank(template.getNextPageRule())) {
            listPage.setNextPageUrl(this.getNextUrlByRule(template, deep));
        }
        List<String> urlList = new ArrayList<String>();
        for (Element tt : urls) {
            String linkHref = tt.absUrl("href");
            if (!StringUtils.isBlank(linkHref)) {
                if (!StringUtils.isBlank(template.getUrlMatcher())) {
                    String urlStr = HtmlMatcher.anlyze(template.getUrlMatcher(), linkHref);
                    urlList.add(urlStr == null ? "" : urlStr.trim());
                } else urlList.add(linkHref == null ? "" : linkHref.trim());
            }
        }
        listPage.setUrls(urlList);
        return listPage;
    }

    @Override
    public DetailPage parse(JuiceHtml html, Template template) {
        if (StringUtils.isBlank(html.getHtmlStr()) || StringUtils.isBlank(html.getUrl())) return null;
        Document doc = this.getDoc(html.getHtmlStr(), html.getUrl());
        if (template == null) return null;
        DetailPage detailPage=new DetailPage();
        if (StringUtils.isBlank(template.getTitleSelector())) {
            detailPage.setTitle(doc.title() == null ? "" : doc.title().trim());
        } else {
            detailPage.setAuthor(getSelectDoc(doc,template.getTitleSelector(),template.getTitleMatcher()));
        }
        detailPage.setAuthor(getSelectDoc(doc,template.getAuthorSelector(),template.getAuthorMatcher()));
        String timeStr=getSelectDoc(doc, template.getCreateTimeSelector(), template.getCreateTimeMatcher());
        if(!StringUtils.isBlank(timeStr)){
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat(template.getCreateTimeFormat());
            try {
                Date date = simpleDateFormat.parse(timeStr);
                detailPage.setPublishTime(date);
            } catch (ParseException e) {
                log.error("格式化日期错误，template:"+template);
            }
        }
        detailPage.setSrc(getSelectDoc(doc,template.getSourceSelector(),template.getSourceMatcher()));
        Elements text = doc.select(template.getContentSelector());
        List<String> con = new ArrayList<String>();
        for (Element tt : text) {
            String str = tt.ownText();
            if (StringUtils.isBlank(str)) {
                str = tt.text();
            }
            if (StringUtils.isBlank(str)) {
                str = tt.toString();
                str = delHTMLTag(str);
            }
            if (!StringUtils.isBlank(str)) {
                if (!StringUtils.isBlank(template.getContentMatcher())) {
                    String conStr = HtmlMatcher.anlyze(template.getContentMatcher(), str);
                    con.add(conStr == null ? "" : conStr.trim());
                } else con.add(str == null ? "" : str.trim());
            }
        }
        detailPage.setContentList(con);
        List<String> imgList = new ArrayList<String>();
        if (!StringUtils.isBlank(template.getImageSelector())) {
            Elements imgEs = doc.select(template.getImageSelector());
            if (imgEs != null && imgEs.size() > 0) {
                for (Element tt : imgEs) {
                    String srcUrl = tt.absUrl("src");
                    if (!StringUtils.isBlank(srcUrl)) {
                        if (!StringUtils.isBlank(template.getImageMatcher())) {
                            String imgStr = HtmlMatcher.anlyze(template.getImageMatcher(), srcUrl);
                            imgList.add(imgStr == null ? "" : imgStr.trim());
                        } else imgList.add(srcUrl == null ? "" : srcUrl.trim());
                    }
                }
            }
        }
        detailPage.setImageList(imgList);
        return detailPage;
    }

    private String getSelectDoc(Document doc, String selector, String matcher) {
        if (!StringUtils.isBlank(selector)) {
            Elements author = doc.select(selector);
            if (author != null && author.size() > 0) {
                for (Element tt : author) {
                    String str = tt.ownText();
                    if (StringUtils.isBlank(str)) {
                        str = tt.text();
                    }
                    if (StringUtils.isBlank(str)) {
                        str = tt.toString();
                        str = delHTMLTag(str);
                    }
                    if (StringUtils.isBlank(str)) continue;
                    if (!StringUtils.isBlank(matcher)) {
                        String authorStr = HtmlMatcher.anlyze(matcher, str);
                        return authorStr == null ? "" : authorStr.trim();
                    } else return str == null ? "" : str.trim();
                }
            }
        }
        return null;
    }

    private String getNextUrlByRule(Template template, int deep) {

        int pageNum;
        if (deep == 0) {
            pageNum = template.getStart();
        } else {
            int span = template.getSpan();
            if (span <= 0) span = 1;
            pageNum = deep * span + template.getStart();
        }
        if (pageNum > template.getEnd()) return null;
        String nextPageUrl = null;
        if (!StringUtils.isBlank(template.getNextPageRule())) {
            nextPageUrl = template.getNextPageRule().replaceAll("\\{(.*)}", String.valueOf(pageNum));
        }
        return nextPageUrl;
    }


    private Document getDoc(String htmlStr, String url) {
        if (StringUtils.isBlank(htmlStr)) return null;
        Document doc;
        if (StringUtils.isBlank(url)) {
            doc = Jsoup.parse(htmlStr);
        } else doc = Jsoup.parse(htmlStr, url);
        return doc;
    }

    private String delHTMLTag(String htmlStr) {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式

        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); //过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); //过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); //过滤html标签
        htmlStr = htmlStr.replaceAll("&nbsp;", "");
        return htmlStr.trim(); //返回文本字符串
    }
}
