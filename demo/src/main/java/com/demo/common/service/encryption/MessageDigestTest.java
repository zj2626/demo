package com.demo.common.service.encryption;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestTest {
    public static void main(String args[]) {
        String pass = "hello world";
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes;
            if (true) {
                bytes = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
            }
            if (false) {
                digest.update(pass.getBytes(StandardCharsets.UTF_8));
                bytes = digest.digest();
            }

            System.out.println(byte2hex(bytes));

            System.out.println(byteArrayToHex(bytes));

            System.out.println(new String(bytes));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    //二行制转字符串
    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        for (int n = 0; n < b.length; n++) {
            String stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp);
            }
            if (n < b.length - 1) {
                hs.append(":");
            }
        }
        return hs.toString().toUpperCase();
    }

    public static String byteArrayToHex(byte[] byteArray) {
        // 首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray = new char[byteArray.length * 2];

        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;

        for (byte b : byteArray) {

            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];

            resultCharArray[index++] = hexDigits[b & 0xf];

        }
        // 字符数组组合成字符串返回
        return new String(resultCharArray);
    }
}
