package com.github.rockysoft.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.github.rockysoft.entity.Criteria;
import com.github.rockysoft.entity.User;
import com.github.rockysoft.entity.UserRole;

public interface UserMapper {
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
	int deleteByPrimaryKey(Integer userId);

	/**
	 * 保存记录,不管记录里面的属性是否为空
	 */
	int insert(User record);

	/**
	 * 保存属性不为空的记录
	 */
	int insertSelective(User record);

	/**
	 * 根据条件查询记录集
	 */
	List<User> selectByExample(Criteria example);

	/**
	 * 根据主键查询记录
	 */
//	User findUserById(String userId);
	User selectByPrimaryKey(Integer userId);

	/**
	 * 根据条件更新属性不为空的记录
	 */
	int updateByExampleSelective(@Param("record") User record, @Param("condition") Map<String, Object> condition);

	/**
	 * 根据条件更新记录
	 */
	int updateByExample(@Param("record") User record, @Param("condition") Map<String, Object> condition);

	/**
	 * 根据主键更新属性不为空的记录
	 */
	int updateByPrimaryKeySelective(User record);

	/**
	 * 根据主键更新记录
	 */
	int updateByPrimaryKey(User record);
	
//	@Select("select * from User where login_name=#{loginName}")
	User findUserByLoginName(String loginName);
	
	int deleteUserRolesByUserId(Integer userId);
	
	int insertUserRole(UserRole userRole);
	
	//Integer userId, String hashPassword, String salt,  Date gmtModified
	int updatePwd(Map<String, Object> parametersMap);
}