package com.fly.caipiao.analysis.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author baidu
 * @date 2018/5/13 下午5:01
 * @description ${TODO}
 **/
@Document(collection = "user")
public class CDNLogEntity implements Serializable {
    private String date;
    private String ip;
    private String referer;
    private String targetUrl;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }
}
