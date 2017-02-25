package com.qianshanding.framework.commons.exception;

/**
 * 索引异常类型
 * 
 * @author Fish Exp
 */
public class IndexException extends RuntimeException {
	private static final long serialVersionUID = -2920541562595283945L;

	public IndexException() {
		super();
	}

	public IndexException(String message, Throwable cause) {
		super(message, cause);
	}

	public IndexException(String message) {
		super(message);
	}

	public IndexException(Throwable cause) {
		super(cause);
	}

}
