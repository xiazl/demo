package com.fly.caipiao.analysis.framework.excepiton;


/**
 * @author baidu
 * @date 2018/6/18 下午2:58
 * @description 异常定义
 **/

public class AppException extends RuntimeException {

	private static final long serialVersionUID = 1403920268808583220L;

	/** 错误编码 **/
	private String errorCode;

	public AppException(Throwable cause) {
		super(cause);
	}

	public AppException(String errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
	}

	public AppException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public AppException(String errorCode, Throwable cause) {
		super(errorCode, cause);
		this.errorCode = errorCode;
	}

	public AppException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("AppException{");
		sb.append("errorCode='").append(errorCode).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
