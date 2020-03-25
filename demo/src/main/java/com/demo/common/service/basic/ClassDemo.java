package com.demo.common.service.basic;

import com.demo.common.service.basic.bean.A;
import com.demo.common.service.basic.bean.B;
import org.junit.Test;

public class ClassDemo {

    @Test
    public void testInstanceof(){
        A a = new A();
        B b = new B();

        // 子类实例 instanceof 父类
        System.out.println(a instanceof A);
        System.out.println(a instanceof B);
        System.out.println(b instanceof A);
        System.out.println(b instanceof B);
        System.out.println("------");
        // 父类 instanceof 子类
        System.out.println(A.class.isAssignableFrom(A.class));
        System.out.println(A.class.isAssignableFrom(B.class));
        System.out.println(B.class.isAssignableFrom(A.class));
        System.out.println(B.class.isAssignableFrom(B.class));
    }

}
