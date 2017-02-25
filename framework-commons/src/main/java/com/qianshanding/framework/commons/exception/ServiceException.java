package com.qianshanding.framework.commons.exception;

/**
 * 服务异常类型
 * 
 * @author Fish Exp
 */
public class ServiceException extends Exception {
	private static final long serialVersionUID = -2920541562595283945L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

}
