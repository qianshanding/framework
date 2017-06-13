package com.qianshanding.framework.commons.monitor;

import com.qianshanding.framework.commons.monitor.config.ContainerConfig;
import com.qianshanding.framework.commons.monitor.monitor.Monitors;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

/**
 *
 */
@Log4j2
public class Main {

    public static void main(String[] args) {
        start(args);
    }

    private static void start(String[] args) {
        //设置dubbo日志类型
        setLoggerType("slf4j");
        //开启JVM监控
        startJVMMonitor();
        //启动容器，阻塞
        com.alibaba.dubbo.container.Main.main(args);
    }


    private static String getAppFullName() {
        String appName = ContainerConfig.get("application.name");
        if (StringUtils.isEmpty(appName)) {
            log.error("appName can not be empty");
            throw new IllegalArgumentException("appName can not be empty");
        }
        return appName;
    }

    private static void setLoggerType(String type) {
        System.setProperty("dubbo.application.logger", type);
    }


    private static void startJVMMonitor() {
        Monitors.getInstance().startJVMMonitor();
    }
}