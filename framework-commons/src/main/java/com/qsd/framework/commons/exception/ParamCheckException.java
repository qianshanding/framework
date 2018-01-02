package com.qsd.framework.commons.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.text.MessageFormat;

/**
 * Created by jianghengwei on 2017/04/12.
 * 参数校验合法性check 异常类
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ParamCheckException extends RuntimeException {

	public String message;

	public int errorCode;

	private ErrorLevel errorLevel = ErrorLevel.INFO;

	/***
	 * 异常构建
	 *
	 * @param message   异常消息
	 */
	public ParamCheckException(String message) {
		this.errorCode = ErrorCodes.PARAM_CHECK_COMMON_ERROR.getCode();
		this.message = message;
	}

	/***
	 * 异常构建
	 *
	 * @param errorCode 异常码
	 * @param message   异常消息
	 */
	public ParamCheckException(int errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	/***
	 * 异常构建
	 *
	 * @param errorCode 异常码
	 * @param message   异常消息
	 */
	public ParamCheckException(int errorCode, String message, ErrorLevel errorLevel) {
		this.errorCode = errorCode;
		this.message = message;
		this.errorLevel = errorLevel;

	}

	/***
	 * 异常构建,支持替换变量
	 *
	 * @param errorCode 异常码
	 * @param message   异常消息
	 */
	public ParamCheckException(int errorCode, String message, Object ... arguments) {
		this.errorCode = errorCode;
		this.message = MessageFormat.format(message, arguments);
	}

}
