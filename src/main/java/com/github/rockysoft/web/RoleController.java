package com.github.rockysoft.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.rockysoft.entity.Criteria;
import com.github.rockysoft.entity.ExtGridReturn;
import com.github.rockysoft.entity.ExtPager;
import com.github.rockysoft.entity.Menu;
import com.github.rockysoft.entity.Permission;
import com.github.rockysoft.entity.Resource;
import com.github.rockysoft.entity.Role;
import com.github.rockysoft.entity.User;
import com.github.rockysoft.framework.util.ResponseUtils;
import com.github.rockysoft.service.AccountService;

@Controller
@RequestMapping(value = "/account/roles")
public class RoleController {

	private static final int DEFAULT_PAGE_NUM = 0;
	private static final int DEFAULT_PAGE_SIZE = 8;
	private static final String REDIRECT_SUCCESS_URL = "redirect:/account/role/";

	@Autowired
	private AccountService accountService;

//	@RequiresPermissions("role:view")
//	@RequestMapping(value = { "list", "" })
//	public String list(@RequestParam(value = "page", required = false) Integer page, Model model) {
//		int pageNum = page != null ? page : DEFAULT_PAGE_NUM;
//		Page<Role> roles = accountManager.getAllRole(pageNum, DEFAULT_PAGE_SIZE);
//		model.addAttribute("page", roles);
//		return "account/roleList";
//	}
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Object list(ExtPager pager, @RequestParam(required = false) String roleName) {
		Criteria criteria = new Criteria();
		// 设置分页信息
		if (pager.getLimit() != null && pager.getStart() != null) {
//			criteria.setOracleEnd(pager.getLimit() + pager.getStart());
//			criteria.setOracleStart(pager.getStart());
			criteria.setMysqlOffset(pager.getStart());
			criteria.setMysqlLength(pager.getLimit());
		}
		// 排序信息
//		if (StringUtils.isNotBlank(pager.getDir()) && StringUtils.isNotBlank(pager.getSort())) {
//			criteria.setOrderByClause(pager.getSort() + " " + pager.getDir());
//		}
		if (StringUtils.isNotBlank(roleName)) {
			criteria.put("roleNameLike", roleName);
		}
		List<Role> list = this.accountService.selectRoleByExample(criteria);
		int total = this.accountService.countRoleByExample(criteria);
//		logger.debug("total:{}", total);
		return new ExtGridReturn(total, list, true, "获取成功");
	}
	
//	@RequestMapping(value="getAll", method = RequestMethod.GET)
//	@ResponseBody
//	public Object getAll() {
//		Criteria criteria = new Criteria();
//		List<Role> list = this.accountService.selectRoleByExample(criteria);
//		Map<String, Object> map = new HashMap<String, Object>();
//		for (Role role : list) {
//			map.put("ItemValue", role.getId());
//			map.put("ItemText", role.getName());
//		}
//		return ResponseUtils.sendList(map);
//	}
	
	
	@RequestMapping(value="{id}/permissions", method = RequestMethod.GET)
	@ResponseBody
	public Object getPermissionListbyRoleId(ExtPager pager, @PathVariable("id") Integer roleId) {
		Criteria criteria = new Criteria();
		//criteria.put("roleId", roleId);
		// 设置分页信息
		if (pager.getLimit() != null && pager.getStart() != null) {
//			criteria.setOracleEnd(pager.getLimit() + pager.getStart());
//			criteria.setOracleStart(pager.getStart());
			criteria.setMysqlOffset(pager.getStart());
			criteria.setMysqlLength(pager.getLimit());
		}
		// 排序信息
//		if (StringUtils.isNotBlank(pager.getDir()) && StringUtils.isNotBlank(pager.getSort())) {
//			criteria.setOrderByClause(pager.getSort() + " " + pager.getDir());
//		}
		List<Permission> permissionList = this.accountService.getPermissionListbyRoleId(roleId);// .selectRoleByExample(criteria);
		//int total = this.accountService.countPermissionByExample(criteria);// .countRoleByExample(criteria);
		//return new ExtGridReturn(total, list, true, "获取成功");
		return permissionList;
	}
	
	@RequestMapping(value="{id}/permissions", method=RequestMethod.POST)
	public @ResponseBody Object saveRolePermissions(@PathVariable("id") int roleId, String permissionIds) throws Exception{
		this.accountService.saveRolePermissions(roleId, permissionIds);
		return ResponseUtils.sendSuccess("保存成功");
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public @ResponseBody Object get(@PathVariable("id") Integer roleId) throws Exception{
		Role role = this.accountService.getRole(roleId);//  .updateRole(role);
//		return ResponseUtils.sendSuccess("保存成功");
//		return role;
		return ResponseUtils.sendForm(role);
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.PUT)
	public @ResponseBody Object update(@RequestBody Role role) throws Exception{
		this.accountService.updateRole(role);
		return ResponseUtils.sendSuccess("保存成功");
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody Object save(@RequestBody Role role) throws Exception{
		this.accountService.saveRole(role);
		return ResponseUtils.sendSuccess("保存成功");
	}
	
	@RequestMapping(value="{id}",method=RequestMethod.DELETE)
	public @ResponseBody Object delete(@PathVariable("id") Integer roleId) throws Exception{
		this.accountService.deleteRole(roleId);
		return ResponseUtils.sendSuccess("删除成功");
	}
	
	
	
	
	@RequestMapping(value="{id}/menus", method = RequestMethod.GET)
	@ResponseBody
	public Object getMenuListbyRoleId(ExtPager pager, @PathVariable("id") Integer roleId) {
		Criteria criteria = new Criteria();
		//criteria.put("roleId", roleId);
		// 设置分页信息
		if (pager.getLimit() != null && pager.getStart() != null) {
//			criteria.setOracleEnd(pager.getLimit() + pager.getStart());
//			criteria.setOracleStart(pager.getStart());
			criteria.setMysqlOffset(pager.getStart());
			criteria.setMysqlLength(pager.getLimit());
		}
		// 排序信息
//		if (StringUtils.isNotBlank(pager.getDir()) && StringUtils.isNotBlank(pager.getSort())) {
//			criteria.setOrderByClause(pager.getSort() + " " + pager.getDir());
//		}
		List<Menu> resourceList = this.accountService.getMenuListbyRoleId(roleId);// .selectRoleByExample(criteria);
		//int total = this.accountService.countPermissionByExample(criteria);// .countRoleByExample(criteria);
		//return new ExtGridReturn(total, list, true, "获取成功");
		return resourceList;
	}
	
	@RequestMapping(value="{id}/menus", method=RequestMethod.POST)
	public @ResponseBody Object saveRoleMenus(@PathVariable("id") int roleId, String menuIds) throws Exception{
		this.accountService.saveRoleMenus(roleId, menuIds);
		return ResponseUtils.sendSuccess("保存成功");
	}

//	@RequiresPermissions("role:edit")
//	@RequestMapping(value = "/save", method = RequestMethod.GET)
//	public String createForm(Model model) {
//		model.addAttribute("role", new Role());
//		model.addAttribute("allPermissions", Permission.values());
//		return "account/roleForm";
//	}

//	@RequiresPermissions("role:edit")
//	@RequestMapping(value = "/save", method = RequestMethod.POST)
//	public String save(Role role, RedirectAttributes redirectAttributes) {
//		accountManager.saveRole(role);
//		redirectAttributes.addFlashAttribute("message", "创建权限组 " + role.getName() + " 成功");
//		return REDIRECT_SUCCESS_URL;
//	}
//
//	@RequiresPermissions("role:edit")
//	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
//	public String updateForm(@PathVariable("id") Integer id, Model model) {
//		model.addAttribute("allPermissions", Permission.values());
//		model.addAttribute("role", accountManager.getRole(id));
//		return "account/roleForm";
//	}
//
//	@RequiresPermissions("role:edit")
//	@RequestMapping(value = "/update", method = RequestMethod.POST)
//	public String update(@ModelAttribute("role") Role role, RedirectAttributes redirectAttributes) {
//		accountManager.saveRole(role);
//		redirectAttributes.addFlashAttribute("message", "修改权限组 " + role.getName() + " 成功");
//		return REDIRECT_SUCCESS_URL;
//	}
//
//	@RequiresPermissions("role:edit")
//	@RequestMapping(value = "delete/{id}")
//	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
//
//		boolean falg = accountManager.deleteRole(id);
//		if (!falg) {
//			redirectAttributes.addFlashAttribute("message", "不能删除默认权限组!");
//		} else {
//			redirectAttributes.addFlashAttribute("message", "删除权限组成功");
//		}
//		return REDIRECT_SUCCESS_URL;
//	}
//
//	@RequiresPermissions("role:edit")
//	@RequestMapping(value = "checkRoleName")
//	public @ResponseBody
//	String checkRoleName(@RequestParam("oldName") String oldName, @RequestParam("name") String name) {
//		if (name.equals(oldName) || accountManager.findRoleByName(name) == null) {
//			return "true";
//		}
//		return "false";
//	}

}
