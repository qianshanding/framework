package com.qianshanding.framework.commons.result;

import java.util.HashMap;
import java.util.Map;

/**
 * @see Result
 * @author Fish Exp
 */
public class ResultSupport implements Result {
	private static final long serialVersionUID = 5200768484307069511L;
	/**
	 * 判断是否成功
	 */
	private boolean success;
	/**
	 * 资源信息
	 */
	private String message;
	/**
	 * 错误信息对象
	 */
	private Object error;
	/**
	 * 默认返回对象
	 */
	private Object model;
	/**
	 * 返回map存放信息
	 */
	@SuppressWarnings("rawtypes")
	private Map map = new HashMap();
	/**
	 * 错误信息存放
	 */
	private Map<String, String> errorMessages = new HashMap<String, String>();

	@Override
	public boolean isSuccess() {
		return success;
	}

	@Override
	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public Object getError() {
		return error;
	}

	@Override
	public void setError(Object error) {
		this.setSuccess(false);
		this.error = error;
	}

	@Override
	public Object getDefaultModel() {
		return model;
	}

	@Override
	public void setDefaultModel(Object model) {
		this.model = model;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setDefaultModel(String modelKey, Object object) {
		this.map.put(modelKey, object);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Map getModels() {
		return map;
	}

	@Override
	public Map<String, String> getErrorMessages() {
		return errorMessages;
	}

	@Override
	public void setErrorMessages(String errorKey, String errorMessage) {
		this.setSuccess(false);
		this.errorMessages.put(errorKey, errorMessage);
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public void setMessage(String message) {
		this.message = message;
	}

}
