package com.fly.caipiao.analysis.framework.response;

/**
 * @author baidu
 * @date 2018/6/21 下午5:28
 * @description ${TODO}
 **/
public class SuccessRespData<T> extends Result {

    public static final int CODE = 1000;

    public static final String MESSAGE = "操作成功";


    public SuccessRespData() {
        this(null);
    }

    public SuccessRespData(T data) {
        this(data,null);
    }

    public SuccessRespData(T data, String message) {
        this.setData(data);
        this.setCode(CODE);
        this.setMessage(message);
    }
}
