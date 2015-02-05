package com.juice.core.config;

/**
 * 配置项
 *
 * @author wangqing
 * @since 15-2-3 下午5:19
 */
public class JuiceConfig {
    private static volatile JuiceConfig instance = null;
    private int downLoadThreadNum;
    private int threadTimeout;
    private String defaultCharset;
    private String tempFilePath;
    private int connectTimeout;
    private int readTimeout;
    private String proxyHostName;
    private int proxyPort;

    private JuiceConfig() {
    }

    /**
     * 获取ListTemplate对象
     *
     * @return instance
     */
    public static JuiceConfig getInstance() {
        if (instance == null) {
            synchronized (JuiceConfig.class) {
                if (instance == null)
                    instance = new JuiceConfig();
            }
        }
        return instance;
    }

    public int getDownLoadThreadNum() {
        return downLoadThreadNum;
    }

    public void setDownLoadThreadNum(int downLoadThreadNum) {
        this.downLoadThreadNum = downLoadThreadNum;
    }

    public String getDefaultCharset() {
        return defaultCharset;
    }

    public void setDefaultCharset(String defaultCharset) {
        this.defaultCharset = defaultCharset;
    }

    public String getTempFilePath() {
        return tempFilePath;
    }

    public void setTempFilePath(String tempFilePath) {
        this.tempFilePath = tempFilePath;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public String getProxyHostName() {
        return proxyHostName;
    }

    public void setProxyHostName(String proxyHostName) {
        this.proxyHostName = proxyHostName;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public int getThreadTimeout() {
        return threadTimeout;
    }

    public void setThreadTimeout(int threadTimeout) {
        this.threadTimeout = threadTimeout;
    }
}
