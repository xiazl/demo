package com.fly.caipiao.analysis.entity;

/**
 * @author baidu
 * @date 2018/7/22 下午4:20
 * @description hbase 返回数据结构
 **/
public class HbaseEntity {
    private String id;
    private String dateTime;
    private String ip;
    private String referer;
    private String targetUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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
