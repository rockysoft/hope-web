package com.github.rockysoft.entity;

import java.util.Date;

public class Log {

	private Integer id;
	
	/* 用戶id */
	private Integer userId;
	
	/* 操作时间  */
	private Date createTime;
	
	/* 操作内容 */
	private String content;
	
	/* 用户所做的操作 */
	private String operation;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	/* 操作結果 */
	//private String operationResult;
	
	
	
}
