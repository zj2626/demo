package com.github.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 后台用户表
 * </p>
 *
 * @author zj2626
 * @since 2020-03-18
 */
@Data
@TableName("t_admin")
public class TAdmin {
    private Long id;

    private String loginPhone;

    private String username;

    private Integer age;

    private Integer sex;

    private String password;

    /**
     * 头像
     */
    private String icon;

    /**
     * 备注信息
     */
    private String note;

    /**
     * 最后登录时间
     */
    private Date loginTime;

    /**
     * 帐号启用状态：0->禁用；1->启用
     */
    private Integer status;
}
