package com.demo.common.service.basic;

import com.demo.common.service.basic.bean.Father;
import com.demo.common.service.basic.bean.ParamDefaultValue;
import com.demo.common.service.basic.bean.Son;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class ClassDemo {

    @Test
    public void testInstanceof() {
        Son son = new Son();
        Father father = new Father();

        // 子类实例 instanceof 父类类型
        System.out.println(son instanceof Son);
        System.out.println(son instanceof Father);
        System.out.println(father instanceof Son);
        System.out.println(father instanceof Father);
        System.out.println("------");
        // 父类.class.isAssignableFrom(子类.class)
        System.out.println(Son.class.isAssignableFrom(Son.class));
        System.out.println(Son.class.isAssignableFrom(Father.class));
        System.out.println(Father.class.isAssignableFrom(Son.class));
        System.out.println(Father.class.isAssignableFrom(Father.class));
    }

    /**
     * 使用反射的方式设置默认值
     *
     * @param o
     * @return void
     * @author zj2626
     * @date 2020/6/18
     */
    public static void applyDefaultValue(Object o) {
        Class sourceClass = o.getClass();
        //获取对象所有字段 包括父类
        ArrayList<Field> fields = new ArrayList<>();
        while (sourceClass != null) {
            fields.addAll(Arrays.asList(sourceClass.getDeclaredFields()));
            sourceClass = sourceClass.getSuperclass();
        }

        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(ParamDefaultValue.class)) {
                try {
                    Object val = field.get(o);
                    if (val != null) {
                        continue;
                    }
                    Class type = field.getType();
                    if (type.isPrimitive()) {
                        continue;
                    }
                    String defVal = field.getAnnotation(ParamDefaultValue.class).value();

                    if (String.class.isAssignableFrom(type)) {
                        field.set(o, defVal);
                    } else if (Number.class.isAssignableFrom(type)) {
                        if (Byte.class.isAssignableFrom(type)) {
                            field.set(o, Byte.valueOf(defVal));
                        } else if (Float.class.isAssignableFrom(type)) {
                            field.set(o, Float.valueOf(defVal));
                        } else if (Short.class.isAssignableFrom(type)) {
                            field.set(o, Short.valueOf(defVal));
                        } else if (Integer.class.isAssignableFrom(type)) {
                            field.set(o, Integer.valueOf(defVal));
                        } else if (Double.class.isAssignableFrom(type)) {
                            field.set(o, Double.valueOf(defVal));
                        } else if (Long.class.isAssignableFrom(type)) {
                            field.set(o, Long.valueOf(defVal));
                        }
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
