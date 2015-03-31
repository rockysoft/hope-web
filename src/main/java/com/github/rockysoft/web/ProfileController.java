package com.github.rockysoft.web;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.rockysoft.entity.User;
import com.github.rockysoft.service.AccountService;
import com.github.rockysoft.service.ShiroDbRealm.ShiroUser;
import com.google.common.collect.Lists;

/**
 * 用户修改自己资料的Controller.
 * 
 * @author liukai
 */
@Controller
@RequestMapping(value = "/profile")
public class ProfileController {

	//private static final int DEFAULT_PAGE_NUM = 0;
	//private static final int DEFAULT_PAGE_SIZE = 8;
	//private static final String REDIRECT_SUCCESS_URL = "redirect:/profile/";

	@Autowired
	private AccountService accountService;
	/**
	 * 跳转到个人信息页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String profileForm(Model model) {

		//model.addAttribute("group", comm.accountService.findGroupByUserId(getCurrentUserId()));
		model.addAttribute("user", accountService.getCurrentUser());

		return "account/profile";
	}

	/**
	 * 修改
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String profile(@RequestParam(value = "id") Integer id, @RequestParam(value = "email") String email,
			@RequestParam(value = "plainPassword") String plainPassword,
			@RequestParam(value = "phonenum") String phonenum, @RequestParam(value = "realName") String realName,
			@RequestParam(value = "leaderId") Integer leaderId,
			@RequestParam(value = "departmentId") Integer departmentId,
			@RequestParam(value = "groupId") Integer groupId, RedirectAttributes redirectAttributes) {

		//List<Group> groupList = Lists.newArrayList();

		//groupList.add(comm.accountService.getGroup(groupId));

		ShiroUser sUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		if (sUser == null)
			return "redirect:/home/";
		User user = accountService.getUser(sUser.getId());
		if (user == null)
			return "redirect:/home/";
		user.setEmail(email);
		user.setPlainPassword(plainPassword);
//		user.setPhonenum(phonenum);
		user.setRealName(realName);
//		user.setLeaderId(leaderId);
		//user.setDepartment(comm.accountService.getDepartment(departmentId));
		//user.setGroupList(comm.accountService.getGroupListById(groupId));

		accountService.updateUser(user);

		// 更新Shiro中当前用户的用户名.

		updateCurrentUserName(user.getRealName());

		redirectAttributes.addFlashAttribute("message", "修改个人信息成功");

		return "redirect:/profile/";
	}

	/**
	 * 更新Shiro中当前用户的用户名.
	 * 
	 * @param userName
	 */
	private void updateCurrentUserName(String userName) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		user.setName(userName);
	}
	
		/**
	 * 部门Map
	 * 
	 * @return
	 */
//	@ModelAttribute("departmentMap")
//	public Map<String, String> departmentMap() {
//		return Constant.DEPARTMENT;
//	}

	/**
	 * 有审核权限的领导列表
	 * 
	 * @return
	 */
//	@ModelAttribute("leaderList")
//	public List<User> leaderList() {
//		return accountService.getLeaderListByType(Constant.USER_TYPE_AUDIT);
//	}

}
