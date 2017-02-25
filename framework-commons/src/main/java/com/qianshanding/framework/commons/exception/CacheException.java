package com.qianshanding.framework.commons.exception;

/**
 * Cache异常类型
 * 
 * @author Fish Exp
 */
public class CacheException extends RuntimeException {
	private static final long serialVersionUID = -2920541562595283945L;

	public CacheException() {
		super();
	}

	public CacheException(String message, Throwable cause) {
		super(message, cause);
	}

	public CacheException(String message) {
		super(message);
	}

	public CacheException(Throwable cause) {
		super(cause);
	}

}
