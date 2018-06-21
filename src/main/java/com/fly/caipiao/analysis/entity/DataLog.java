package com.fly.caipiao.analysis.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author baidu
 * @date 2018/6/19 下午5:01
 * @description ${TODO}
 **/

public class DataLog implements Serializable {
    private Long id;
    private String ip;
    private String referer;
    private Date dateTime;
    private String targetUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }
}
