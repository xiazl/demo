package com.fly.caipiao.analysis.entity;

import com.fly.caipiao.analysis.common.utils.MD5Encrypt;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author baidu
 * @date 2018/7/22 下午10:20
 * @description 资源-平台详情
 **/

@Document(collection = "resource_platform")
public class ResourcePlatformStatistics implements Serializable {
    private String id;
    private String date;
    @Indexed
    private String targetUrl;
    @Indexed
    private String referer;
    private Integer count;

    public String getId() {
        return id;
    }

    public void setId() {
        this.id = MD5Encrypt.getEncrypt().encode(date+targetUrl+referer);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
