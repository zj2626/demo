package com.demo.common.service.spring.learn3.proxy.dynamic.jdk;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ProxyUtil2 {

    /**
     * .java -> .class -> new
     * <p>
     * 重新构建TargetClass, 转化为聚合的形式
     * <p>
     * 1. 生成代理类的java文件
     * 2. 对生成的文件进行编译
     * 3. 产生对象(反射)
     *
     * @return
     */
    public static Object newInstance(Class interfaceClass, ProxyInvocationHandler handler) {
        String packageName = "com.google";
        String importName = interfaceClass.getName();
        String className = "Proxy$01";
        String tab = "\t";
        String line = "\n";
        String filePath = "E:/Download/proxy/";

        StringBuilder content = new StringBuilder("" +
                "package " + packageName + ";" + line +
                "import " + importName + ";" + line + line +
                "public class " + className + " implements ");

        /* 实现的接口*/
        //        for(Class interfaceClass : interfaces){
        //            content.append(interfaceClass.getSimpleName()).append(",");
        //        }
        //        content.append("{").append(line);
        content.append(interfaceClass.getSimpleName());
        content.append(" {").append(line);

        /*成员属性->目标对象*/
        content.append(tab).append("private ").append(handler.getClass().getName()).append(" handler;").append(line);

        /*构造方法*/
        content.append(tab).append("public ").append(className).append(" (").append(handler.getClass().getName()).append(" handler){").append(line);
        content.append(tab).append(tab).append("this.handler = handler;").append(line).append(tab).append("}").append(line);

        /*重写的方法*/
        Method[] methods = interfaceClass.getDeclaredMethods();
        for (Method method : methods) {
            content.append(line).append(tab).append("@Override").append(line);
            String returnType = method.getReturnType().getName();
            content.append(tab).append("public ").append(returnType).append(" ").append(method.getName()).append("(");

            /*方法参数*/
            StringBuilder params = new StringBuilder();
            StringBuilder paramNames = new StringBuilder();
            StringBuilder paramClassNames = new StringBuilder();
            Class<?>[] paraClasses = method.getParameterTypes();
            for (int i = 0; i < method.getParameterCount(); i++) {
                params.append(paraClasses[i].getName()).append(" v").append(i);
                paramNames.append(" v").append(i);
                paramClassNames.append(paraClasses[i].getName()).append(".class");
                if (i < method.getParameterCount() - 1) {
                    params.append(",");
                    paramNames.append(",");
                    paramClassNames.append(",");
                }
            }
            content.append(params);
            content.append("){").append(line);
            content.append(tab).append(tab).append("try {").append(line);

            content.append(tab).append(tab).append(tab).append("Object[] args = new Object[]{").append(paramNames).append("};").append(line);
            content.append(tab).append(tab).append(tab).append("Class[] paraClassess = new Class[]{").append(paramClassNames).append("};").append(line);
            content.append(tab).append(tab).append(tab).append(Method.class.getName()).append(" method = this.getClass()" +
                    ".getDeclaredMethod(\"").append(method.getName()).append("\", paraClassess);").append(line);
            content.append(tab).append(tab).append(tab);
            if (!"void".equals(returnType)) {
                content.append("return ").append("(").append(returnType).append(")");
            }
            content.append("handler.invoke(method, args);").append(line);

            content.append(tab).append(tab).append("} catch (Throwable e) {\n" +
                    "            throw new RuntimeException(e);\n" +
                    "        }").append(line);
            content.append(tab).append("}").append(line);
        }

        content.append("}").append(line);

        System.out.println(content.toString());

        File file = new File(filePath + "com/google/" + className +
                ".java");
        FileOutputStream outputStream = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            outputStream.write(content.toString().getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /*编译*/
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager standardJavaFileManager = javaCompiler.getStandardFileManager(null, null, null);
        Iterable compilationUnits = standardJavaFileManager.getJavaFileObjects(file);
        JavaCompiler.CompilationTask compilationTask = javaCompiler.getTask(null, standardJavaFileManager, null, null, null,
                compilationUnits);
        compilationTask.call();
        try {
            standardJavaFileManager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*加载类 生成对象*/
        URL[] urls = new URL[0];
        try {
            urls = new URL[]{new URL("file:" + filePath)};
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            URLClassLoader urlClassLoader = new URLClassLoader(urls);
            Class clazz = urlClassLoader.loadClass(packageName + "." + className);
            Constructor constructor = clazz.getConstructor(handler.getClass());
            return constructor.newInstance(handler);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void invoke() {

    }
}
