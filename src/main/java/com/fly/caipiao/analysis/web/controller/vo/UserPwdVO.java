package com.fly.caipiao.analysis.web.controller.vo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author baidu
 * @date 2018/6/21 下午3:46
 * @description 用户
 **/
public class UserPwdVO {

    private String username;
    // 密码
    @NotNull(message = "密码不能为空")
    @Length(min=5,max = 15,message = "密码为5～15位")
    private String password;

    // 旧密码
    @NotNull(message = "旧密码不能唯空")
    @Length(min=5,max = 15,message = "旧密码为5～15位")
    private String oldPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
