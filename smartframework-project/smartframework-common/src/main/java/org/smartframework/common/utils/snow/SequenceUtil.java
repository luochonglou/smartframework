package org.smartframework.common.utils.snow;


import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 基于雪花算法序列工具
 *
 * @author Tiro
 * @date 2019/6/1015:49
 */
public class SequenceUtil {
    private static SnowFlake snowFlake;

    static {

        String[] ips = getLocalIp().split("\\.");
        int workerId = 0;
        for (String ip : ips) {
            workerId += Integer.parseInt(ip);
        }
        snowFlake = new SnowFlake(workerId);
    }

    /**
     * 获取下个序列号
     *
     * @return long
     */
    public static long next() {
        return snowFlake.nextId();
    }

    /**
     * 获取下个序列号
     *
     * @param workerId 工作id
     * @return long
     */
    public static long next(long workerId) {
        return snowFlake.nextId(workerId);
    }

    /**
     * 获取下个序列号,并添加前缀
     *
     * @param prex 前缀
     * @return long
     */
    public static String next(String prex) {
        return prex + next();
    }

    /**
     * 获取下个序列号,并添加前缀
     *
     * @param prex     前缀
     * @param workerId 工作id
     * @return long
     */
    public static String next(String prex, long workerId) {
        return prex + next(workerId);
    }

    /**
     * 获得本机ip
     *
     * @return
     */
    private static String getLocalIp() {
        Enumeration<NetworkInterface> allNetInterfaces = null;
        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            throw new RuntimeException("获取本机ip异常", e);
        }
        while (allNetInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
            Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress ip = (InetAddress) addresses.nextElement();
                if (ip != null && ip instanceof Inet4Address) {
                    String host = ip.getHostAddress();
                    if ("127.0.0.1".equals(host) || "localhost".equals(host)) {
                        continue;
                    }
                    return host;
                }
            }
        }
        return "127.0.0.1";
    }
}
