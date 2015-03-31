package com.github.rockysoft.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.rockysoft.entity.Criteria;
import com.github.rockysoft.entity.Permission;
import com.github.rockysoft.entity.Role;
import com.github.rockysoft.entity.RoleMenu;
import com.github.rockysoft.entity.RolePermission;

public interface RoleMapper {
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
	int deleteByPrimaryKey(Integer roleId);

	/**
	 * 保存记录,不管记录里面的属性是否为空
	 */
	int insert(Role record);

	/**
	 * 保存属性不为空的记录
	 */
	int insertSelective(Role record);

	/**
	 * 根据条件查询记录集
	 */
	List<Role> selectByExample(Criteria example);

	/**
	 * 根据主键查询记录
	 */
	Role selectByPrimaryKey(Integer roleId);

	/**
	 * 根据条件更新属性不为空的记录
	 */
	int updateByExampleSelective(@Param("record") Role record, @Param("condition") Map<String, Object> condition);

	/**
	 * 根据条件更新记录
	 */
	int updateByExample(@Param("record") Role record, @Param("condition") Map<String, Object> condition);

	/**
	 * 根据主键更新属性不为空的记录
	 */
	int updateByPrimaryKeySelective(Role record);

	/**
	 * 根据主键更新记录
	 */
	int updateByPrimaryKey(Role record);
	
	int deleteRolePermissionByRoleId(int roleId);
	
	int insertRolePermission(RolePermission rolePermission);
	
	int deleteRoleMenuByRoleId(int roleId);
	
	int insertRoleMenu(RoleMenu roleMenu);
	
	List<Role> getRoleListByUserId(Integer userId);
	
}