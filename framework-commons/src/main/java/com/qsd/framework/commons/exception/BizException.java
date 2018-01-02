package com.qsd.framework.commons.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.text.MessageFormat;

@Data
@EqualsAndHashCode(callSuper = false)
public class BizException extends RuntimeException {

	private int errorCode;

	private String message;

	private ErrorLevel errorLevel = ErrorLevel.INFO;

	public BizException(ErrorCode errorCode) {
		this.errorCode = errorCode.getCode();
		this.message = errorCode.getMessage();
	}

	public BizException(ErrorCode errorCode, ErrorLevel errorLevel) {
		this.errorCode = errorCode.getCode();
		this.message = errorCode.getMessage();
		this.errorLevel = errorLevel;
	}

	/***
	 * 异常构建
	 *
	 * @param errorCode 异常码
	 * @param message   异常消息
	 */
	public BizException(int errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public BizException(ErrorCode errorCode, Object... arguments) {
		this.errorCode = errorCode.getCode();
		this.message = MessageFormat.format(errorCode.getMessage(), arguments);
	}

	/***
	 * 异常构建,支持替换变量
	 *
	 * @param errorCode 异常码
	 * @param message   异常消息
	 */
	public BizException(int errorCode, String message, Object ... arguments) {
		this.errorCode = errorCode;
		this.message = MessageFormat.format(message, message);
	}


	/***
	 * 异常构建(指定Error级别)
	 *
	 * @param errorCode 异常码
	 * @param errorLevel 异常等级
	 * @param message   异常消息
	 */
	public BizException(int errorCode, ErrorLevel errorLevel, String message) {
		this.errorCode = errorCode;
		this.message = message;
		this.errorLevel = errorLevel;
	}


	/***
	 * 异常构建(指定Error级别)
	 *
	 * @param errorLevel 异常等级
	 * @param arguments   异常消息
	 */
	public BizException(ErrorCode errorCode, ErrorLevel errorLevel, Object... arguments) {
		this.errorCode = errorCode.getCode();
		this.message = MessageFormat.format(errorCode.getMessage(), arguments);
		this.errorLevel = errorLevel;
	}

	/***
	 * 异常构建(指定Error级别),支持替换变量
	 *
	 * @param errorCode 异常码
	 * @param message   异常消息
	 */
	public BizException(int errorCode, ErrorLevel errorLevel, String message, Object ... arguments) {
		this.errorCode = errorCode;
		this.message = MessageFormat.format(message, message);
	}

	/**
	 * 对应 Exception_Arg异常
	 * @param message
	 */
	public BizException(String message) {
		this.errorCode = ErrorCodes.PARAM_CHECK_COMMON_ERROR.getCode();
		this.message =message;
	}


}
