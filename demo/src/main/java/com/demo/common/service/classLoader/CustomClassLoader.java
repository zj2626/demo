package com.demo.common.service.classLoader;

import java.io.*;

/**
 * ClassLoader方法:
 * 1.getParent() : 返回该类加载器的父类加载器
 * 2.loadClass(String name) : 加载类名是name的类, 返回Class实例
 * 3.findClass(String name) : 查找类名是name的类, 返回Class实例
 * 4.findLoadClass(String name) : 查找类名是name的已经加载的类, 返回Class实例
 * 5.defindeClass(String name, byte[] b, int off, int len) : 把byte数组b中的内容转换为Java类, 返回Class实例
 * 6.resolveClass(Class<?> c) : 链接指定的Java类
 */
public class CustomClassLoader extends ClassLoader {
    private static String separator = "/";
    private String rootPath;

    public CustomClassLoader(String rootPath) {
        this.rootPath = rootPath;
    }



    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> c = findLoadedClass(name);
        if (c != null) {
            return c;
        }

        byte[] classData = getClassData(name);
        if (null != classData) {
            return defineClass(name, classData, 0, classData.length);
        } else {
            throw new ClassNotFoundException();
        }
    }

    private byte[] getClassData(String name) {
        // 需要把.class放置在应用工程所有的目录之外 防止父类加载器可以加载
        String path = rootPath + separator + getFileName(name);
        System.out.println(path);

        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;

        try {
            File file = new File(path);
            inputStream = new FileInputStream(file);
            outputStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[2014];
            int temp;
            while ((temp = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, temp);
            }

            return outputStream.toByteArray();
        } catch (Exception e) {
            System.err.println(e.getMessage());

            return null;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }

            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }

    }

    private String getFileName(String name) {
        int index = name.lastIndexOf('.');
        if(index == -1){
            return name+".class";
        }else{
            return name.substring(index+1)+".class";
        }
    }
}
