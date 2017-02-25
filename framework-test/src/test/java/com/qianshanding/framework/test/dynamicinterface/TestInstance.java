package com.qianshanding.framework.test.dynamicinterface;

/**
 * Created by zhengyu on 2016/12/27.
 */
public class TestInstance implements TestInter {

    @Override
    @SubThread
    public void runHttp() {
        System.out.println("TestInstance.runHttp");
        System.out.println(Thread.currentThread());
    }

    @Override
    @SubThread
    public void saveData(String data) {
        System.out.println("TestInstance.saveData");
        System.out.println(data);
        System.out.println(Thread.currentThread());
    }

    @Override
    @CurrentThread
    public void draw() {
        System.out.println("TestInstance.draw");
        System.out.println(Thread.currentThread());

    }
}