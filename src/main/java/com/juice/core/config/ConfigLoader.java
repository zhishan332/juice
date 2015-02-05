package com.juice.core.config;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 配置加载器
 *
 * @author wangqing
 * @since 15-2-3 下午6:02
 */
public class ConfigLoader {
    private static final Logger log = LoggerFactory.getLogger(ConfigLoader.class);


    /**
     * 配置文件加载
     * TODO 添加配置为空验证
     */
    public  void load() {
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(ConfigLoader.class.getClassLoader().getResourceAsStream("juice-config.xml"));
            Element root = document.getRootElement();
            Iterator it = root.elementIterator("settings");
            while (it.hasNext()) {
                Element e = (Element) it.next();
                Iterator authIt = e.elementIterator("setting");
                while (authIt.hasNext()) {
                    Element authEle = (Element) authIt.next();
                    String name = authEle.attributeValue("name");
                    String val = authEle.attributeValue("value");
                    if ("downLoadThreadNum".equals(name)) {
                        if(!StringUtils.isBlank(val)){
                            JuiceConfig.getInstance().setDownLoadThreadNum(Integer.parseInt(val));
                        }
                    } else if ("threadTimeout".equals(name)) {
                        if(!StringUtils.isBlank(val)){
                            JuiceConfig.getInstance().setThreadTimeout(Integer.parseInt(val));
                        }
                    } else if ("defaultCharset".equals(name)) {
                        JuiceConfig.getInstance().setDefaultCharset(val);
                    } else if ("tempFilePath".equals(name)) {
                        JuiceConfig.getInstance().setTempFilePath(val);
                    } else if ("connectTimeout".equals(name)) {
                        if(!StringUtils.isBlank(val)){
                            JuiceConfig.getInstance().setConnectTimeout(Integer.parseInt(val));
                        }
                    } else if ("readTimeout".equals(name)) {
                        if(!StringUtils.isBlank(val)){
                            JuiceConfig.getInstance().setReadTimeout(Integer.parseInt(val));
                        }
                    } else if ("proxyHostName".equals(name)) {
                        JuiceConfig.getInstance().setProxyHostName(val);
                    } else if ("proxyPort".equals(name)) {
                        if(!StringUtils.isBlank(val)){
                            JuiceConfig.getInstance().setProxyPort(Integer.parseInt(val));
                        }
                    }
                }
            }

            Iterator tit = root.elementIterator("templates");
            while (tit.hasNext()) {
                Element e = (Element) tit.next();
                Iterator temIt = e.elementIterator("template");
                while (temIt.hasNext()) {
                    Template template = new Template();
                    Element temEle = (Element) temIt.next();
                    String name = temEle.attributeValue("name");
                    template.setTemplateName(name);
                    String url = temEle.attributeValue("url");
                    template.setFirstUrl(url);
                    String charset = temEle.attributeValue("charset");
                    template.setCharset(charset);
                    String cronExpression = temEle.attributeValue("cronExpression");
                    template.setCronExpression(cronExpression);
                    String useProxy = temEle.attributeValue("useProxy");
                    template.setUseProxy(Boolean.parseBoolean(useProxy));
                    Iterator cit = temEle.elementIterator("property");
                    while (cit.hasNext()) {
                        Element propEle = (Element) cit.next();
                        String select = propEle.attributeValue("selector");
                        String matcher = propEle.attributeValue("matcher");
                        String proName = propEle.attributeValue("name");
                        if ("url".equals(proName)) {
                            template.setUrlSelector(select);
                            template.setUrlMatcher(matcher);
                        } else if ("nextPage".equals(proName)) {
                            String rule = propEle.attributeValue("rule");
                            String span = propEle.attributeValue("span");
                            template.setNextPageRule(rule);
                            template.setSpan(Integer.parseInt(span));
                            setStartEnd(rule,template);
                        } else if ("title".equals(proName)) {
                            template.setTitleSelector(select);
                            template.setTitleMatcher(matcher);
                        } else if ("createTime".equals(proName)) {
                            template.setCreateTimeSelector(select);
                            template.setCreateTimeMatcher(matcher);
                            String format = propEle.attributeValue("format");
                            template.setCreateTimeFormat(format);
                        } else if ("author".equals(proName)) {
                            template.setAuthorSelector(select);
                            template.setAuthorMatcher(matcher);
                        } else if ("source".equals(proName)) {
                            template.setSourceSelector(select);
                            template.setSourceMatcher(matcher);
                        } else if ("content".equals(proName)) {
                            template.setContentSelector(select);
                            template.setContentMatcher(matcher);
                        } else if ("image".equals(proName)) {
                            template.setImageSelector(select);
                            template.setImageMatcher(matcher);
                        }
                    }
                    TemplateFactory.getInstance().addTemplate(template);
                }
            }
        } catch (DocumentException e) {
            log.error("加载juice配置文件失败，juice可能无法使用！！！", e);
        }
    }

    private  void setStartEnd(String nextRule,Template template){
        Pattern pattern = Pattern.compile("\\{(.*)}");
        Matcher matcher = pattern.matcher(nextRule);
        if (matcher.find()) {
            String str = matcher.group(1);
            String[] aay = str.split("-");
            int start = Integer.parseInt(aay[0]);
            int end = Integer.parseInt(aay[1]);
            template.setStart(start);
            template.setEnd(end);
        }
    }
}
