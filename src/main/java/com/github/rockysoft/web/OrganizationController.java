package com.github.rockysoft.web;

import java.util.ArrayList;
import java.util.List;

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
import com.github.rockysoft.entity.Department;
import com.github.rockysoft.entity.ExtGridReturn;
import com.github.rockysoft.entity.ExtPager;
import com.github.rockysoft.entity.Menu;
import com.github.rockysoft.entity.Organization;
import com.github.rockysoft.entity.TreeNode;
import com.github.rockysoft.framework.util.ResponseUtils;
import com.github.rockysoft.service.AccountService;

@Controller
@RequestMapping(value = "/account/organizations")
public class OrganizationController {

	private static final int DEFAULT_PAGE_NUM = 0;
	private static final int DEFAULT_PAGE_SIZE = 8;
	private static final String REDIRECT_SUCCESS_URL = "redirect:/account/organizations/";

	@Autowired
	private AccountService accountService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Object list(ExtPager pager, @RequestParam(required = false) String orgName) {
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
		if (StringUtils.isNotBlank(orgName)) {
			criteria.put("orgNameLike", orgName);
		}
//		List<Organization> list = this.accountService.selectOrganizationByExample(criteria);
		List<Organization> list = this.accountService.GetOrganizationList(criteria);
//		int total = this.accountService.countDepartmentByExample(criteria);
//		logger.debug("total:{}", total);
//		return new ExtGridReturn(total, list, true, "获取成功");
		return ResponseUtils.sendTreeGrid(list);
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public @ResponseBody Object get(@PathVariable("id") Integer organizationId) throws Exception{
		Organization organization = this.accountService.getOrganization(organizationId);//  .updateRole(role);
		return ResponseUtils.sendForm(organization);
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.PUT)
	public @ResponseBody Object update(@RequestBody Organization organization) throws Exception{
		this.accountService.updateOrganization(organization);
		return ResponseUtils.sendSuccess("保存成功");
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody Object save(@RequestBody Organization organization) throws Exception{
		this.accountService.saveOrganization(organization);
		return ResponseUtils.sendSuccess("保存成功");
	}
	
	@RequestMapping(value="{id}",method=RequestMethod.DELETE)
	public @ResponseBody Object delete(@PathVariable("id") Integer organizationId) throws Exception{
		this.accountService.deleteOrganization(organizationId);
		return ResponseUtils.sendSuccess("删除成功");
	}
	
	@RequestMapping(value="remove",method=RequestMethod.POST)
	public @ResponseBody Object remove(Integer[] ids) throws Exception{
		//for (String id : ids.split(",")) {
		for (Integer id : ids) {//这里 是否需要先判断id的有效性，合法性，先通过id获取entity,在删除entity.
			this.accountService.deleteOrganization(id);
		}
		return ResponseUtils.sendSuccess("删除成功");
	}
	
	@RequestMapping(value="deptTree", method = RequestMethod.GET)
	@ResponseBody 
	public Object getTree(){ 
		List<TreeNode> treeNodes = new ArrayList<TreeNode>(); 
		Criteria criteria = new Criteria();
		List<Organization> orgs = this.accountService.selectOrganizationByExample(criteria); 
		treeNodes = buildtree(orgs, null); 
		//return treeNodes; 
		return new ExtGridReturn(0, treeNodes, true, "获取成功");
	}
	
	
	public static List<TreeNode> buildtree(List<Organization> orgs, Integer id) {
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		for (Organization org : orgs) {
		if (id == null)	{
			if (org.getParentId() == null) {
				TreeNode node = new TreeNode();
				node.setId(org.getId());
				node.setText(org.getName());
				node.setLeaf(org.getLeaf() == 1 ? true : false);
//				node.setIconCls(iconCls);
				node.setChildren(buildtree(orgs, node.getId()));
				treeNodes.add(node);
			} else {
				continue;
			}
		} else {
			if (org.getParentId() == null) {
				continue;
			} else {
				if (id.equals(org.getParentId())){ //如果是根节点
					TreeNode node = new TreeNode();
					node.setId(org.getId());
					node.setText(org.getName());
					node.setLeaf(org.getLeaf() == 1 ? true : false);
//					node.setIconCls(iconCls);
					node.setChildren(buildtree(orgs, node.getId()));
					treeNodes.add(node);
				}
			}
		}
			
		}
		return treeNodes;
	}
	
}
