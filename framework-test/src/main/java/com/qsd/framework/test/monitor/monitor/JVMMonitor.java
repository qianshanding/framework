package com.qsd.framework.test.monitor.monitor;

import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import javax.management.ObjectName;
import javax.management.openmbean.CompositeDataSupport;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengyu on 2016/11/21.
 */
@Log4j2
class JVMMonitor implements Monitor {

    private static String F_THREAD_COUNT = "tc";

    final static JVMMonitor instance = new JVMMonitor();
    private MemoryPoolMXBean METASPACE_MEMORY_POOL = null;
    private MemoryPoolMXBean EDEN_MEMORY_POOL = null;
    private MemoryPoolMXBean OLD_MEMORY_POOL = null;
    private String ymFullName = null;
    private String omFullName = null;

    public static JVMMonitor getInstance() {
        return instance;
    }

    public JVMMonitor() {
        if (StringUtils.isEmpty(ymFullName)) {
            ymFullName = getMemeoryPoolFullName("Eden");
        }
        if (StringUtils.isEmpty(omFullName)) {
            omFullName = getMemeoryPoolFullName("Old");
        }
        List<MemoryPoolMXBean> pools = ManagementFactory.getMemoryPoolMXBeans();
        if (pools != null && !pools.isEmpty()) {
            for (MemoryPoolMXBean pool : pools) {
                if ("Metaspace".equals(pool.getName())) {
                    METASPACE_MEMORY_POOL = pool;
                }
                if (ymFullName.equals(pool.getName())) {
                    EDEN_MEMORY_POOL = pool;
                }
                if (omFullName.equals(pool.getName())) {
                    OLD_MEMORY_POOL = pool;
                }
            }
        }
    }

    @Override
    public void run() {
        final Long threadCount = new Long(ManagementFactory.getThreadMXBean().getThreadCount());
        Map<String, Object> result = new HashMap<>();
        result.put(F_THREAD_COUNT, threadCount);
        Map<String, Long> gcMap = getGCMap();
        result.put("ygc", gcMap.get("ygc"));
        result.put("ygct", gcMap.get("ygct"));
        result.put("fgc", gcMap.get("fgc"));
        result.put("fgct", gcMap.get("fgct"));
        result.put("ym", getYoungMemory());
        result.put("om", getOldMemory());
        result.put("mm", getMetaspaceMemory());
        result.put("time", System.currentTimeMillis());

        System.out.println(JSON.toJSONString(result));
    }

    public String getMemeoryPoolFullName(String memoryPoolShortName) {
        for (GarbageCollectorMXBean garbageCollector : ManagementFactory.getGarbageCollectorMXBeans()) {
            for (String name : garbageCollector.getMemoryPoolNames()) {
                if (name.contains(memoryPoolShortName)) {
                    return name;
                }
            }
        }
        return "";
    }

    public long getYoungMemory() {
        if (null != EDEN_MEMORY_POOL) {
            return EDEN_MEMORY_POOL.getUsage().getUsed();
        }

        return this.getMemoryUsage(ymFullName);
    }

    public long getOldMemory() {
        if (null != OLD_MEMORY_POOL) {
            return OLD_MEMORY_POOL.getUsage().getUsed();
        }
        return this.getMemoryUsage(omFullName);
    }

    public long getMetaspaceMemory() {
        if (null != METASPACE_MEMORY_POOL) {
            return METASPACE_MEMORY_POOL.getUsage().getUsed();
        }
        return this.getMemoryUsage("Metaspace");
    }

    private long getMemoryUsage(String memoryName) {

        long result = 0;
        try {
            CompositeDataSupport cds = (CompositeDataSupport) ManagementFactory.getPlatformMBeanServer().getAttribute(
                    new ObjectName("java.lang:type=MemoryPool,name=" + memoryName), "Usage");
            if (null != cds) {
                MemoryUsage mu = MemoryUsage.from(cds);
                result = mu.getUsed();
            }
        } catch (Exception e) {
            log.warn("get " + memoryName + " memory error,we will set it to zero", e);
        }
        return result;
    }

    private long lastYgcCount = 0;
    private long lastFgcCount = 0;
    private long lastYgcTime = 0;
    private long lastFgcTime = 0;

    public GarbageCollectorMXBean getOldCollector() {
        for (GarbageCollectorMXBean garbageCollector : ManagementFactory.getGarbageCollectorMXBeans()) {
            for (String name : garbageCollector.getMemoryPoolNames()) {
                if (name.contains("Old")) {
                    return garbageCollector;
                }
            }
        }
        return null;
    }

    public GarbageCollectorMXBean getYgCollector() {
        boolean isEden = false;
        boolean isOld = false;
        for (GarbageCollectorMXBean garbageCollector : ManagementFactory.getGarbageCollectorMXBeans()) {

            for (String name : garbageCollector.getMemoryPoolNames()) {
                if (name.contains("Eden")) {
                    isEden = true;
                }
                if (name.contains("Old")) {
                    isOld = true;
                }
            }
            if (isEden == true && isOld == false) {
                return garbageCollector;
            }
        }
        return null;

    }

    public Map<String, Long> getGCMap() {
        Map<String, Long> resultMap = new HashMap<String, Long>();
        long ygc = new Long(0);
        long fgc = new Long(0);
        long ygct = new Long(0);
        long fgct = new Long(0);

        GarbageCollectorMXBean oldGarbageCollector = getOldCollector();
        GarbageCollectorMXBean ygGarbageCollector = getYgCollector();

        long ygCount = ygGarbageCollector.getCollectionCount();
        long ygTime = ygGarbageCollector.getCollectionTime();
        ygc = ygCount - lastYgcCount;
        ygct = ygTime - lastYgcTime;
        lastYgcCount = ygCount;
        lastYgcTime = ygTime;

        long oldCount = oldGarbageCollector.getCollectionCount();
        long oldTime = oldGarbageCollector.getCollectionTime();
        fgc = oldCount - lastFgcCount;
        fgct = oldTime - lastFgcTime;
        lastFgcCount = oldCount;
        lastFgcTime = oldTime;

        resultMap.put("ygc", ygc);
        resultMap.put("ygct", ygct);
        resultMap.put("fgc", fgc);
        resultMap.put("fgct", fgct);

        return resultMap;
    }

}
