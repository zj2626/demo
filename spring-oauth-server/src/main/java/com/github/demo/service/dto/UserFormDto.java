package com.github.demo.service.dto;

import com.github.demo.entity.Privilege;
import com.github.demo.entity.User;
import com.github.demo.utils.PasswordHandler;

/**
 * 2016/3/25
 *
 * @author Shengzhao Li
 */
public class UserFormDto extends UserDto {
    private static final long serialVersionUID = 7959857016962260738L;


    private String password;

    public UserFormDto() {
    }


    public Privilege[] getAllPrivileges() {
        return new Privilege[]{Privilege.MOBILE, Privilege.UNITY};
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User newUser() {
        final User user = new User()
                .username(getUsername())
                .phone(getPhone())
                .email(getEmail())
                .password(PasswordHandler.encode(getPassword()));
        user.privileges().addAll(getPrivileges());
        return user;
    }
}
