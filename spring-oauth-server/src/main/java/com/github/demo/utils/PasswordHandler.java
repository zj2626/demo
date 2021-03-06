package com.github.demo.utils;

import com.github.demo.utils.SpringBeanHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 2016/3/25
 *
 * @author Shengzhao Li
 */
public class PasswordHandler {


//    private PasswordEncoder passwordEncoder = SpringBeanHolder.getBean(PasswordEncoder.class);


    private PasswordHandler() {
    }


    public static String encode(String password) {
        PasswordEncoder passwordEncoder = SpringBeanHolder.getBean(PasswordEncoder.class);
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
