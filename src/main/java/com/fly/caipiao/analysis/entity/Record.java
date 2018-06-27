package com.fly.caipiao.analysis.entity;

import java.util.Date;

/**
 * @author baidu
 * @date 2018/6/24 下午11:09
 * @description ${TODO}
 **/
public class Record {
    // 主键ID
    private Integer id;
    // url地址
    private String url;
    // 类型 1 资源地址 2 来源平台地址
    private Integer type;
    // 创建时间
    private Date createTime;

    public Record() {
    }

    public Record(String url, Integer type) {
        this.url = url;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
