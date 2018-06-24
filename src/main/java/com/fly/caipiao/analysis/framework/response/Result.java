package com.fly.caipiao.analysis.framework.response;

/**
 * @author baidu
 * @date 2018/6/21 下午5:25
 * @description ${TODO}
 **/
public class Result<T> {
    private static final int DEFAULT_CODE = 1000;
    // 状态码
    private int code = DEFAULT_CODE;
    // 返回message
    private String message;
    // 数据返回
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public void setErrorMessage(String message) {
        this.setCode(2000);
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
