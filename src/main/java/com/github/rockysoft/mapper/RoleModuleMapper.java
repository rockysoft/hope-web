package com.github.rockysoft.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.rockysoft.entity.Criteria;
import com.github.rockysoft.entity.RoleModule;


public interface RoleModuleMapper {
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
	int deleteByPrimaryKey(String roleModuleId);

	/**
	 * 保存记录,不管记录里面的属性是否为空
	 */
	int insert(RoleModule record);

	/**
	 * 保存属性不为空的记录
	 */
	int insertSelective(RoleModule record);

	/**
	 * 根据条件查询记录集
	 */
	List<RoleModule> selectByExample(Criteria example);

	/**
	 * 根据主键查询记录
	 */
	RoleModule selectByPrimaryKey(String roleModuleId);

	/**
	 * 根据条件更新属性不为空的记录
	 */
	int updateByExampleSelective(@Param("record") RoleModule record, @Param("condition") Map<String, Object> condition);

	/**
	 * 根据条件更新记录
	 */
	int updateByExample(@Param("record") RoleModule record, @Param("condition") Map<String, Object> condition);

	/**
	 * 根据主键更新属性不为空的记录
	 */
	int updateByPrimaryKeySelective(RoleModule record);

	/**
	 * 根据主键更新记录
	 */
	int updateByPrimaryKey(RoleModule record);
}