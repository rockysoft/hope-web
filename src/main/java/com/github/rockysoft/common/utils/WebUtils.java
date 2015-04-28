package com.github.rockysoft.common.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * Web层相关的实用工具类
 * 
 * @author 
 * @date 2011-12-1 下午3:14:59
 */
public class WebUtils {
	/**
	 * 将请求参数封装为Map<br>
	 * request中的参数t1=1&t1=2&t2=3<br>
	 * 形成的map结构：<br>
	 * key=t1;value[0]=1,value[1]=2<br>
	 * key=t2;value[0]=3<br>
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static HashMap<String, String> getPraramsAsMap(HttpServletRequest request) {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		Map map = request.getParameterMap();
		Iterator keyIterator = (Iterator) map.keySet().iterator();
		while (keyIterator.hasNext()) {
			String key = (String) keyIterator.next();
			String value = ((String[]) (map.get(key)))[0];
			hashMap.put(key, value);
		}
		return hashMap;
	}
	
	public static String getRemoteIP(HttpServletRequest request) {
	    String ip = request.getHeader("X-Forwarded-For");
	    if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
	        //多次反向代理后会有多个ip值，第一个ip才是真实ip
	        int index = ip.indexOf(",");
	        if(index != -1){
	            return ip.substring(0,index);
	        }else{
	            return ip;
	        }
	    }
	    ip = request.getHeader("X-Real-IP");
	    if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
	        return ip;
	    }
	    return request.getRemoteAddr();
	}
}
