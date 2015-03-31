package com.github.rockysoft.web;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
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
import com.github.rockysoft.entity.Role;
import com.github.rockysoft.entity.User;
import com.github.rockysoft.framework.util.ResponseUtils;
import com.github.rockysoft.service.AccountService;
import com.github.rockysoft.viewmodel.MenuModel;

@Controller
@RequestMapping(value = "/account/menus")
public class MenuController { //extends BaseController {

	private static final int DEFAULT_PAGE_NUM = 0;
	private static final int DEFAULT_PAGE_SIZE = 8;
	private static final String REDIRECT_SUCCESS_URL = "redirect:/account/menus/";

	@Autowired
	private AccountService accountService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Object list(ExtPager pager, @RequestParam(required = false) String text) {
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
		if (StringUtils.isNotBlank(text)) {
			criteria.put("text", text);
		}
		List<Menu> list = this.accountService.GetMenuList(criteria);
//		for (Menu l : list) {
//			System.out.println("menu.text="+l.getText());
//        	System.out.println("menu.sort="+l.getSort());
//        }
		//int total = this.accountService.countMenuByExample(criteria);
//		logger.debug("total:{}", total);
		//return new ExtGridReturn(total, list, true, "获取成功");
		return ResponseUtils.sendTreeGrid(list);
//		return list;
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public @ResponseBody Object get(@PathVariable("id") Integer menuId) throws Exception{
		Menu menu = this.accountService.getMenu(menuId);//  .updateRole(role);
		return ResponseUtils.sendForm(menu);
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.PUT)
	public @ResponseBody Object update(@PathVariable("id") Integer id, @RequestBody MenuModel model) throws Exception{
		
		Menu menu = this.accountService.getMenu(id);
		if (menu == null) {
			return ResponseUtils.sendSuccess("无效的请求");
		}
		
		menu.setParentId(model.getParentId());
		menu.setButtons(listToString(model.getButtons(), ','));
		menu.setText(model.getText());
		menu.setController(model.getController());
		menu.setUrl(model.getUrl());
		menu.setSort(model.getSort());
		this.accountService.updateMenu(menu);
		return ResponseUtils.sendSuccess("保存成功");
	}
	
	public String listToString(List<String> list, char separator) {    
		StringBuilder sb = new StringBuilder();    
		for (int i = 0; i < list.size(); i++) {        
			sb.append(list.get(i)).append(separator);    
		}    
		return sb.toString().substring(0,sb.toString().length()-1);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody Object save(@RequestBody MenuModel model) throws Exception{
		Menu menu = new Menu();
		menu.setParentId(model.getParentId());
		menu.setButtons(listToString(model.getButtons(), ','));
		menu.setText(model.getText());
		menu.setController(model.getController());
		menu.setUrl(model.getUrl());
		menu.setSort(model.getSort());
		this.accountService.saveMenu(menu);
		return ResponseUtils.sendSuccess("保存成功");
	}
	
	@RequestMapping(value="{id}",method=RequestMethod.DELETE)
	public @ResponseBody Object delete(@PathVariable("id") Integer id) throws Exception{
		this.accountService.deleteMenu(id);
		return ResponseUtils.sendSuccess("删除成功");
	}
	
	@RequestMapping(value="remove",method=RequestMethod.POST)
	public @ResponseBody Object remove(Integer[] ids) throws Exception{
		//for (String id : ids.split(",")) {
		for (Integer id : ids) {//这里 是否需要先判断id的有效性，合法性，先通过id获取entity,在删除entity.
			this.accountService.deleteMenu(id);
		}
		return ResponseUtils.sendSuccess("删除成功");
	}
	
	@RequestMapping(value="GetAll", method = RequestMethod.GET)
	public @ResponseBody Object GetMenuTree(String roleId) {
//		Integer roleId =
		Integer id = roleId==null?-1:Integer.parseInt(roleId);
		Criteria criteria = new Criteria();
		return this.accountService.GetMenuTree(criteria, id);
	}
	
	@RequestMapping(value="GetAuthorizedMenus", method = RequestMethod.GET)
	public @ResponseBody Object GetAuthorizedMenuTree() {
		
//		Criteria criteria = new Criteria();
		User user = getCurrentUser();
		if (user == null)
			return ResponseUtils.sendFailure("会话过期！", null);
		Integer userId = user.getId();
		List<Menu> authorizedMenus = this.accountService.GetAuthorizedMenuTree(userId);
		//return ResponseUtils.sendTreeMenu(authorizedMenus);
		return ResponseUtils.sendTreeGrid(authorizedMenus);
//		return this.accountService.GetMenuTree(criteria, "");
	}
	
	 public final User getCurrentUser() {
		 	User user = null;
	        Subject currentUser = SecurityUtils.getSubject();
	        if (null != currentUser) {
	            Session session = currentUser.getSession();
	            if (null != session) {
	                user = (User) session.getAttribute("CURRENT_USER");
	            }
	        }
	        return user;
	  };
	
	
}
