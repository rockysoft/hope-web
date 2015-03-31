package com.github.rockysoft.entity;

import java.util.List;

public abstract class ExtTreeData<T> {

	/// <summary>
	/// 编号
	/// </summary>
	private int id;

	/// <summary>
	/// 显示文本
	/// </summary>
	private String text;

	/// <summary>
	/// icon
	/// </summary>
	private String iconCls;

	/// <summary>
	/// 叶子
	/// </summary>
	private Boolean leaf;

	/// <summary>
	/// 儿子们 virtual
	/// </summary>
	private  List<T> children;

	/// <summary>
	/// 多少级 virtual
	/// </summary>
	private Integer level;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public List<T> getChildren() {
		return children;
	}

	public void setChildren(List<T> children) {
		this.children = children;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	//public abstract void extTreeData();
	
}
