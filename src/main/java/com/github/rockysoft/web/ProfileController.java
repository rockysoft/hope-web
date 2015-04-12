package com.github.rockysoft.web;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.rockysoft.entity.Principal;
import com.github.rockysoft.entity.Role;
import com.github.rockysoft.entity.User;
import com.github.rockysoft.framework.util.ResponseUtils;
import com.github.rockysoft.service.AccountService;
//import com.github.rockysoft.service.ShiroDbRealm.ShiroUser;
import com.github.rockysoft.viewmodel.UserModel;
import com.google.common.collect.Lists;

/**
 * 用户修改自己资料的Controller.
 * 
 * @author liukai
 */
@Controller
@RequestMapping(value = "/account/profile")
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
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody Object get() throws Exception{
		Principal principal = this.accountService.getCurrentUser(); 
		if (principal == null) 
			return "redirect:/home/";
		User user = principal.getUser();
//		User user = accountService.getUser(principal.getId());
		if (user == null)
			return ResponseUtils.sendFailure("会话过期！");
		UserModel model = new UserModel();
		model.setId(user.getId());
		model.setOrgId(user.getOrgId());
		model.setPassword(user.getPassword());
		model.setLoginName(user.getLoginName());
		model.setRealName(user.getRealName());
		model.setSex(user.getSex());
		model.setEmail(user.getEmail());
		model.setMobile(user.getMobile());
		model.setOfficePhone(user.getOfficePhone());
		model.setRemark(user.getRemark());
		return ResponseUtils.sendForm(model);
	}

	/**
	 * 修改
	 */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Object profile(@RequestBody UserModel model) {
//		ShiroUser sUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		Principal principal = this.accountService.getCurrentUser();
		if (principal == null)
			return "redirect:/home/";
		User currentUser = principal.getUser();
		User user = new User();//accountService.getUser(sUser.getId());
		user.setId(currentUser.getId());
//		if (user == null)
//			return "redirect:/home/";
		user.setEmail(model.getEmail());
//		user.setPhonenum(phonenum);
		user.setRealName(model.getRealName());
		accountService.changeProfile(user);
		// 更新Shiro中当前用户的用户名.
		updateCurrentUserName(user.getRealName());
		return ResponseUtils.sendSuccess("保存成功");
	}

	/**
	 * 更新Shiro中当前用户的用户名.
	 * 
	 * @param realName
	 */
	private void updateCurrentUserName(String realName) {
//		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		Principal principal = this.accountService.getCurrentUser();
		User currentUser = principal.getUser();
		currentUser.setRealName(realName);
		principal.setUser(currentUser);
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
