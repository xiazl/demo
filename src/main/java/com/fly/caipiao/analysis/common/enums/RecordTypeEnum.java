package com.fly.caipiao.analysis.common.enums;

/**
 * @author baidu
 * @date 2018/6/26 下午1:16
 * @description record表type类型
 **/
public enum  RecordTypeEnum {
    RESOURCE(1,"资源地址"),
    PLATFORM(2,"推广平台地址")
    ;
    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    RecordTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
