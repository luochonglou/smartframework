/**
 * Created by Tiro on 2020/08/21.
 */
package org.smartframework.common.utils.security;

import org.smartframework.common.exception.SystemException;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Objects;

/**
 * 类描述：摘要工具 <br>
 *
 * @Description: 摘要工具
 * @Author: Tiro
 * @Date: 2020/8/21 16:33
 */
public abstract class DigestUtil {
    private static final Integer SALT_LEN = 16;
    public static final int DEFAULT_DIGEST_TIMES = 1024;


    /**
     * 生成随机salt
     *
     * @return
     */
    public static String generateSalt() {
        return DigestUtil.generateSalt(SALT_LEN);
    }

    /**
     * 生成随机salt
     *
     * @param len
     * @return
     */
    public static String generateSalt(int len) {
        byte[] bytes = new byte[len >> 1];
        SecureRandom random = new SecureRandom();
        random.nextBytes(bytes);
        return DigestUtil.encodeHex(bytes);
    }


    /**
     * @param data
     * @return
     * @throws UnsupportedEncodingException
     * @throws GeneralSecurityException
     */
    public static String sha1Hex(String data) {
        return DigestUtil.sha1Hex(data, null);
    }

    /**
     * @param data
     * @param salt
     * @return
     */
    public static String sha1Hex(String data, String salt) {
        byte[] pwdBytes = digest(DigestUtil.getByteUtf8(data),
                "SHA-1",
                DigestUtil.decodeHex(salt),
                DEFAULT_DIGEST_TIMES);
        return DigestUtil.encodeHex(pwdBytes);
    }


    /**
     * @param data
     * @return
     * @throws UnsupportedEncodingException
     * @throws GeneralSecurityException
     */
    public static String md5Hex(String data) {
        return DigestUtil.md5Hex(data, null);
    }

    /**
     * @param data
     * @param salt
     * @return
     */
    public static String md5Hex(String data, String salt) {
        byte[] pwdBytes = digest(DigestUtil.getByteUtf8(data),
                "MD5",
                DigestUtil.decodeHex(salt),
                DEFAULT_DIGEST_TIMES);
        return DigestUtil.encodeHex(pwdBytes);
    }


    /**
     * 摘要算法
     *
     * @param bytes
     * @param algorithm
     * @param salt
     * @param counts
     * @return
     */
    private static byte[] digest(byte[] bytes, String algorithm, byte[] salt, int counts) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            if (Objects.nonNull(salt)) {
                digest.update(salt);
            }
            byte[] result = digest.digest(bytes);
            for (int i = 1; i < counts; i++) {
                digest.reset();
                result = digest.digest(result);
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            throw new SystemException(e);
        }
    }

    /**
     * 字符串转字节数组
     * <p>UTF-8编码格式</p>
     *
     * @param str
     * @return
     */
    private static byte[] getByteUtf8(String str) {
        try {
            return Objects.isNull(str) || 0 == str.trim().length() ? null : str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new SystemException(e);
        }
    }

    /**
     * 字节数组转换为十六进制字符串
     *
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    private static String encodeHex(byte[] bytes) {
        if (null == bytes) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int k = 0, j = bytes.length; k < j; k++) {
            if ((bytes[k] & 0xFF) < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(bytes[k] & 0xFF, 16));
        }
        return sb.toString();
    }

    /**
     * 十六进制字符串转换为字节数组
     *
     * @param hexString 十六进制字符串
     * @return 字节数组
     */
    private static byte[] decodeHex(String hexString) {
        if (null == hexString) {
            return null;
        }
        if (hexString.length() % 2 != 0) {
            return new byte[0];
        }
        byte[] dest = new byte[hexString.length() / 2];
        for (int i = 0, j = dest.length; i < j; i++) {
            dest[i] = (byte) Integer.parseInt(hexString.substring(2 * i, 2 * i + 2), 16);
        }
        return dest;
    }


}
