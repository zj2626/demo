package com.demo.common.service.util;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomStringUtils {

    //生成随机数字和字母,
    public static String getRandomString(int length) {
        String val = "";

        Random random = new Random();
        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    //生成随机数字
    public static String getRandomNumberString(int length) {
        StringBuilder val = new StringBuilder();

        Random random = new Random();
        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {
            val.append(random.nextInt(10));
        }
        return val.toString();
    }


    public static <T> T getRandomFromList(List<T> list) {

        if (list == null || list.size() < 1) {
            return null;
        }
        int index = ThreadLocalRandom.current().nextInt(0, list.size());
        return list.get(index);
    }


}
