package com.demo.common.service.encryption.md5;

public class SaltDemo {
    public static void main(String[] args) {
        // 原文
        String plaintext = "123456";
        System.out.println("原始：" + plaintext);
        System.out.println("普通MD5后：" + MD5Util.MD5(plaintext));

        // 获取加盐后的MD5值
        String ciphertext = MD5Util.generate(plaintext);
        System.out.println("加盐后MD5：" + ciphertext);
        System.out.println("是否是同一字符串:" + MD5Util.verify(plaintext, ciphertext));

        /**
         * 其中某次原文字符串的MD5值
         */
        String[] tempSalt = {
                "385910011f0135389bb6b196d17f00682c71648e7bc70504",
                "062c68125d9883f57f04e44909f285650454d64775c68e1b",
                "41591989aa29b7015809ca7805096c50429b887456d43638",
                "b3df5484963321fa2291f28666d48d25d09db32c2fe4105c"
        };

        for (String temp : tempSalt) {
            System.out.println("是否是同一字符串:" + MD5Util.verify(plaintext, temp));
        }

    }
}
