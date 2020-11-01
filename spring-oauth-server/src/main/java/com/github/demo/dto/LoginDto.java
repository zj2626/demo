package com.github.demo.dto;

import lombok.Data;

/**
 * 用户登录参数
 */
@Data
public class LoginDto {
    private String loginPhone;
    private String password;
}
