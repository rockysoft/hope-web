package com.github.rockysoft.entity;

import java.util.List;

/**
 * @author Administrator
 *
 */
public class MyInfo {
	
	private String curIp;
	private String loginName;
	private String prevLoginIp;
	private String prevLoginDate;
	private String buttonAlign;
	private List<String> actions;
	private List<String> roles;
	private List<String> roleColumns;
	
	public String getCurIp() {
		return curIp;
	}
	public void setCurIp(String curIp) {
		this.curIp = curIp;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPrevLoginIp() {
		return prevLoginIp;
	}
	public void setPrevLoginIp(String prevLoginIp) {
		this.prevLoginIp = prevLoginIp;
	}
	public String getPrevLoginDate() {
		return prevLoginDate;
	}
	public void setPrevLoginDate(String prevLoginDate) {
		this.prevLoginDate = prevLoginDate;
	}
	public String getButtonAlign() {
		return buttonAlign;
	}
	public void setButtonAlign(String buttonAlign) {
		this.buttonAlign = buttonAlign;
	}
	public List<String> getActions() {
		return actions;
	}
	public void setActions(List<String> actions) {
		this.actions = actions;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public List<String> getRoleColumns() {
		return roleColumns;
	}
	public void setRoleColumns(List<String> roleColumns) {
		this.roleColumns = roleColumns;
	}
	
}
