package org.smartframework.common.utils;


import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机工具
 *
 * @author chonglou
 * @date 2019/5/2911:43
 */
public abstract class RandomUtil {
    /**
     * 所有字符
     */
    public static final String ALL_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 字母
     */
    public static final String LETTER_CHAR = "abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 数字
     */
    public static final String NUMBER_CHAR = "0123456789";
    /**
     * 验证码
     */
    public static final String VERIFICATION_CODE = "ABCDEFGHJKMNPQRSTUVWXYZ23456789";


    /**
     * 生成指定定范围内的随机数
     *
     * @param bound
     * @return
     */
    public static int nextInt(int bound) {
        return ThreadLocalRandom.current().nextInt(bound);
    }

    /**
     * 生成指定定范围内的随机数
     *
     * @param scopeMin
     * @param scopeMax
     * @return
     */
    public static int integer(int scopeMin, int scopeMax) {
        return ThreadLocalRandom.current().nextInt(scopeMax) % (scopeMax - scopeMin + 1) + scopeMin;
    }

    /**
     * 返回固定长度的数字
     *
     * @param length
     * @return
     */
    public static String number(int length) {
        return RandomUtil.generate(NUMBER_CHAR, length);
    }

    /**
     * 返回一个定长的随机字符串(只包含大小写字母、数字)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String str(int length) {
        return RandomUtil.generate(ALL_CHAR, length);
    }

    /**
     * 返回一个定长的随机纯字母字符串(只包含大小写字母)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String letter(int length) {
        return RandomUtil.generate(LETTER_CHAR, length);
    }

    /**
     * 返回一个定长的随机验证码
     *
     * @param length
     * @return
     */
    public static String captchaCode(int length) {
        return RandomUtil.generate(VERIFICATION_CODE, length);
    }


    /**
     * @param reference
     * @param length
     * @return
     */
    private static String generate(String reference, int length) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(reference.charAt(ThreadLocalRandom.current().nextInt(reference.length())));
        }
        return result.toString();
    }

    /**
     * 生成一个定长的纯0字符串
     *
     * @param length 字符串长度
     * @return 纯0字符串
     */
    public static String zeroString(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append('0');
        }
        return sb.toString();
    }

    /**
     * 每次生成的len位数都不相同
     *
     * @param param
     * @return 定长的数字
     */
    public static int getNotSimple(int[] param, int len) {
        for (int i = param.length; i > 1; i--) {
            int index = ThreadLocalRandom.current().nextInt(i);
            int tmp = param[index];
            param[index] = param[i - 1];
            param[i - 1] = tmp;
        }
        int result = 0;
        for (int i = 0; i < len; i++) {
            result = result * 10 + param[i];
        }
        return result;
    }

    /**
     * 从指定的数组中随机数组中的某个元素
     */
    public static <T> T randomItem(T[] param) {
        int index = integer(0, param.length);
        return param[index];
    }

}