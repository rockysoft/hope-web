<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
	//String extLibPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/hope-web/static/ext";
	String extLibPath = "http://cdn.staticfile.org/extjs/4.2.1";//qiniu
	//String extLibPath = "http://cdn.sencha.com/ext/gpl/4.2.1";//office
	String ctx = request.getContextPath();
	pageContext.setAttribute("extLibPath", extLibPath);
	pageContext.setAttribute("ctx", ctx);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>业务基础平台</title>
		<link rel="shortcut icon" href="favicon.ico">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/ext-ux/portal/css/portal.css" />
	</head>
	<body>
		<!--
		<div id="loading-tip" style="border-radius:3px;position: absolute;z-index: 1;border: solid 1px #ccc;background-color: #fff;padding: 10px;">
			<div class="loading-indicator" style="color: #444;font: bold 13px tahoma, arial, helvetica;padding: 10px;height: auto;">
				<img src="${ctx}/static/images/loading32.gif" width="31" height="31"
					style="margin-right: 8px; float: left; vertical-align: top;" />
				业务基础平台V1.0
				<br />
				<span id="loading-msg" style="font: normal 10px arial, tahoma, sans-serif;">加载样式和图片...</span>
			</div>
		</div>
		-->
		<script type="text/javascript">
			var extLibPath = "${extLibPath}";
			var ctx = "${ctx}";
			//var tip = document.getElementById("loading-tip");
			//tip.style.top = (document.body.scrollHeight - tip.style.top - 100) / 2;
			//tip.style.left = (document.body.scrollWidth - 200) / 2;
		</script>
		<link rel="stylesheet" type="text/css" href="${extLibPath}/resources/css/ext-all.css" />
		<!--
		<link rel="stylesheet" type="text/css" href="/resources/css/ext-all-neptune.css" />
		-->
		<link rel="stylesheet" type="text/css" href="${ctx}/static/css/icon.css" />
		<!--
		<link rel="stylesheet" type="text/css" href="${ctx}/static/example.css" />
		-->
		<link rel="stylesheet" type="text/css" href="${ctx}/static/css/hope.css" />
		<script type="text/javascript">
			//document.getElementById("loading-msg").innerHTML = "加载核心组件...";
		</script>
		<script type="text/javascript" src="${extLibPath}/bootstrap.js" ></script> 
		<!--     
		<script type="text/javascript" src=" /ext-all-dev.js"></script>
		-->
		<script type="text/javascript" src="${extLibPath}/locale/ext-lang-zh_CN.js"></script>
		<!--
		<script type="text/javascript" src="/ext-theme-neptune.js"></script>
		-->
		<script type="text/javascript" src="${ctx}/static/ext-override.js"></script>
		<script type="text/javascript" src="${ctx}/privateInfo"></script>
		<!--
		<script type="text/javascript" src="${ctx}/static/examples.js"></script>
		-->
		 
		 <script type="text/javascript">
        var appName = '业务基础系统';
        var appVersion = 'v1.0';
        var appBaseUri = '/hope-web/';
        Ext.Loader.setPath({
            'Ext.ux': 'static/ext-ux',
            'Hope.app': 'static/admin'
        });
            //全局分页大小
    var globalPageSize=20,
    //全局时间列宽度
    globalDateColumnWidth=160;

    /* 
    var idata = {
  "sysInfo": {
    "errLogAllowedRoles": "administrators"
  },
  "myInfo": {
    "curIp": "114.252.38.218",
    "loginName": "administrator",
    "prevLoginIp": "114.252.38.218",
    "prevLoginDate": "2014-09-20 10:19:01",
    "buttonAlign": "center",
    "actions": [
      "RoleAdd",
      "RoleEdit",
      "RoleDelete",
      "OrganizationAdd",
      "OrganizationEdit",
      "OrganizationDelete",
      "UserAdd",
      "UserEdit",
      "UserDelete",
      "UserExport",
      "RoleActionAdd",
      "RoleActionEdit",
      "RoleActionDelete",
      "RoleMenuAdd",
      "RoleMenuEdit",
      "RoleMenuDelete",
      "RoleActionExport",
      "RoleExport",
      "LogExport",
      "MessageAdd",
      "MessageDelete",
      "MessageMultiSend",
      "MessageGroupSend",
      "RoleSaveRoleAction",
      "NewsColumnAdd",
      "NewsColumnEdit",
      "NewsColumnDelete",
      "NewsAdd",
      "NewsEdit",
      "SysConfigAdd",
      "SysConfigEdit",
      "SysConfigDelete",
      "AdAdd",
      "AdEdit",
      "AdDelete",
      "RoleAssignColumns"
    ],
    "roles": [
      "administrators"
    ],
    "roleColumns": {
      "News": [
        "AddName",
        "AddTime",
        "IsEnable",
        "IsTop",
        "LastEditName",
        "LastEditTime",
        "Source",
        "Tags",
        "Title"
      ]
    }
  }
}
    */
    </script>
		<script type="text/javascript" src="${ctx}/static/admin/app.js"></script>
	</body>
</html>
