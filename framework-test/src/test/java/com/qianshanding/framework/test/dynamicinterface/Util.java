package com.qianshanding.framework.test.dynamicinterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by zhengyu on 2016/12/27.
 */
public class Util {
    public static <T> T getInstance(Class<T> cl, Object instance) {
        Object o = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                new Class[]{cl},
                new MyHandler(instance)
        );
        return (T) o;

    }

    private static class MyHandler implements InvocationHandler {
        private Object instance;

        public MyHandler(Object instance) {
            this.instance = instance;
        }

        /*
         * 无论调用动态接口的任何方法都会调用invoke方法
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Method method1 = instance.getClass().getMethod(method.getName(), method.getParameterTypes());
            CurrentThread currentThread = method1.getAnnotation(CurrentThread.class);
            if (currentThread != null) {
                method1.invoke(instance, args);
            }
            SubThread subThread = method1.getAnnotation(SubThread.class);
            if (subThread != null) {
                method1.invoke(instance, args);
            }
            if (subThread != null) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            method1.invoke(instance, args);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }

                    }
                }.start();
            }
            return null;
        }
    }
}