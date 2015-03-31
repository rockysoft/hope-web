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
import com.github.rockysoft.entity.TreeNode;
import com.github.rockysoft.framework.util.ResponseUtils;
import com.github.rockysoft.service.AccountService;

@Controller
@RequestMapping(value = "/account/departments")
public class DepartmentController {

	private static final int DEFAULT_PAGE_NUM = 0;
	private static final int DEFAULT_PAGE_SIZE = 8;
	private static final String REDIRECT_SUCCESS_URL = "redirect:/account/resources/";

	@Autowired
	private AccountService accountService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Object list(ExtPager pager, @RequestParam(required = false) String resourceName) {
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
		if (StringUtils.isNotBlank(resourceName)) {
			criteria.put("resourceNameLike", resourceName);
		}
		List<Department> list = this.accountService.selectDepartmentByExample(criteria);
		int total = this.accountService.countDepartmentByExample(criteria);
//		logger.debug("total:{}", total);
		return new ExtGridReturn(total, list, true, "获取成功");
	}
	
	
	@RequestMapping(value="{id}", method=RequestMethod.PUT)
	public @ResponseBody Object update(@RequestBody Department department) throws Exception{
		this.accountService.updateDepartment(department);
		return ResponseUtils.sendSuccess("保存成功");
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody Object save(@RequestBody Department department) throws Exception{
		this.accountService.saveDepartment(department);
		return ResponseUtils.sendSuccess("保存成功");
	}
	
	@RequestMapping(value="{id}",method=RequestMethod.DELETE)
	public @ResponseBody Object delete(@PathVariable("id") String departmentId) throws Exception{
		this.accountService.deleteDepartment(departmentId);
		return ResponseUtils.sendSuccess("删除成功");
	}
	
	@RequestMapping(value="deptTree", method = RequestMethod.GET)
	@ResponseBody 
	public Object getTree(){ 
		List<TreeNode> treeNodes = new ArrayList<TreeNode>(); 
		Criteria criteria = new Criteria();
		List<Department> depts = this.accountService.selectDepartmentByExample(criteria); 
		treeNodes = buildtree(depts, null); 
		//return treeNodes; 
		return new ExtGridReturn(0, treeNodes, true, "获取成功");
	}
	
	
	public static List<TreeNode> buildtree(List<Department> depts, Integer id) {
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		for (Department dept : depts) {System.out.println("id=>"+id);System.out.println("DeptParentId=>"+dept.getDeptParentId());
		if (id == null)	{
			if (dept.getDeptParentId() == null) {
				System.out.println("DeptParentId="+dept.getDeptParentId());
				System.out.println("DeptId="+dept.getDeptId());
				TreeNode node = new TreeNode();
				node.setId(dept.getDeptId());
				node.setText(dept.getDeptName());
				node.setLeaf(dept.getLeaf() == 1 ? true : false);
//				node.setIconCls(iconCls);
				node.setChildren(buildtree(depts, node.getId()));
				treeNodes.add(node);
			} else {
				continue;
			}
		} else {
			if (dept.getDeptParentId() == null) {
				continue;
			} else {
				if (id.equals(dept.getDeptParentId())){ //如果是根节点
					System.out.println("DeptParentId="+dept.getDeptParentId());
					System.out.println("DeptId="+dept.getDeptId());
					TreeNode node = new TreeNode();
					node.setId(dept.getDeptId());
					node.setText(dept.getDeptName());
					node.setLeaf(dept.getLeaf() == 1 ? true : false);
//					node.setIconCls(iconCls);
					node.setChildren(buildtree(depts, node.getId()));
					treeNodes.add(node);
				}
			}
		}
			
		}
		return treeNodes;
	}
	
}
