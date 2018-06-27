package com.fly.caipiao.analysis.entity;

/**
 * @author baidu
 * @date 2018/6/19 下午3:28
 * @description 权限
 **/
public class UserRole {
    private Integer id;
    private Integer userId;
    private Integer roleId;
    private String statusFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(String statusFlag) {
        this.statusFlag = statusFlag;
    }
}
