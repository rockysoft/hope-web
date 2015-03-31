package com.github.rockysoft.entity;

import java.util.List;

/**
 * Ext Grid返回对象
 * 
 * @author chenxin
 * @date 2011-3-10 下午09:43:35
 */
public class ExtGridReturn {

	/**
	 * 总共条数,total
	 */
	private int total;
	/**
	 * 所有数据,root
	 */
	private List<?> root;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	private String message;
	
	private boolean success;

	public ExtGridReturn() {
	}

	public ExtGridReturn(int total, List<?> root, boolean success, String message) {
		this.total = total;
		this.root = root;
		this.success = success;
		this.message = message;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<?> getRoot() {
		return root;
	}

	public void setRoot(List<?> root) {
		this.root = root;
	}

}
