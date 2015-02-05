package com.juice.core.http;


import com.juice.core.exception.JuiceException;
import com.juice.core.io.FileUtils;

import java.io.File;
import java.util.Properties;

/**
 * 默认的抓取实现
 *
 * @author wangqing
 * @since 1.0.0
 */
public class DefalutHunter implements Hunter {
    private JuiceDownLoader downLoader;

    public DefalutHunter(JuiceDownLoader downLoader) {
        this.downLoader = downLoader;
    }

    @Override
    public String getHtml(String url,String charsetName, int timeout) throws Exception {
        File ff = downLoader.downLoad(url, null, timeout);
        if (ff.exists()) {
            String html = FileUtils.getFileContent(ff, charsetName);
            boolean flag=ff.delete();
            if(!flag) System.out.println("临时文件删除失败");
            return html;
        } else throw new JuiceException("html temp file is not exist!");
    }

    @Override
    public String getHtml(String url,String charsetName, int timeout,HttpProxy httpProxy) throws Exception {
        File ff = downLoader.downLoad(url, null, timeout,httpProxy);
        if (ff.exists()) {
            String html = FileUtils.getFileContent(ff, charsetName);
            boolean flag=ff.delete();
            if(!flag) System.out.println("临时文件删除失败");
            return html;
        } else throw new JuiceException("html temp file is not exist!");
    }

    public static void main(String[] args) throws Exception {
        DefalutHunter defalutHunter=new DefalutHunter(new JuiceDownLoader(2));
        String url="http://blog.csdn.net/redhat456/article/details/6149774";
        HttpProxy http=new HttpProxy();
        http.setProxyHost("42.96.162.252");
        http.setProxyPort(9222);
        long b1=System.currentTimeMillis();
        String dd=defalutHunter.getHtml(url,"utf-8",20,http);
        long b2=System.currentTimeMillis();
        System.out.println("11:"+(b2-b1));
        System.out.println("1:"+dd);
        Properties props = System.getProperties();
        System.out.println("123123:"+dd.length());
        long c1=System.currentTimeMillis();
        String ss=defalutHunter.getHtml(url,"utf-8",20);
        long c2=System.currentTimeMillis();
        System.out.println("22:"+(c2-c1));
        System.out.println("2:"+ss);
        System.out.println("123123:"+ss.length());

    }
}
