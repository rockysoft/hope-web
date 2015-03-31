package com.github.rockysoft.entity;

import java.io.Serializable;

/**
 * 角色模块表
 */
public class RoleMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	
	private int roleId;
	
	private int menuId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}
	
	

}