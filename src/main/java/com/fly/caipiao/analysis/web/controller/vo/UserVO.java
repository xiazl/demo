package com.fly.caipiao.analysis.web.controller.vo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author baidu
 * @date 2018/6/21 下午3:46
 * @description 用户
 **/
public class UserVO {
    // 用户名
    @NotNull(message = "username不能为空")
    @Length(min=5,max = 40,message = "为5～15位")
    private String username;
    // 密码
    @NotNull(message = "密码为5～15位")
    @Length(min=5,max = 15,message = "")
    private String password;
    // 昵称
    private String nickname;
    // 手机号
    private String cellphone;
    // 头像
    private String logo;

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
