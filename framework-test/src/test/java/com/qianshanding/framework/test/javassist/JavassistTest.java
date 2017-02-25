package com.qianshanding.framework.test.javassist;

import javassist.*;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * javassist是一款字节码编辑工具，可以直接编辑和生成Java生成的字节码，以达到对.class文件进行动态修改的效果。
 * 熟练使用这套工具，可以让Java编程更接近与动态语言编程。
 * Created by zhengyu on 2016/11/24.
 */
public class JavassistTest {

    /**
     * 获取类信息
     *
     * @throws NotFoundException
     */
    @Test
    public void testClassInfo() throws NotFoundException {
        // 获取默认类型池对象
        ClassPool classPool = ClassPool.getDefault();

        // 获取指定的类型
        CtClass ctClass = classPool.get("java.lang.String");

        System.out.println("类名:" + ctClass.getName());  // 获取类名
        System.out.println("包名:" + ctClass.getPackageName());    // 获取包名
        System.out.println("访问限定符:" + Modifier.toString(ctClass.getModifiers()));   // 获取限定符和简要类名
        System.out.println("简要类名:" + ctClass.getSimpleName());
        System.out.println("超类:" + ctClass.getSuperclass().getName());  // 获取超类
        // 获取接口
        if (ctClass.getInterfaces() != null) {
            System.out.println("继承接口:");
            boolean first = true;
            for (CtClass c : ctClass.getInterfaces()) {
                if (first) {
                    first = false;
                } else {
                    System.out.print(", ");
                }
                System.out.print(c.getName());
            }
        }
        System.out.println();
    }

    @Test
    public void testCreateClass() throws Exception {
        //CtClass对象容器
        ClassPool pool = ClassPool.getDefault();
        try {
            //创建Class
            CtClass cc = pool.makeClass("Man");
            //阻止优化调整：优化调整减少内存使用，去除对象内的一些不必要的属性，优化调整后，方法签名和注解都是不可访问的
            cc.stopPruning(true);
            //添加name属性
            CtField f = new CtField(pool.get("java.lang.String"), "name", cc);
            f.setModifiers(Modifier.PRIVATE);
            cc.addField(f);
            //生成get/set方法
            cc.addMethod(CtNewMethod.getter("getName", f));
            cc.addMethod(CtNewMethod.setter("setName", f));

            //生成构造函数
            CtConstructor c = new CtConstructor(new CtClass[]{}, cc);
            c.setBody("{\n" + "name=\"cjq\";\n}");
            cc.addConstructor(c);

            //添加say方法
            CtMethod ctm = new CtMethod(CtClass.voidType, "say",
                    new CtClass[]{}, cc);
            ctm.setModifiers(Modifier.PUBLIC);
            ctm.setBody("{\nSystem.out.println(\"my name is \"+ name);\n}");
            cc.addMethod(ctm);
            //调用生成的class类中的方法/这里可以用toClass()方法来加载类，也可以用java的类加载器来加载
            Class<?> clazz = cc.toClass();
            Object o = clazz.newInstance();
            o.getClass().getMethod("say", new Class[]{}).invoke(o, new Object[]{});
            //将class文件输出到系统文件中
            byte[] byteArr = cc.toBytecode();
            FileOutputStream fos = new FileOutputStream(new File("src/test/java/com/architecture/test/javassist/Man.class"));
            fos.write(byteArr);
            fos.close();
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
