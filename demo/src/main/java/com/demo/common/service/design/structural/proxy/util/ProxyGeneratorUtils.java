package com.demo.common.service.design.structural.proxy.util;

import com.demo.common.service.design.structural.proxy.demo2.RealSubject;
import org.junit.Test;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 代理类的生成工具
 *
 * @author zyb
 * @since 2012-8-9
 */
public class ProxyGeneratorUtils {

    @Test
    public void testGenerateProxyClass() {
        ProxyGeneratorUtils.writeProxyClassToHardDisk("H:/$Proxy4.class");
    }

    /**
     * 把代理类的字节码写到硬盘上
     *
     * @param path 保存路径
     */
    public static void writeProxyClassToHardDisk(String path) {
        // 第一种方法，这种方式在刚才分析ProxyGenerator时已经知道了
        // System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", true);

        // 第二种方法

        // 获取代理类的字节码
        byte[] classFile = ProxyGenerator.generateProxyClass("$Proxy4", RealSubject.class.getInterfaces());

        FileOutputStream out = null;

        try {
            out = new FileOutputStream(path);
            out.write(classFile);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}