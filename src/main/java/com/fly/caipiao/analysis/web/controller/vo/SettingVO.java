package com.fly.caipiao.analysis.web.controller.vo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author baidu
 * @date 2018/7/31 下午3:34
 * @description 域名配置验证VO
 **/
public class SettingVO {
    private Integer id;
    @NotNull(message = "域名不能为空")
    @Length(min = 5, max = 40, message = "域名为5～40位字符")
    private String domain;
    @NotNull(message = "AccessKeyId不能为空")
    @Length(min = 5, max = 40, message = "域名为5～40位字符")
    private String key;
    @NotNull(message = "AccessKeySecret不能为空")
    @Length(min = 5, max = 60, message = "域名为5～60位字符")
    private String secret;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
