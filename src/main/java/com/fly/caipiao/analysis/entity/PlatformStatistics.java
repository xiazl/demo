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

@Document(collection = "platform")
public class PlatformStatistics implements Serializable {
    @Id
    private String id;
    private String date;
    private String referer;
    private Integer count;

    public String getId() {
        return id;
    }

    public void setId() {
        this.id = MD5Encrypt.getEncrypt().encode(date+referer);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
