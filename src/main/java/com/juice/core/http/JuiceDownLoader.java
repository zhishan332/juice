package com.juice.core.http;


import com.juice.core.config.JuiceConfig;
import com.juice.core.exception.JuiceException;
import com.juice.core.io.FileUtils;
import com.juice.core.thread.ThreadPool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 基于多线程的下载器
 *
 * @author wangqing
 * @since 1.0.0
 */
public class JuiceDownLoader implements DownLoader {
    private int threadNum;
    private String tempFilePath;
    private ExecutorService executorService;

    public JuiceDownLoader(int threadNum) {
        if (threadNum <= 0) {
            this.threadNum = 1;
        }
        this.tempFilePath = FileUtils.getWorkPath();
        executorService = ThreadPool.getInstance(this.threadNum).get();
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }

    public void setTempFilePath(String tempFilePath) {
        this.tempFilePath = tempFilePath;
    }

    /**
     * 下载
     *
     * @param url     下载地址
     * @param path    下载路径
     * @param timeout 超时时间 ，0的话为线程不限制时间但是URLConnection会默认使用30s为超时时间，直到程序出现异常 单位秒
     * @return 下载后文件路径
     * @throws Exception
     */
    @Override
    public File downLoad(String url, String path, int timeout) throws Exception {
        return downLoad(url,path,timeout,null);
    }

    /**
     * 代理下载
     *
     * @param url     下载地址
     * @param path    下载路径
     * @param timeout 超时时间 ，0的话为线程不限制时间但是URLConnection会默认使用30s为超时时间，直到程序出现异常 单位秒
     * @return 下载后文件路径
     * @throws Exception
     */
    @Override
    public File downLoad(String url, String path, int timeout,HttpProxy httpProxy) throws Exception {
        if (path == null || "".equals(path.trim())) path = FileUtils.getAutoFileName(tempFilePath) ;
        File tempFile = new File(path);
        if(tempFile.exists()) tempFile.delete();
        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();
        }
        if (tempFile.isDirectory())
            throw new JuiceException("路径不正确：" + path);
        File ff = new File(path);
        Future<Boolean> future = executorService.submit(new HttpDownloader(url,
                (new FileOutputStream(path)).getChannel(),timeout-1,httpProxy));
        if (timeout <= 0) {
            if (future.get()) {
                if (ff.exists()) return ff;
            }
        } else {
            if (future.get(timeout, TimeUnit.SECONDS)) {
                if (ff.exists()) return ff;
            }
        }

        return null;
    }

    class HttpDownloader implements Callable<Boolean> {
        private URLConnection connection;
        private FileChannel outputChann;

        public HttpDownloader(String url, FileChannel fileChannel,int timeout, final HttpProxy httpProxy) throws Exception {
            if(null!=httpProxy){
                // 创建代理服务器
                InetSocketAddress addr = new InetSocketAddress(httpProxy.getProxyHost(),
                        httpProxy.getProxyPort());
                Proxy proxy = new Proxy(Proxy.Type. HTTP , addr); // http 代理
                connection = (new URL(url)).openConnection(proxy);
            }else{
                connection = (new URL(url)).openConnection();
            }

            connection.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            connection.addRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.137 Safari/537.36");
            connection.setConnectTimeout(JuiceConfig.getInstance().getConnectTimeout()*1000);//设置连接超时
            connection.setReadTimeout(JuiceConfig.getInstance().getReadTimeout()*1000);//设置读取超时
            this.outputChann = fileChannel;
        }

        /**
         * Computes a result, or throws an exception if unable to do so.
         *
         * @return computed result
         * @throws Exception if unable to compute a result
         */
        @Override
        public Boolean call() throws Exception {
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            ReadableByteChannel rChannel = Channels.newChannel(inputStream);
            outputChann.transferFrom(rChannel, 0, Integer.MAX_VALUE);
            inputStream.close();
            outputChann.close();
            return true;
        }
    }
}
