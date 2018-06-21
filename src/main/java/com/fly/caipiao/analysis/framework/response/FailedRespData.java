package com.fly.caipiao.analysis.framework.response;

/**
 * @author baidu
 * @date 2018/6/21 下午5:29
 * @description ${TODO}
 **/
public class FailedRespData<T> extends Result{

    public static final Integer CODE = 3000;

    public static final String MESSAGE = "操作失败";

    public FailedRespData() {
        this(null);
    }

    public FailedRespData(String message) {
        this(message,CODE);
    }

    public FailedRespData(String message,Integer code) {
        this.setCode(code);
        this.setMessage(message);
    }

}
