package com.github.rockysoft.web;

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
import com.github.rockysoft.entity.ExtGridReturn;
import com.github.rockysoft.entity.ExtPager;
import com.github.rockysoft.entity.LoginLog;
import com.github.rockysoft.framework.util.ResponseUtils;
import com.github.rockysoft.service.AccountService;
import com.github.rockysoft.service.LoginLogService;

@Controller
@RequestMapping(value = "/account/loginLogs")
public class LoginLogController {

	private static final int DEFAULT_PAGE_NUM = 0;
	private static final int DEFAULT_PAGE_SIZE = 8;
	private static final String REDIRECT_SUCCESS_URL = "redirect:/account/loginLogs/";

	@Autowired
	private LoginLogService loginLogService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Object list(ExtPager pager, @RequestParam(required = false) String permissionName) {
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
		if (StringUtils.isNotBlank(permissionName)) {
			criteria.put("permissionNameLike", permissionName);
		}
		List<LoginLog> list = this.loginLogService.selectLogByExample(criteria);
		int total = this.loginLogService.countLogByExample(criteria);
//		logger.debug("total:{}", total);
		return new ExtGridReturn(total, list, true, "获取成功");
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public @ResponseBody Object get(@PathVariable("id") Integer permissionId) throws Exception{
		LoginLog loginLog = this.loginLogService.getLog(permissionId);//  .updateRole(role);
//		return ResponseUtils.sendSuccess("保存成功");
//		return role;
		return ResponseUtils.sendForm(loginLog);
	}
}
