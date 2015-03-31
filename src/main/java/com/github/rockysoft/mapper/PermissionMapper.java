package com.github.rockysoft.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.rockysoft.entity.Criteria;
import com.github.rockysoft.entity.Permission;
import com.github.rockysoft.entity.Role;

public interface PermissionMapper {
	/**
	 * 根据条件查询记录总数
	 */
	int countByExample(Criteria example);

	/**
	 * 根据条件删除记录
	 */
	int deleteByExample(Criteria example);

	/**
	 * 根据主键删除记录
	 */
	int deleteByPrimaryKey(Integer permissionId);

	/**
	 * 保存记录,不管记录里面的属性是否为空
	 */
	int insert(Permission record);

	/**
	 * 保存属性不为空的记录
	 */
	int insertSelective(Permission record);

	/**
	 * 根据条件查询记录集
	 */
	List<Permission> selectByExample(Criteria example);

	/**
	 * 根据主键查询记录
	 */
	Permission selectByPrimaryKey(Integer permissionId);

	/**
	 * 根据条件更新属性不为空的记录
	 */
	int updateByExampleSelective(@Param("record") Permission record, @Param("condition") Map<String, Object> condition);

	/**
	 * 根据条件更新记录
	 */
	int updateByExample(@Param("record") Permission record, @Param("condition") Map<String, Object> condition);

	/**
	 * 根据主键更新属性不为空的记录
	 */
	int updateByPrimaryKeySelective(Permission record);

	/**
	 * 根据主键更新记录
	 */
	int updateByPrimaryKey(Permission record);
	
	List<Permission> getPermissionListByRoleId(Integer roleId);
}