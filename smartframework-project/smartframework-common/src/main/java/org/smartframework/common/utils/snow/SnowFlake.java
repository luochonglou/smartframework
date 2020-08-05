package org.smartframework.common.utils.snow;

/**
 * 推特雪花算法
 * <p>
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000 <br>
 * 1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0<br>
 * 41位时间截(毫秒级)，注意，41位时间截不是存储当前时间的时间截，而是存储时间截的差值（当前时间截 - 开始时间截)
 * 得到的值），这里的的开始时间截，一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下下面程序IdWorker类的startTime属性）。41位的时间截，可以使用69年，年T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69<br>
 * 10位的数据机器位，可以部署在1024个节点，包括5位datacenterId和5位workerId<br>
 * 12位序列，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间截)产生4096个ID序号<br>
 * 加起来刚好64位，为一个Long型。<br>
 * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，并且效率较高，经测试，SnowFlake每秒能够产生26万ID左右。
 *
 * @author Tiro
 * @date 2019/6/1015:52
 */
public class SnowFlake {

    /**
     * 工作ID
     */

    private long workerId;
    /**
     * 毫秒内序列
     */
    private long sequence;
    /**
     * 初始时间
     */
    private long twepoch = 1560153236610L;
    /**
     * 工作id占用位数
     */
    private long workerIdBits = 10L;
    /**
     * 最大工作id
     */
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);
    /**
     * 序列号占用位数
     */
    private long sequenceBits = 12L;
    /**
     * 序列号最大值
     */
    private long maxSequence = -1L ^ (-1L << sequenceBits);
    /**
     * 工作id向左位移个数
     */
    private long workerIdShift = sequenceBits;
    /**
     * 时间戳差值左位移个数
     */
    private long timestampLeftShift = sequenceBits + workerIdBits;
    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = timeGen();

    /**
     * @param workerId
     */
    public SnowFlake(long workerId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        this.workerId = workerId;
    }

    /**
     * @return
     */
    public long nextId() {
        return nextId(workerId);
    }

    /**
     * @param workerId
     * @return
     */
    public synchronized long nextId(Long workerId) {
        //获得当前时间
        long timestamp = timeGen();
        //如果当前时间小于上一次时间，说明服务器的时间被调过，直接报错
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                    lastTimestamp - timestamp));
        }
        //如果时间相等则尝试增加序列号
        if (lastTimestamp == timestamp) {
            //序列号增加一位，并&最大序列号防止溢出
            sequence = (sequence + 1) & maxSequence;
            //序列号已经耗尽，则等待下一个时间
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            //时间不同则直接序列号为0
            sequence = 0;
        }
        //记录时间错
        lastTimestamp = timestamp;
        //组建id
        return ((timestamp - twepoch) << timestampLeftShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    /**
     * 获取下一时间
     *
     * @param lastTimestamp
     * @return
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        SnowFlake worker = new SnowFlake(1);
        int len = 30;
        for (int i = 0; i < len; i++) {
            System.out.println(worker.nextId());
        }

    }

}
