package com.juice.core.io;

import com.juice.core.config.JuiceConfig;

import java.io.*;

/**
 * To change this template use File | Settings | File Templates.
 *
 * @author wangqing
 * @since 1.0.0
 */
public class FileUtils {
    /**
     * 获取当前工作目录
     *
     * @return 当前工作目录
     */
    public static String getWorkPath() {
        return JuiceConfig.getInstance().getTempFilePath();
    }

    /**
     * 获取经过检查的路径
     *
     * @param filePath
     * @return
     */
    public static String getCheckedPath(String filePath) {
        if (filePath == null || "".equals(filePath.trim())) {
            return FileUtils.getWorkPath();
        } else {
            File file = new File(filePath.trim());
            if (file.exists() && file.isDirectory()) {
                return file.getAbsolutePath();
            } else return null;
        }
    }

    /**
     * 获得随机的文件名路径
     *
     * @param filePath
     * @return
     */
    public static String getAutoFileName(String filePath) {
        long curtime = System.nanoTime();
        String fileName = curtime + ".txt";
        File ff = new File(filePath + File.separator + fileName);
        if (ff.exists()) return null;
        return ff.getAbsolutePath();
    }

    /**
     * 读取文件内容到string
     *
     * @param file
     * @return
     * @throws java.io.IOException
     */
    public static String getFileContent(File file,String charsetName) throws IOException {
        if (file == null) return null;
        BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file),charsetName));

        String content;
        StringBuilder sb = new StringBuilder();
        while (true) {
            content = bf.readLine();
            if (content == null) {
                break;
            }
            sb.append(content.trim());
        }
        bf.close();
        return sb.toString();
    }
}
