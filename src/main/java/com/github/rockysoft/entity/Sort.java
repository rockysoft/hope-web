package com.github.rockysoft.entity;

public class Sort {

	
	/**
	 * 排序的字段
	 */
	private String property;
	
	/**
	 * 排序的方向
	 */
	private String direction;

	public String getProperty() {
		return Table.toClumn(property);
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

}
