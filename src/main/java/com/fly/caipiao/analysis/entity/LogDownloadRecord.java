package com.fly.caipiao.analysis.entity;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.data.annotation.Transient;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author baidu
 * @date 2018/6/24 下午11:09
 * @description 日志文件分析记录
 **/
public class LogDownloadRecord {
    // 主键ID
    private Integer id;
    // 文件名
    private String name;
    // 问价大小
    private Integer size;
    // key
    private String key;
    // 下载路径
    private String logPath;
    // 状态
    private Integer statusFlag;
    // 创建时间
    private Date createTime;

    @Transient
    private String createDate;

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public Integer getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(Integer statusFlag) {
        this.statusFlag = statusFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateDate() {
        return DateFormatUtils.format(this.createTime,"yyyy-MM-dd HH:hh:ss",
                TimeZone.getTimeZone("GMT+8"),Locale.US);
    }
}
