package com.github.rockysoft.entity;

import java.util.List;

public class MenuTree extends ExtTreeData<MenuTree> {
	private Integer sort;
	//private String ControllerName;
	private String buttons;
	private Boolean expanded;
	private String url;
	private Boolean checked;
	
//	public MenuTree (id, item.getSort(), item.getText(), item.getButtons(),item.getUrl(), new List<MenuTree>(), true, 0) {
//		this.setId(id);
//		
//	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getButtons() {
		return buttons;
	}
	public void setButtons(String buttons) {
		this.buttons = buttons;
	}
	public Boolean getExpanded() {
		return expanded;
	}
	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	
}
