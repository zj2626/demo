package com.kdniao.logisticsfront.common.biz.service.impl.encryption;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestTest {
    public static void main(String args[]) {
        String pass = "hello world";
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(pass.getBytes("utf-8"));
            byte[] bytes = digest.digest();
            System.out.println(byte2hex(bytes));
            System.out.println(new String(bytes));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public static String byte2hex(byte[] b) //二行制转字符串
    {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            if (n < b.length - 1)
                hs = hs + ":";
        }
        return hs.toUpperCase();
    }
}
