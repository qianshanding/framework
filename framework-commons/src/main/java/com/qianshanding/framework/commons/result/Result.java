package com.qianshanding.framework.commons.result;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * @author Fish Exp
 */
public interface Result extends Serializable {
	/**
	 * 获取此次操作是否成功
	 * 
	 * @param success
	 */
	boolean isSuccess();

	/**
	 * 设置此次操作是否成功
	 * 
	 * @param success
	 */
	void setSuccess(boolean success);

	/**
	 * 获取直接设置的错误对象信息
	 * 
	 * @return
	 */
	Object getError();

	/**
	 * 设置错误对象信息
	 * 
	 * @param error
	 */
	void setError(Object error);

	/**
	 * 获取默认的模型对象,这个必须是用<code>setDefaultModel();</code>设置后才能获取.
	 * 
	 * @return
	 */
	Object getDefaultModel();

	/**
	 * 设置默认单个对象模型.设置后可以通过<code>getDefaultModel()</code>获取.
	 * 
	 * @param model
	 */
	void setDefaultModel(Object model);

	/**
	 * 设置多个model,取出的时候可以通过<code>getModels()</code>方法获取.
	 * 
	 * @param modelKey
	 * @param map
	 */
	void setDefaultModel(String modelKey, Object object);

	/**
	 * 获取<code>setDefaultModel()</code>设置的值.
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Map getModels();

	/**
	 * 获取<code>setErrorMessages</code>方法设置的值.
	 * 
	 * @return
	 */
	Map<String, String> getErrorMessages();

	/**
	 * 设置错误信息.
	 * 
	 * @param errorKey
	 * @param errorMessage
	 */
	void setErrorMessages(String errorKey, String errorMessage);

	/**
	 * 获取通过<code>setMessage()</code>设置的值.
	 * 
	 * @return
	 */
	String getMessage();

	/**
	 * 设置资源信息.
	 * 
	 * @param message
	 */
	void setMessage(String message);

}
