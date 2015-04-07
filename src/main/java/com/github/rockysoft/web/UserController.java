package com.github.rockysoft.web;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.ui.Model;

import com.github.rockysoft.entity.ChangePasswordVo;
import com.github.rockysoft.entity.Criteria;
import com.github.rockysoft.entity.ExtGridReturn;
import com.github.rockysoft.entity.ExtPager;
import com.github.rockysoft.entity.Menu;
import com.github.rockysoft.entity.Role;
import com.github.rockysoft.entity.User;
import com.github.rockysoft.framework.util.ResponseUtils;
import com.github.rockysoft.framework.utils.Digests;
import com.github.rockysoft.framework.utils.Encodes;
import com.github.rockysoft.service.AccountService;
import com.github.rockysoft.viewmodel.UserModel;
import com.google.common.collect.Lists;



@Controller
@RequestMapping(value = "/account/users")
public class UserController {

	private static final int DEFAULT_PAGE_NUM = 0;
	private static final int DEFAULT_PAGE_SIZE = 8;
	private static final String REDIRECT_SUCCESS_URL = "redirect:/account/user/";

	@Autowired
	private AccountService accountService;

//	@Autowired
//	private AuditFlowManager auditFlowManager;

	//@Autowired
//	private RoleListEditor roleListEditor;

//	@InitBinder
//	public void initBinder(WebDataBinder b) {
//		b.registerCustomEditor(List.class, "roleList", roleListEditor);
//	}

	/**
	 * 显示所有的user list
	 * 
	 * @param page
	 * @param name
	 * @param model
	 * @return
	 */
	//@RequiresPermissions("user:view")
	@RequestMapping(method = RequestMethod.GET)
//	public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "name", required = false, defaultValue = "") String name, Model model) {
//
//		int pageNum = page != null ? page : DEFAULT_PAGE_NUM;
//		Page<User> users = accountManager.getAllUser(pageNum, DEFAULT_PAGE_SIZE, name);
//
//		model.addAttribute("page", users);
//		return "account/userList";
//	}
//	@RequestMapping(method = RequestMethod.POST)
	//@ResponseBody
	//public Object list(ExtPager pager, @RequestParam(required = false, defaultValue = "") String realName) {
	public @ResponseBody ExtGridReturn list(ExtPager pager, 
	@RequestParam(required = false, defaultValue = "") String realName, 
	@RequestParam(required = false, defaultValue = "-1") Integer organizationId
//	Model model
	) {
		Criteria criteria = new Criteria();
		
		// 设置分页信息
		if (pager.getLimit() != null && pager.getStart() != null) {
		//	criteria.setOracleEnd(pager.getLimit() + pager.getStart());
		//	criteria.setOracleStart(pager.getStart());
			criteria.setMysqlOffset(pager.getStart());
			criteria.setMysqlLength(pager.getLimit());
		}
		// 排序信息
//		if (StringUtils.isNotBlank(pager.getDir()) && StringUtils.isNotBlank(pager.getSort())) {
//			criteria.setOrderByClause(pager.getSort() + " " + pager.getDir());
//		}
		if (StringUtils.isNotBlank(realName)) {
			criteria.put("realNameLike", realName);
		}
		if (organizationId != -1) {
			criteria.put("orgId", organizationId);
		}
//		int pageStart = pageSize * (pageNo - 1);
		//criteria.setOracleEnd(pageSize + pageStart);
		//criteria.setOracleStart(pageStart);
//		criteria.setMysqlOffset(pageStart);
//		criteria.setMysqlLength(pageSize);
		List<User> list = this.accountService.selectByExample(criteria);
		int total = this.accountService.countByExample(criteria);
		return new ExtGridReturn(total, list, true, "成功");
//		return ResponseUtils.sendPagination(list) ;
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public @ResponseBody Object get(@PathVariable("id") Integer userId) throws Exception{
		User user = this.accountService.getUser(userId);//  .updateRole(role);
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
		Integer[] roles = new Integer[user.getRoleList().size()];
		int i=0;
		for (Role role : user.getRoleList()) {
			roles[i] = role.getId();
			i++;
//			model.setRoles(roles);
		}
		model.setRoles(roles);
		return ResponseUtils.sendForm(model);
	}

	/**
	 * 跳转到新增页面
	 * 
	 * @param model
	 * @return
	 */
//	@RequiresPermissions("user:edit")
//	@RequestMapping(value = "/save", method = RequestMethod.GET)
//	public String createForm(Model model) {
//		model.addAttribute("user", new User());
//		model.addAttribute("allRoles", accountService.getAllRole());
//		return "account/userForm";
//	}

	/**
	 * 新增
	 * 
	 * @param user
	 * @param redirectAttributes
	 * @return
	 */
//	@RequiresPermissions("user:edit")
//	@RequestMapping(value = "/save", method = RequestMethod.POST)
//	public String save(User user, RedirectAttributes redirectAttributes) {
//
//		/*
//		 * 权限组里是否包含管理员
//		 */
//		boolean isAdmin = user.getRoleList().contains(accountService.getRole(Constant.USER_TYPE_ADMIN));
//
//		/*
//		 * 权限组里是否包含审核人
//		 */
//		boolean isAudit = user.getRoleList().contains(accountService.getRole(Constant.USER_TYPE_AUDIT));
//
//		if (isAdmin) {
//			if (isAudit) {// 如果权限组包含管理员和审核人,用户类型都设置为审核人
//				user.setType(Constant.USER_TYPE_AUDIT);
//			} else {
//				user.setType(Constant.USER_TYPE_ADMIN);
//			}
//		} else if (isAudit) {
//
//			// 审核人的用户类型都设置为审核人
//			user.setType(Constant.USER_TYPE_AUDIT);
//
//		} else {
//			// 申请人或其他
//			user.setType(Constant.USER_TYPE_APPLY);
//		}
//
//		accountService.saveUser(user);
//		redirectAttributes.addFlashAttribute("message", "创建用户 " + user.getName() + " 成功");
//		return REDIRECT_SUCCESS_URL;
//	}

	/**
	 * 跳转到修改页面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
//	@RequiresPermissions("user:edit")
//	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
//	public String updateForm(@PathVariable("id") Integer id, Model model) {
//		model.addAttribute("allRoles", accountService.getAllRole());
//		model.addAttribute("user", accountService.getUser(id));
//		return "account/userForm";
//	}

	/**
	 * 修改
	 * 
	 * @param user
	 * @param redirectAttributes
	 * @return
	 */
//	@RequiresPermissions("user:edit")
//	@RequestMapping(value = "/update", method = RequestMethod.POST)
//	public String update(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
//
////		auditFlowManager.deleteByUser(user);// 删除该用户所有的审核流程.

		/*
		 * 权限组里是否包含管理员
		 */
//		boolean isAdmin = user.getRoleList().contains(accountService.getRole(Constant.USER_TYPE_ADMIN));

		/*
		 * 权限组里是否包含审核人
		 */
//		boolean isAudit = user.getRoleList().contains(accountService.getRole(Constant.USER_TYPE_AUDIT));

//		if (isAdmin) {
//			if (isAudit) {// 如果权限组包含管理员和审核人,用户类型都设置为审核人
//				user.setType(Constant.USER_TYPE_AUDIT);
////				auditFlowManager.saveAuditFlow(user);
//			} else {
//				user.setType(Constant.USER_TYPE_ADMIN);
//			}
//		} else if (isAudit) {
//
//			// 审核人的用户类型都设置为审核人
//			user.setType(Constant.USER_TYPE_AUDIT);
////			auditFlowManager.saveAuditFlow(user);
//
//		} else {
//			// 申请人或其他
//			user.setType(Constant.USER_TYPE_APPLY);
//		}
//
//		accountService.saveUser(user);
//
//		redirectAttributes.addFlashAttribute("message", "修改用户 " + user.getName() + " 成功");
//		return REDIRECT_SUCCESS_URL;
//	}
	
	@RequestMapping(value="{id}", method=RequestMethod.PUT)
	public @ResponseBody Object update(@RequestBody UserModel model) throws Exception{
		User user = this.accountService.getUser(model.getId());
		
		user.setOrgId(model.getOrgId());
		user.setPassword(model.getPassword());
		//user.setConfirmPassword(model.getConfirmPassword());
		user.setPlainPassword(model.getPassword());
		user.setLoginName(model.getLoginName());
		user.setRealName(model.getRealName());
		user.setSex(model.getSex());
		user.setEmail(model.getEmail());
		user.setMobile(model.getMobile());
		user.setOfficePhone(model.getOfficePhone());
		user.setRemark(model.getRemark());
		List<Role> roleList = Lists.newArrayList();
		for (Integer id : model.getRoles() ) {
			Role role = this.accountService.getRole(id);  //roleId,
			if (role != null)
				roleList.add(role);
		}
		if (!roleList.isEmpty())
			user.setRoleList(roleList);
		this.accountService.updateUser(user);
		return ResponseUtils.sendSuccess("保存成功");
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody Object save(@RequestBody UserModel model) throws Exception{
		User user = new User();
		user.setOrgId(model.getOrgId());
		user.setPassword(model.getPassword());
		//user.setConfirmPassword(model.getConfirmPassword());
		user.setPlainPassword(model.getPassword());
		user.setLoginName(model.getLoginName());
		user.setRealName(model.getRealName());
		user.setSex(model.getSex());
		user.setEmail(model.getEmail());
		user.setMobile(model.getMobile());
		user.setOfficePhone(model.getOfficePhone());
		user.setRemark(model.getRemark());
		List<Role> roleList = Lists.newArrayList();
		for (Integer id : model.getRoles() ) {
			Role role = this.accountService.getRole(id);  //roleId,
			if (role != null)
				roleList.add(role);
		}
		if (!roleList.isEmpty())
			user.setRoleList(roleList);
		this.accountService.saveUser(user);
		
		
		return ResponseUtils.sendSuccess("保存成功");
	}
	
	@RequestMapping(value="{id}",method=RequestMethod.DELETE)
	public @ResponseBody Object delete(@PathVariable("id") Integer userId) throws Exception{
		this.accountService.deleteUser(userId);
		return ResponseUtils.sendSuccess("删除成功");
	}
	
	@RequestMapping(value="remove",method=RequestMethod.POST)
	public @ResponseBody Object remove(Integer[] ids) throws Exception{
		//for (String id : ids.split(",")) {
		for (Integer id : ids) {//这里 是否需要先判断id的有效性，合法性，先通过id获取entity,在删除entity.
			this.accountService.deleteUser(id);
		}
		return ResponseUtils.sendSuccess("删除成功");
	}
	
	@RequestMapping(value="changePwd",method=RequestMethod.POST)
	public @ResponseBody Object changePwd(@RequestBody ChangePasswordVo changePasswordVO) throws Exception{
		String oldPwd = changePasswordVO.getOldPassword();
		String newPwd = changePasswordVO.getNewPassword();
		String newConfirmPwd = changePasswordVO.getConfirmPassword();
		
		//验证新密码和确认密码是否一致
		if (!newPwd.equals(newConfirmPwd)) {
			return ResponseUtils.sendFailure("失败：新密码和确认新密码不一致");
		}
		
		//从session中获取用户名
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		User user = (User)session.getAttribute("CURRENT_USER");
		byte[] salt = Encodes.decodeHex(user.getSalt());
		//验证旧密码是否正确		 
		byte[] hashPassword = Digests.sha1(oldPwd.getBytes(), salt, 1024);
		if (!user.getPassword().equals(Encodes.encodeHex(hashPassword))) {
			return ResponseUtils.sendFailure("失败：原密码不正确");	
		}
		
		//更新密码
		if (this.accountService.changePwd(user.getId(), newPwd)>0) 
			return ResponseUtils.sendSuccess("修改成功");
		else 
			return ResponseUtils.sendFailure("修改失败");
	}


	/**
	 * 删除用户
	 * 
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
//	@RequiresPermissions("user:edit")
//	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
//	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
//		boolean falg = accountService.deleteUser(id);
//		if (!falg) {
//			redirectAttributes.addFlashAttribute("message", "不能删除超级管理员");
//		} else {
//			redirectAttributes.addFlashAttribute("message", "删除用户成功");
//		}
//		return REDIRECT_SUCCESS_URL;
//	}

	/**
	 * 跳转到注册页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/regist", method = RequestMethod.GET)
	public String registForm() {
		return "regist";
	}

	/**
	 * 注册
	 * 
	 * @param user
	 *            用户信息
	 * @param redirectAttributes
	 * @return
	 */
//	@RequestMapping(value = "/regist", method = RequestMethod.POST)
//	public String regist(User user, RedirectAttributes redirectAttributes) {
//
//		List<Role> roleList = Lists.newArrayList();
//
//		roleList.add(accountService.getRole(Constant.USER_TYPE_APPLY));// 插入roleId为2的角色：申请人角色
//		user.setRoleList(roleList);
//
//		user.setType(Constant.USER_TYPE_APPLY);// 申请人
//
//		User leader = accountService.getUser(user.getLeaderId());// 所属领导信息
		// 领导所属部门.
//		user.setDepartmentId(leader.getDepartmentId());

		// 保存用户信息
//		accountService.saveUser(user);

		// 用户登录
//		Subject subject = SecurityUtils.getSubject();
//		UsernamePasswordToken token = new UsernamePasswordToken(user.getEmail(), user.getPassword());
//		token.setRememberMe(true);
//		subject.login(token);
//
//		redirectAttributes.addFlashAttribute("message", "注册成功!");
//
//		return "redirect:/home/";
//	}

	/**
	 * 验证登陆邮箱是否唯一
	 * 
	 * @param oldEmail
	 * @param email
	 * @return
	 */
//	@RequestMapping(value = "checkEmail")
//	@ResponseBody
//	public String checkEmail(@RequestParam("oldEmail") String oldEmail, @RequestParam("email") String email) {
//
//		if (email.equals(oldEmail) || accountService.findUserByEmail(email) == null) {
//			return "true";
//		}
//		return "false";
//	}

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
