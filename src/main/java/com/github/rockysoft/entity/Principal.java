package com.github.rockysoft.entity;

import java.io.Serializable;
import java.util.List;

public class Principal  implements Serializable{
private static final long serialVersionUID = 1L;
	

	// 当前用户
	private User user;

	// 当前用户所在的组集合
	private List<Role> roleList;

	// 当前用户的授权资源集合
	private List<Resource> authorizationInfo;

	// 当前用户的菜单集合
	private List<Resource> menusList;

	public Principal() {

	}

	public Principal(User user) {
		this.user = user;
	}

	public Principal(User user, List<Role> roleList,List<Resource> authorizationInfo, List<Resource> menusList) {
		this.user = user;
		this.roleList = roleList;
		this.authorizationInfo = authorizationInfo;
		this.menusList = menusList;
	}

	/**
	 * 获取当前用户
	 * 
	 * @return {@link User}
	 */
	public User getUser() {
		return user;
	}

	/**
	 * 设置当前用户
	 * 
	 * @param user 当前用户
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 获取当前用户所在的组集合
	 * 
	 * @return List
	 */
	public List<Role> getRoleList() {
		return roleList;
	}

	/**
	 * 设置当前用户所在的组集合
	 * 
	 * @param groupsList 组集合
	 */
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	/**
	 * 获取当前用户的所有授权资源集合
	 * 
	 * @return List
	 */
	public List<Resource> getAuthorizationInfo() {
		return authorizationInfo;
	}

	/**
	 * 设置当前用户的所有授权资源集合
	 * 
	 * @param authorizationInfo 资源集合
	 */
	public void setAuthorizationInfo(List<Resource> authorizationInfo) {
		this.authorizationInfo = authorizationInfo;
	}

	/**
	 * 获取当前用户拥有的菜单集合
	 * 
	 * @return List
	 */
	public List<Resource> getMenusList() {
		return menusList;
	}

	/**
	 * 设置当前用户拥有的菜单集合
	 * 
	 * @param menusList 资源集合
	 */
	public void setMenusList(List<Resource> menusList) {
		this.menusList = menusList;
	}
}
