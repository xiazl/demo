package com.fly.caipiao.analysis.entity;

import com.fly.caipiao.analysis.common.utils.MD5Encrypt;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author baidu
 * @date 2018/7/20 下午3:48
 * @description ${TODO}
 **/
@Document(collection = "resource")
public class ResourceStatistics implements Serializable {
    @Id
    private String id;
    private String date;
    private String targetUrl;
    private Integer count;

    public String getId() {
        return id;
    }

    public void setId() {
        this.id = MD5Encrypt.getEncrypt().encode(date+targetUrl);
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
