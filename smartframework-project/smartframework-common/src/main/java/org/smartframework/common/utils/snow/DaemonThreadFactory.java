package org.smartframework.common.utils.snow;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;


/**
 * 守护线程工厂
 *
 * @author Tiro
 * @date 2019/5/3016:32
 */
public class DaemonThreadFactory implements ThreadFactory {

    private AtomicLong threadNo = new AtomicLong(1);
    private String poolName;
    private int priority = Thread.NORM_PRIORITY;

    public DaemonThreadFactory(String poolName) {
        setPoolName(poolName);
    }

    public DaemonThreadFactory setPoolName(String poolName) {
        this.poolName = poolName + "-";
        return this;
    }

    public DaemonThreadFactory setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    @Override
    public Thread newThread(Runnable r) {
        String threadName = poolName + threadNo.getAndIncrement();
        Thread newThread = new Thread(r, threadName);
        newThread.setDaemon(true);
        if (newThread.getPriority() != priority) {
            newThread.setPriority(priority);
        }
        return newThread;
    }

}