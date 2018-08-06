package com.fly.caipiao.analysis.entity;

import com.fly.caipiao.analysis.common.utils.MD5Encrypt;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author baidu
 * @date 2018/6/17 下午5:01
 * @description ${TODO}
 **/

//@Document(collection = "user")
//@CompoundIndexes({
//        @CompoundIndex(name = "comp_index", def = "{referer : 1, targetUrl : 1}")
//})
public class CDNLogEntity implements Serializable {
    private String id;
    private String dateTime;
    private String ip;
//    @Indexed
    private String referer;
//    @Indexed
    private String targetUrl;

    private Long createTime;

    public String getId() {
        return id;
    }

    public void setId() {
        this.id = MD5Encrypt.getEncrypt().encode(dateTime+ip);
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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CDNLogEntity entity = (CDNLogEntity) o;
        return Objects.equals(id, entity.id) &&
                Objects.equals(dateTime, entity.dateTime) &&
                Objects.equals(ip, entity.ip) &&
                Objects.equals(referer, entity.referer) &&
                Objects.equals(targetUrl, entity.targetUrl) &&
                Objects.equals(createTime, entity.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, dateTime, ip, referer, targetUrl, createTime);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CDNLogEntity{");
        sb.append("id='").append(id).append('\'');
        sb.append(", dateTime='").append(dateTime).append('\'');
        sb.append(", ip='").append(ip).append('\'');
        sb.append(", referer='").append(referer).append('\'');
        sb.append(", targetUrl='").append(targetUrl).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}
