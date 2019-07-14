package com.kdniao.logisticsfront.common.biz.service.impl.math.algorithm;

import org.junit.Test;

import java.util.Random;

/**
 * *** Java Number & Math 类 ***
 * <p>
 * 所有的包装类（Integer、Long、Byte、Double、Float、Short）都是抽象类 Number 的子类。
 */
public class MathDemo {

    @Test
    public void number() {
        Integer x = 5;
        x = x + 10;
        System.out.println(x);
    }

    /*Math类的静态方法*/
    @Test
    public void math() {
        System.out.println(Math.sin(Math.PI / 2));
        System.out.println(Math.cos(0));
        System.out.println(Math.exp(Math.E));
        System.out.println(Math.abs(-7.1));                 //绝对值
        System.out.println(Math.log(2000));                 // 对数
        System.out.println("sqrt: " + Math.sqrt(9));        //平方根
        System.out.println(Math.pow(9, 2));                 //n次方
        System.out.println(Math.max(1, 2));                 //最大值
        System.out.println();
        System.out.println("round: " + Math.round(1.49999999));   //四舍五入
        System.out.println("round: " + Math.round(1.5));
        System.out.println("round: " + Math.round(-1.5));
        System.out.println("round: " + Math.round(-1.50000001));
        System.out.println();
        System.out.println("floor: " + Math.floor(5.9));    //返回小于数值的最大整数
        System.out.println("floor: " + Math.floor(-5.1));
        System.out.println("ceil: " + Math.ceil(5.1));      //返回大于数值的最小整数
        System.out.println("ceil: " + Math.ceil(-5.9));
        System.out.println();
        System.out.println(Math.nextAfter(1.1, 2.2));
        System.out.println(Math.nextDown(1.1));
        System.out.println(Math.nextUp(1.1));
        System.out.println(Math.addExact(1, 1));
        System.out.println(Math.copySign(1.1, 2.2));
    }

    /*Math 随机数*/
    @Test
    public void random() {
        // 随机数范围 [0, 1.0)
        System.out.println(Math.random());
        // 随机数范围 [0, 100)
        System.out.println((int) (Math.random() * 100));
        // 随机数范围 [0, 100.0)
        System.out.println(Math.random() * 100);
        System.out.println();

        Random random = new Random();
        // 随机数范围 [0, 100)
        System.out.println(random.nextInt(100));
        // 随机数范围 [0, 100.0)
        System.out.println(random.nextDouble() * 100);
        System.out.println();
        // 随机数范围 [0, 9]
        System.out.println(random.nextInt(10));
        // 随机数范围 [1, 10]
        System.out.println(random.nextInt(10) + 1);
        // 随机数范围 [50, 100]
        System.out.println(random.nextInt(50) + 50);
    }

    @Test
    public void addTest() {
        int a = 1;
        System.out.println(a++);
        System.out.println(a);
        System.out.println(++a);
        System.out.println(a);
        System.out.println();

        getInt(a++);
        System.out.println(a);
        getInt(++a);
        System.out.println(a);
    }

    private void getInt(int i) {
        System.out.println(i);
    }
}
