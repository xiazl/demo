package com.fly.caipiao.analysis.framework.page;

import java.util.Date;

/**
 * @author baidu
 * @date 2018/8/1 上午11:16
 * @description CDN日志文件下载条件参数
 **/
public class CDNDownloadConditionVO {
    // 开始时间
    private Date startTime;
    // 结束时间
    private Date endTime;
    // 域名
    private String domain;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
