package com.fly.caipiao.analysis.web.controller.vo;

import java.io.Serializable;

/**
 * @author baidu
 * @date 2018/7/29 下午1:45
 * @description 阿里云日志列表属性
 **/
public class CDNFileVO implements Serializable {
    private String logName;
    private String logPath;
    private Integer logSize;
    private String startTime;
    private String endTime;
    private String key;

    private Integer status = 0;

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public Integer getLogSize() {
        return logSize;
    }

    public void setLogSize(Integer logSize) {
        this.logSize = logSize;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
