package com.qianshanding.framework.commons.monitor.monitor;

import com.qianshanding.framework.commons.monitor.factory.DaemonThreadFactory;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by zhengyu on 2016/11/21.
 */
@Log4j2
public class Monitors {
    private final static Monitors instance = new Monitors();

    private final ScheduledExecutorService executor;

    private static AliveMonitor aliveMonitor = AliveMonitor.getInstance();

    private static JVMMonitor jvmMonitor = JVMMonitor.getInstance();
    private AtomicBoolean isJvmRunning = new AtomicBoolean(false);


    public static Monitors getInstance() {
        return instance;
    }

    private Monitors() {
        executor = Executors.newScheduledThreadPool(1, new DaemonThreadFactory("hawk-jvm-alive-monitor"));

        //关闭线程池的钩子
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("=====>");
                if (!executor.isShutdown()) {
                    // TODO TIMEOUT
                    executor.shutdown();
                }
            }
        }));
    }


    public void startJVMMonitor() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                jvm();
                isJvmRunning.compareAndSet(false, true);
            }
        }, "hawk-register-application-thread");

        t.setDaemon(true);
        t.start();
    }

    private void jvm() {
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    jvmMonitor.run();
                } catch (Exception e) {
                    log.error("run jvm monitor error", e);
                }
            }
        }, 10, 60, TimeUnit.SECONDS);
    }

    public void shutdown() {
        executor.shutdown();
        isJvmRunning.compareAndSet(false, true);
    }
}
