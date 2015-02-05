package com.juice.parse.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 模板定位器
 *
 * @author wangqing
 * @since 14-11-18 下午2:01
 */
public class HtmlMatcher {

    public static void main(String[] args) {
        HtmlMatcher.anlyze("(.*)(\\[+)  1", "中国石油新闻中心　[2014-11-18 08:14]");
    }

    public  static String anlyze(String regxStr, String str) {
        if (null == regxStr || "".equals(regxStr)) return str;
        regxStr = regxStr.trim();
        String regx;
        int groupNum = 0;
        Pattern pattern = Pattern.compile("\\s+");
        String[] regAry = pattern.split(regxStr);
        regx = regAry[0];
        if (regAry.length > 1) {
            groupNum = Integer.valueOf(regAry[1]);
        }
        if (null == str || "".equals(str)) return "";
        str = str.trim();
        pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group(groupNum).trim();
        }
        return "";
    }
}
