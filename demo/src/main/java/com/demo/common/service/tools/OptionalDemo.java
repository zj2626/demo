package com.demo.common.service.tools;

import lombok.Data;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class OptionalDemo {
    Son son;
    Map<String, Son> sons;

    {
        Father father = new Father("[ father entity ]");

        son = new Son("[ son entity ]");
        son.setFather(father);

        sons = Collections.singletonMap("aaa", son);

        System.out.println("-----------------");
        System.out.println(son);
        System.out.println(sons);
        System.out.println("-----------------");
        System.out.println("-----------------");
        System.out.println("-----------------");
    }

    /**
     * 创建 Optional
     */
    @Test
    public void emptyTest() {
        Optional<Son> optionalSon = Optional.empty();
        print("empty " + optionalSon.isPresent());

        Optional<Son> optionalSon2 = Optional.ofNullable(null);
        print("ofNullable " + optionalSon2.isPresent());

        try {
            Optional<Son> optionalSon3 = Optional.of(null);
            print("of " + optionalSon3.isPresent());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Optional<Son> optionalSon4 = Optional.of(son);
        print("son " + optionalSon4.isPresent());
        print(optionalSon4.get());
    }

    /**
     * 从 Optional 实例中取回实际值对象的方法之一是使用 get() 方法
     */
    @Test
    public void isPresentTest() {
        Optional<Son> optionalSon = Optional.of(son);
        if (optionalSon.isPresent()) {
            print(optionalSon.get());
        }
    }

    /**
     * 返回默认值 (如果有值则返回该值，否则返回传递给它的参数值)
     * <p>
     * 当Optional不为空,orElse()方法仍然创建了Son对象, orElseGet方法不创建
     */
    @Test
    public void defaultTest() {
        // orElse
        Optional<Son> optionalSon = Optional.ofNullable(son);
        print(optionalSon.orElse(new Son("default name")));

        Optional<Son> optionalSon2 = Optional.ofNullable(null);
        print(optionalSon2.orElse(new Son("default name")));

        // orElseGet
        Optional<Son> optionalSon3 = Optional.ofNullable(son);
        print(optionalSon3.orElseGet(() -> new Son("default name")));

        Optional<Son> optionalSon4 = Optional.ofNullable(null);
        print(optionalSon4.orElseGet(() -> new Son("default name")));

        // orElseThrow
        Optional<Son> optionalSon5 = Optional.ofNullable(null);
        print(optionalSon5.orElseThrow(() -> new RuntimeException("异常~~~~~~~~~")));
    }

    @Test
    public void transformTest() {
        // flatMap
        print(
                Optional.ofNullable(son)
                        .flatMap(u -> Optional.ofNullable(u.getFather()))
                        .orElse(new Father("default name"))
        );

        // [Father中属性grand为null]
        print(
                Optional.ofNullable(son)
                        .flatMap(u -> Optional.ofNullable(u.getFather()))
                        .flatMap(u -> Optional.ofNullable(u.getGrand()))
                        .flatMap(u -> Optional.ofNullable(u.getGrandfather()))
                        .orElse(new GrandGrandfather("default name"))
        );

        // 另一种写法
        //        print(
        //                Optional.ofNullable(son)
        //                        .flatMap(u -> Optional.ofNullable(u.getFather())
        //                                .flatMap(k -> Optional.ofNullable(k.getGrand())
        //                                        .flatMap(l -> Optional.ofNullable(l.getGrandfather())
        //                                        )
        //                                )
        //                        )
        //                        .orElse(new GrandGrandfather("default name"))
        //        );

        // map
        print(
                Optional.ofNullable(son)
                        .map(Son::getFather)
                        .orElse(new Father("default name"))
        );

        // [Father中属性grand为null]
        print(
                Optional.ofNullable(son)
                        .map(Son::getFather)
                        .map(Father::getGrand)
                        .map(Grand::getGrandfather)
                        .orElse(new GrandGrandfather("default name"))
        );
    }

    /**
     * 条件过滤
     */
    @Test
    public void filterTest() {
        print(
                Optional.ofNullable(son)
                        .filter(u -> u.getName().contains("son entity"))
                        .orElse(new Son("default name"))
        );

        print(
                Optional.ofNullable(son)
                        .filter(u -> u.getName().contains("son entity"))
                        .map(Son::getFather)
                        .map(Father::getName)
                        .orElse("default name")
        );

        print(
                Optional.ofNullable(son)
                        .filter(u -> u.getName().contains("no entity"))
                        .orElse(new Son("default name"))
        );

        print(
                Optional.ofNullable(son)
                        .filter(u -> u.getName().contains("no entity"))
                        .map(Son::getFather)
                        .map(Father::getName)
                        .orElse("default name")
        );

        System.out.println("#####################\n");

        print(
                Optional.ofNullable(son)
                        .filter(u -> u.getName().contains("son entity"))
                        .map(Son::getFather)
                        .filter(u -> u.getName().contains("father entity"))
                        .map(Father::getName)
                        // 附加的filter,和上两行的filter一个意思,可删除
                        .filter(u -> u.contains("father entity"))
                        .orElse("default name")
        );

    }

    /**
     * equals对比, 对比的是Optional对象
     */
    @Test
    public void equalsTest() {
        print(
                Optional.ofNullable(son)
                        .equals(Optional.ofNullable(son))
        );

        print(
                Optional.ofNullable(son)
                        .filter(u -> u.getName().contains("no entity"))
                        .equals(Optional.ofNullable(son))
        );

        print(
                Optional.ofNullable(son)
                        .equals(Optional.empty())
        );

        // empty对比
        print(
                Optional.ofNullable(son)
                        .map(Son::getFather)
                        .map(Father::getGrand)
                        .map(Grand::getGrandfather)
                        .equals(Optional.empty())
        );

        // 是equals不是==
        print(
                Optional.ofNullable(son)
                        .map(Son::getFather)
                        .equals(Optional.ofNullable(new Father("[ father entity ]")))
        );
    }

    @Test
    public void testList() {
        String key = "bbb";
        print(
                Optional.ofNullable(sons)
                        .filter(s -> !s.isEmpty() && s.containsKey(key))
                        .map(s -> s.get(key))
                        .orElse(new Son("[ NULL entity ]"))
        );
    }

    @Data
    private class Son {
        private String name;
        private Father father;

        public Son(String name) {
            System.out.println("创建Son对象 name=" + name);
            this.name = name;
        }
    }

    @Data
    private static class Father {
        private String name;
        private Grand grand;

        public Father(String name) {
            System.out.println("创建Father对象 name=" + name);
            this.name = name;
        }
    }

    @Data
    private class Grand {
        private String name;
        private GrandGrandfather grandfather;

        public Grand(String name) {
            System.out.println("创建Grand对象 name=" + name);
            this.name = name;
        }
    }

    @Data
    private class GrandGrandfather {
        private String name;

        public GrandGrandfather(String name) {
            System.out.println("创建GrandGrandfather对象 name=" + name);
            this.name = name;
        }
    }

    private void print(Object msg) {
        if (null == msg) {
            System.out.println("null class");
        } else {
            System.out.println(msg.getClass() + " | " + msg + "\n");
        }
    }
}
