package com.github.rockysoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.rockysoft.entity.Log;
import com.github.rockysoft.mapper.LogMapper;

@Service
public class LogService {
	
	@Autowired
	private LogMapper logMapper;
	
	public void log(Log log) {
		logMapper.insert(log);
	}
	

}
