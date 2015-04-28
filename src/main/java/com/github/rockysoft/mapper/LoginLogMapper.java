package com.github.rockysoft.mapper;

import java.util.List;

import com.github.rockysoft.entity.Criteria;
import com.github.rockysoft.entity.LoginLog;

public interface LoginLogMapper {
	
	/**
	 * 根据条件查询记录总数
	 */
	int countByExample(Criteria example);

	 
	/**
	 * 保存记录,不管记录里面的属性是否为空
	 */
	int insert(LoginLog loginLog);
	
	/**
	 * 根据主键查询记录
	 */
	LoginLog selectByPrimaryKey(Integer id);

	 
	/**
	 * 根据条件查询记录集
	 */
	List<LoginLog> selectByExample(Criteria example);
	
}