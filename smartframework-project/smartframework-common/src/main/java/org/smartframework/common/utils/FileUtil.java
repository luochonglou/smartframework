package org.smartframework.common.utils;

import java.io.File;

/**
 * 文件工具类
 *
 * @author chonglou
 * @date 2019/7/1710:02
 */
public class FileUtil {

    /**
     * 不存在存在文件或目录
     *
     * @param path
     * @return
     */
    public static boolean notExist(String path) {
        return !exist(path);
    }

    /**
     * 存在文件或目录
     *
     * @param path
     * @return
     */
    public static boolean exist(String path) {
        File file = new File(path);
        if (file.exists())
            return true;
        return false;
    }


    /**
     * 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
     *
     * @param destPath
     */
    public static void mkdirs(String destPath) {
        File file = new File(destPath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }


    /**
     * 删除文件或目录
     *
     * @param file
     */
    private static void emptyFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                emptyFile(files[i]);
            }
        }
        file.delete();
    }
}
