package com.qsd.framework.test.monitor.monitor;

/**
 * Created by zhengyu on 2016/11/21.
 */
class AliveMonitor implements Monitor {

    final static AliveMonitor instance = new AliveMonitor();

    public static AliveMonitor getInstance() {
        return instance;
    }

    private AliveMonitor() {
    }

    @Override
    public void run() {
    }
}
