package com.github.rockysoft.framework.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.rockysoft.framework.web.support.pagination.PaginationUtils;


public class ResponseUtils {

	public static final String RESPONSE_SUCCESS_KEY = "success";

	public static final String RESPONSE_FAILURE_KEY = "failure";

	public static final String RESPONSE_TEXT_KEY = "message";

	public static final String RESPONSE_EXTRA_KEY = "extra";

	public static final String PAGINATION_ROOT_PROPERTY_KEY = "root";

	public static final String PAGINATION_TOTAL_PROPERTY_KEY = "total";
	
	public static final String FORM_ROOT_PROPERTY_KEY = "data";

	/**
	 * 分页数据
	 * */
	public static <T> Map<String, Object> sendPagination(List<T> T) {
		Map<String, Object> map = getInstanceMap();
		map.put(PAGINATION_TOTAL_PROPERTY_KEY, PaginationUtils.getPagination()
				.getTotal());
		map.put(PAGINATION_ROOT_PROPERTY_KEY, T);
		return map;
	}

	public static <T> Map<String, Object> sendList(List<T> T) {
		Map<String, Object> map = getInstanceMap();
		map.put(PAGINATION_ROOT_PROPERTY_KEY, T);
		map.put(RESPONSE_SUCCESS_KEY, true);
		return map;
	}
	
	public static <T> Map<String, Object> sendTreeMenu(List<T> T) {
		Map<String, Object> map = getInstanceMap();
//		map.put("text", ".");
		map.put("code", "OK");
	    map.put(RESPONSE_TEXT_KEY, "");
	    map.put("data", T); 
		map.put(RESPONSE_SUCCESS_KEY, true);
		return map;
	}
	
	public static <T> Map<String, Object> sendTreeGrid(List<T> T) {
		Map<String, Object> map = getInstanceMap();
		map.put("text", ".");
		map.put("children", T);
		map.put(RESPONSE_SUCCESS_KEY, true);
		return map;
	}

	public static <T, V, K> Map<String, Object> sendList(Map<K,V> T) {
		Map<String, Object> map = getInstanceMap();
		map.put(PAGINATION_ROOT_PROPERTY_KEY, T);
		map.put(RESPONSE_SUCCESS_KEY, true);
		return map;
	}
	
	public static <T> Map<String, Object> sendForm(T t) {
		Map<String, Object> map = getInstanceMap();
		map.put(FORM_ROOT_PROPERTY_KEY, t);
		map.put(RESPONSE_SUCCESS_KEY, true);
		return map;
	}

	public static Map<String, Object> sendSuccess(String text, Object... extra) {
		Map<String, Object> map = getInstanceMap();
		map.put(RESPONSE_SUCCESS_KEY, true);
		map.put(RESPONSE_TEXT_KEY, text);
		if (extra.length > 0) {
			map.put(RESPONSE_EXTRA_KEY, extra);
		}
		return map;
	}
/*
	public static Map<String, Object> sendFailure(String text, Object extra) {
		Map<String, Object> map = getInstanceMap();
		map.put(RESPONSE_FAILURE_KEY, true);
		map.put(RESPONSE_TEXT_KEY, text);
		map.put(RESPONSE_EXTRA_KEY, extra);
		return map;
	}
*/	
	public static Map<String, Object> sendFailure(String text) {
		Map<String, Object> map = getInstanceMap();
		map.put(RESPONSE_SUCCESS_KEY, false);
		map.put(RESPONSE_TEXT_KEY, text);
//		map.put(RESPONSE_EXTRA_KEY, extra);
		return map;
	}

	private static Map<String, Object> getInstanceMap() {
		return new HashMap<String, Object>();
	}

}
