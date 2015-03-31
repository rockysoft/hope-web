package com.github.rockysoft.entity;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * Ext的分页请求对象
 * 
 * @author chenxin
 * @date 2011-3-17 上午10:37:27
 */
public class ExtPager {

	private Integer limit;
	private Integer start;
	/**
	 * 大写的ASC or DESC
	 */
//	private String dir;
	/**
	 * 排序的字段
	 */
	private String sort;
//	private List<Sort> sort = Lists.newArrayList();

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

//	public List<Sort> getSort() {
//		return sort;
//	}
//
//	public void setSort(List<Sort> sort) {
//		this.sort = sort;
//	}

//	public String getDir() {
//		return dir;
//	}
//
//	public void setDir(String dir) {
//		this.dir = dir;
//	}

	public String getSort() {
		// TODO:先转化
		return sort;//Table.toClumn(sort);
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	

}
