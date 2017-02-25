package com.qianshanding.framework.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 网络工具类
 * 
 * @author Fish Exp 2012-12-3 下午1:18:33
 */
public class NetHelper {
	/**
	 * 获取远程主机IP
	 * 
	 * @param request
	 * @return
	 * @author Fish Exp 2012-12-3 下午1:18:49
	 */
	public static String getRemoteAddress(HttpServletRequest request) {
		String forwards = request.getHeader("x-forwarded-for");
		if (forwards == null || forwards.trim().length() == 0 || "unknown".equalsIgnoreCase(forwards)) {
			forwards = request.getHeader("Proxy-Client-IP");
		}
		if (forwards == null || forwards.trim().length() == 0 || "unknown".equalsIgnoreCase(forwards)) {
			forwards = request.getHeader("WL-Proxy-Client-IP");
		}
		if (forwards == null || forwards.trim().length() == 0 || "unknown".equalsIgnoreCase(forwards)) {
			forwards = request.getRemoteAddr();
		}
		if (forwards == null || forwards.trim().length() == 0 || "unknown".equalsIgnoreCase(forwards)) {
			forwards = request.getHeader("X-Real-IP");
		}
		if (forwards != null && forwards.trim().length() > 0) {
			String[] ips = forwards.split(",");
			return ips[0];
		}
		return forwards;
	}
}
