package org.smartframework.common.utils;

import java.util.UUID;

/**
 * UUID工具类
 *
 * @author chonglou
 * @date 2019/7/1810:12
 */
public class UuidUtil {

    /**
     * UUID分隔符
     */
    private static final String SPLIT = "-";

    /**
     * 返回UUID
     * <P>去除"-",32位</P>
     *
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace(SPLIT, "");
    }

    /**
     * 返回大写UUID
     * <P>去除"-",32位</P>
     *
     * @return
     */
    public static String uuidUpper() {
        return UUID.randomUUID().toString().replace(SPLIT, "").toUpperCase();
    }
}
