package com.github.rockysoft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.rockysoft.entity.Criteria;
import com.github.rockysoft.entity.Log;
import com.github.rockysoft.entity.Permission;
import com.github.rockysoft.entity.Role;
import com.github.rockysoft.mapper.LogMapper;

@Service
public class LogService {
	
	@Autowired
	private LogMapper logMapper;
	
	public void log(Log log) {
		logMapper.insert(log);
	}
	
	public Log getLog(Integer logId) {
		return logMapper.selectByPrimaryKey(logId);
	}
	
	public int countLogByExample(Criteria example) {
		int count = this.logMapper.countByExample(example);
//		logger.debug("count: {}", count);
		return count;
	}

	public List<Log> selectLogByExample(Criteria example) {
		return this.logMapper.selectByExample(example);
	}
}
