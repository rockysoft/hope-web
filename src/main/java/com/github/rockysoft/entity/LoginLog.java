package com.github.rockysoft.entity;

import java.util.Date;

public class LoginLog {

	/* 流水号 */
	private Integer id;
	
	/* 用戶id */
	private Integer userId;
	
	/* 用戶账号 */
	private String loginName;
	
	/* 操作时间  */
	private Date createTime;
	
	/* 操作内容 */
	private String ip;
	
	/* 日志类型 */
	private Integer type; 
	
	/* 日志状态 */
	private Integer status;
	
	public LoginLog() {

	}
	
	public LoginLog(Integer userId, String loginName, Date createTime, String ip, Integer type, Integer status) {
		this.userId = userId;
		this.loginName = loginName;
		this.createTime = createTime;
		this.ip = ip;
		this.type = type;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}  
}
