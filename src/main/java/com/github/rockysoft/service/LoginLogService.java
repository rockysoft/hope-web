package com.github.rockysoft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.rockysoft.entity.Criteria;
import com.github.rockysoft.entity.LoginLog;
import com.github.rockysoft.mapper.LoginLogMapper;

@Service
public class LoginLogService {
	
	@Autowired
	private LoginLogMapper loginLogMapper;
	
	public void log(LoginLog loginLog) {
		loginLogMapper.insert(loginLog);
	}
	
	public LoginLog getLog(Integer id) {
		return loginLogMapper.selectByPrimaryKey(id);
	}
	
	public int countLogByExample(Criteria example) {
		int count = this.loginLogMapper.countByExample(example);
//		logger.debug("count: {}", count);
		return count;
	}

	public List<LoginLog> selectLogByExample(Criteria example) {
		return this.loginLogMapper.selectByExample(example);
	}
}
