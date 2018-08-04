package com.fly.caipiao.analysis.web.controller.vo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author baidu
 * @date 2018/6/21 下午3:46
 * @description 用户
 **/
public class UserVO {
    private Integer id;
    @NotNull(message = "用户名不能为空")
    @Length(min=5,max = 15,message = "用户名为5～15位字符")
    private String username;
    private Integer role;
    private String cellphone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
}
