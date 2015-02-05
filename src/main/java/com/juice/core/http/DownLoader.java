package com.juice.core.http;

import java.io.File;

/**
 * 下载器
 *
 * @author wangqing
 * @since 1.0.0
 */
public interface DownLoader {

    /**
     * 下载
     *
     * @param url     下载地址
     * @param path    下载路径,如果为Null,或者为“”，将默认当前工作路径
     * @param timeout 超时时间 ，0的话为不限制时间，直到程序出现异常
     * @return 下载后文件路径
     * @throws Exception
     */
    File downLoad(String url, String path, int timeout) throws Exception;

    /**
     * 下载
     *
     * @param url     下载地址
     * @param path    下载路径,如果为Null,或者为“”，将默认当前工作路径
     * @param timeout 超时时间 ，0的话为不限制时间，直到程序出现异常
     * @param httpProxy 代理
     * @return 下载后文件路径
     * @throws Exception
     */
    File downLoad(String url, String path, int timeout, HttpProxy httpProxy) throws Exception;
}
