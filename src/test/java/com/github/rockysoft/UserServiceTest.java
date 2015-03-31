package com.github.rockysoft;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.rockysoft.entity.User;
import com.github.rockysoft.service.AccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class UserServiceTest
{
	@Autowired
	private AccountService accountService;
	
	@Test
	public void testFindAllusers() {
		//List<User> users = userService.findAllUsers();
//		Assert.assertNotNull(users);
//		for (User user : users) {
//			System.err.println(user);
//		}
	}
}
