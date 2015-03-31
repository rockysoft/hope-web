package com.github.rockysoft.entity;

import java.io.Serializable;

/**
 * 用户角色表
 */
public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户角色ID
	 */
	private int id;

	/**
	 * 用户ID
	 */
	private int userId;

	/**
	 * 角色ID
	 */
	private int roleId;

	/**
	 * @return 用户角色ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param userRoleId
	 *            用户角色ID
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return 用户ID
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            用户ID
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return 角色ID
	 */
	public int getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId
	 *            角色ID
	 */
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
}