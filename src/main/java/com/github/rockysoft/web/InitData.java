package com.github.rockysoft.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.rockysoft.common.utils.JsonUtils;
import com.github.rockysoft.entity.MyInfo;
import com.github.rockysoft.entity.Role;
import com.github.rockysoft.entity.SysInfo;
import com.github.rockysoft.entity.User;
import com.github.rockysoft.service.AccountService;

@Controller
public class InitData {
	
	@Autowired
	private AccountService accountService;

	@RequestMapping(value = "/privateInfo", produces = {"application/javascript"}, method = RequestMethod.GET)
	public @ResponseBody String init() {

		User currentUser = accountService.getCurrentUser();
		
		List<String> actions = accountService.getPermissionsByUserId(currentUser.getId());
		
		/*
		List<String> actions = new ArrayList<String>(); 
		actions.add("RoleAdd");
		actions.add("RoleEdit");
		actions.add("RoleDelete");
		actions.add("TeamAdd");
		actions.add("TeamEdit");
		actions.add("TeamDelete");
		actions.add("UserAdd");
		actions.add("UserEdit");
		actions.add("UserDelete");
		actions.add("UserExport");
		actions.add("RoleActionAdd");
		actions.add("RoleActionEdit");
		actions.add("RoleActionDelete");
		actions.add("RoleMenuAdd");
		actions.add("RoleMenuEdit");
		actions.add("RoleMenuDelete");
		actions.add("RoleActionExport");
		actions.add("RoleExport");		
		actions.add("LogExport");
		actions.add("MessageAdd");
		actions.add("MessageDelete");
		actions.add("MessageMultiSend");
		actions.add("MessageGroupSend");
		actions.add("RoleSaveRoleAction");
		actions.add("NewsColumnAdd");
		actions.add("NewsColumnEdit");
		actions.add("NewsColumnDelete");
		actions.add("NewsAdd");
		actions.add("NewsEdit");
		actions.add("SysConfigAdd");
		actions.add("SysConfigEdit");
		actions.add("SysConfigDelete");
		actions.add("AdAdd");
		actions.add("AdEdit");
		actions.add("AdDelete");
		actions.add("RoleAssignColumns");
		*/
		List<String> roles = new ArrayList<String>();
		for (Role r : currentUser.getRoleList())
			roles.add(r.getName());//中文乱码
		
		List<String> roleColumns = new ArrayList<String>();
		roleColumns.add("News");
		roleColumns.add("AddName");
		roleColumns.add("AddTime");
		roleColumns.add("IsEnable");
		roleColumns.add("IsTop");
		roleColumns.add("LastEditName");
		roleColumns.add("LastEditTime");
		roleColumns.add("Source");
		roleColumns.add("Tags");
		roleColumns.add("Title");

		MyInfo myInfo = new MyInfo();
		myInfo.setCurIp(currentUser.getLastLoginIp());
		myInfo.setLoginName(currentUser.getLoginName());
		myInfo.setPrevLoginIp(currentUser.getLastLoginIp());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		myInfo.setPrevLoginDate(currentUser.getLastLoginTime()==null ? "":sdf.format(currentUser.getLastLoginTime()));
		myInfo.setButtonAlign("center");
		myInfo.setActions(actions);
		myInfo.setRoles(roles);
		myInfo.setRoleColumns(roleColumns);
		
		SysInfo sysInfo = new SysInfo();
		sysInfo.setErrLogAllowedRoles("administrators");
		
		Map<String, Object> iData = new HashMap<String, Object>();
		iData.put("sysInfo", sysInfo);
		iData.put("myInfo", myInfo);
		String result = "var idata = " + JsonUtils.map2json(iData);
		
        return result;
	}
	
	/*
	private GetMyInfo()
    {
        var curUser = CurrentUser.GetInstance;

        var actions = new List<string>();
        curUser.Actions.ToList().ForEach(action => actions.Add(action.ActionName));

        var roles = new List<string>();
        curUser.UserInfo.Roles.ForEach(role => roles.Add(role.RoleCode));

        var roleIds = curUser.UserInfo.Roles.Select(p => p.Id).ToList();
        var roleColumns = RoleModel.GetInstance.GetRoleColumns(roleIds);

        dynamic myInfo = new ExpandoObject();
        myInfo.curIp = curUser.CurIp;
        myInfo.loginName = curUser.UserInfo.LoginName;
        myInfo.prevLoginIp = curUser.PrevLoginIp;
        myInfo.prevLoginDate = curUser.PrevLoginDate;
        myInfo.buttonAlign = curUser.UserInfo.ButtonAlign;
        myInfo.actions = actions;
        myInfo.roles = roles;
        myInfo.roleColumns = roleColumns;

        return myInfo;
    }

    private GetSettings()
    {
        dynamic sysInfo = new ExpandoObject();
        sysInfo.errLogAllowedRoles = SysConfig.GetAppSetting("elmah.mvc.allowedRoles");

        return sysInfo;
    }
    */
}
