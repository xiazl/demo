package com.fly.caipiao.analysis.entity;

import java.util.Date;

/**
 * @author baidu
 * @date 2018/6/24 下午11:09
 * @description 日志文件分析记录
 **/
public class LogFile {
    // 主键ID
    private Integer id;
    // 文件名
    private String name;
    // 问价大小
    private Integer size;
    // 创建时间
    private Date createTime;

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

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
