package com.github.rockysoft.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.rockysoft.entity.Criteria;
import com.github.rockysoft.entity.Department;
import com.github.rockysoft.entity.Organization;


public interface OrganizationMapper {
	/**
	 * 动态sql<br>
	 * 最好不要带外部参数拼装进来，防止SQL注入<br>
	 * 非正常情况不要用
	 * 
	 * @param example
	 * @return
	 */
	List<HashMap<String, Object>> selectByDynamicSql(Criteria dynamicSql);

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
	int deleteByPrimaryKey(Integer OrgId);

	/**
	 * 保存记录,不管记录里面的属性是否为空
	 */
	int insert(Organization record);

	/**
	 * 保存属性不为空的记录
	 */
	int insertSelective(Organization record);

	/**
	 * 根据条件查询记录集
	 */
	List<Organization> selectByExample(Criteria example);

	/**
	 * 根据条件查询记录集
	 */
	List<Organization> selectMyModules(Criteria example);

	/**
	 * 根据主键查询记录
	 */
	Organization selectByPrimaryKey(Integer OrgId);

	/**
	 * 根据条件更新属性不为空的记录
	 */
	int updateByExampleSelective(@Param("record") Organization record, @Param("condition") Map<String, Object> condition);

	/**
	 * 根据条件更新记录
	 */
	int updateByExample(@Param("record") Organization record, @Param("condition") Map<String, Object> condition);

	/**
	 * 根据主键更新属性不为空的记录
	 */
	int updateByPrimaryKeySelective(Organization record);

	/**
	 * 根据主键更新记录
	 */
	int updateByPrimaryKey(Organization record);
}