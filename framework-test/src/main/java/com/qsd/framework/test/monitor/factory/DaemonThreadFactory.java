package com.qsd.framework.test.monitor.factory;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhengyu on 2016/11/21.
 */
@Log4j2
public class DaemonThreadFactory implements ThreadFactory {
    private ThreadGroup group;
    private AtomicInteger number = new AtomicInteger(1);
    private String prefix;

    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            try {
                if (e != null && e instanceof Error) {
                    if (e instanceof OutOfMemoryError) {
                        try {
                            System.err.println("Halting due to Out Of Memory Error..." + Thread.currentThread().getName());
                        } catch (Throwable err) {
                            // Again we don't want to exit because of logging issues.
                        }
                        Runtime.getRuntime().halt(-1);
                    } else {
                        // Running in daemon mode, we would pass Error to calling thread.
                        throw (Error) e;
                    }
                }
            } catch (Error error) {
                log.error("Received error in main thread.. terminating server...", error);
                Runtime.getRuntime().exit(-2);
            }
        }
    };

    public DaemonThreadFactory(String poolName) {
        if (null == poolName || poolName.isEmpty()) {
            throw new RuntimeException("poolName is null");
        }
        prefix = poolName + "-Pool-";
        group = Thread.currentThread().getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(group, runnable, prefix + number.getAndIncrement(), 0);
        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }
        if (!thread.isDaemon()) {
            // exit with main thread
            thread.setDaemon(true);
        }
        thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
        return thread;
    }
}
