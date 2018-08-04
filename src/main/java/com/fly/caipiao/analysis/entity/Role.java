package com.fly.caipiao.analysis.entity;

/**
 * @author baidu
 * @date 2018/6/19 下午3:28
 * @description 权限
 **/
public class Role {
    private Integer id;
    private String name;
    private String sort;
    private String desc;
    private Integer statusFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(Integer statusFlag) {
        this.statusFlag = statusFlag;
    }
}
