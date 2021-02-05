package service.cloud.client3.start.utils;

import org.springframework.security.crypto.password.PasswordEncoder;

public class MyPasswordEncoder implements PasswordEncoder {
    private static final String salt = "1234567890000000";

    public MyPasswordEncoder() {
        MD5Util.salt = this.salt;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return MD5Util.generate((String) rawPassword);
    }

    /**
     * 对表单值和数据库中值进行比较
     *
     * @param rawPassword     表单传入密码值(需要进行加密)
     * @param encodedPassword 数据库中密码(已经加密过)
     * @return
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return MD5Util.generate((String) rawPassword).equals(encodedPassword);
    }
}
