package com.qianshanding.framework.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @FileName ValidateCodeServlet.java
 * @Description:
 *	<servlet> 
 *   	<servlet-name>ValidateCodeServlet</servlet-name>
 *   	<servlet-class>com.qianshanding.framework.servlet.ValidateCodeServlet</servlet-class>
 *   	<load-on-startup>0</load-on-startup>
 *   	<init-param>
 *   		<param-name>length</param-name>
 *   		<param-value>传入的参数值1</param-value>
 *   	</init-param>
 *   	<init-param>
 *   		<param-name>width</param-name>
 *   		<param-value>传入的参数值2</param-value>
 *   	</init-param>
 *	</servlet>
 *  <br />
 *  code的session key为：verify_code
 * @Date 2016年7月1日 下午2:20:35
 * @author Fish Exp
 * @version V1.0
 * 
 */
public class ValidateCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest reqeust, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置响应的类型格式为图片格式
		response.setContentType("image/jpeg");
		// 禁止图像缓存。
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		//加载初始化传入参数
		String length = this.getInitParameter("length");
		String width = this.getInitParameter("width");
		HttpSession session = reqeust.getSession();
		String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
		session.setAttribute("verify_code", verifyCode);
		VerifyCodeUtils.outputImage(Integer.parseInt(length), Integer.parseInt(width), response.getOutputStream(), verifyCode);
	}
}