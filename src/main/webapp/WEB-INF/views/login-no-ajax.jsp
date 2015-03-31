<%@page language="java"  pageEncoding="utf-8"%>
<%@page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@page import="org.apache.shiro.authc.AuthenticationException"%>
<%@page import="org.apache.shiro.authc.UnknownAccountException"%>
<%@page import="org.apache.shiro.authc.IncorrectCredentialsException"%> 
<html>
  <head>    
    <title>登录 - Simple</title>
    <link rel="shortcut icon" href="favicon.ico">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"> 
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="Heat">
		<meta http-equiv="description" content="Heat">
		<!-- css -->
		<link rel="stylesheet" type="text/css" href="http://cdn.staticfile.org/extjs/4.2.1/resources/css/ext-all-neptune-debug.css" />
		<style type="text/css">
			.app-logo {background:url(static/images/ananymous_user.jpg) no-repeat left center; height:100%; padding-left:120px; font-size:30px; font-weight:bold; font-family:华文隶书,sans-serif; line-height:100px;}
		</style>	
		<!-- js -->
		<script type="text/javascript" src="http://cdn.staticfile.org/extjs/4.2.1/ext-all-debug.js"></script>
		<script type="text/javascript" src="http://cdn.staticfile.org/extjs/4.2.1/locale/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="static/admin/login.js"></script>
  </head>
  <%
  String error = (String) request.getAttribute( org.apache.shiro.web.filter.authc.FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME); 
  Boolean isShowCaptcha = (Boolean) session.getAttribute( com.github.rockysoft.common.shiro.FormAuthenticationCaptchaFilter.DEFAULT_SHOW_CAPTCHA_KEY_ATTRIBUTE); 
  if (isShowCaptcha == null)  
        {  
            isShowCaptcha = false; 
        }
  String msg = "";
   		        if (error != null) {
		            if ("org.apache.shiro.authc.UnknownAccountException".equals(error))
		                msg = "未知帐号错误！";
		            else if ("org.apache.shiro.authc.IncorrectCredentialsException"
		                    .equals(error))
		                msg = "密码错误！";
	            else if ("org.apache.shiro.authc.AuthenticationException"
		                    .equals(error))
		                msg = "认证失败！";
		            else if ("org.apache.shiro.authc.DisabledAccountException"
		                    .equals(error))
		                msg = "账号被冻结！"; 
		           else if ("org.apache.shiro.authc.AccountException"
		                    .equals(error))
		                msg = "账号错误！";
		           else if ("com.github.rockysoft.common.shiro.CaptchaException"
		                    .equals(error))
		                msg = "验证码错误！";
		           else   
		                msg = "其它未知错误："+error;
		         }
%>
  <body style="background-color:lightblue;">
  <div><%= msg  %></div>
  <div id="tr" style="margin-top: 200px;margin-left: 500px" ></div>
  </body>
  <script type="text/javascript">
  	var isShowCaptcha=false;
  	<%if (isShowCaptcha) {%>
  		isShowCaptcha=true;
  	<%}%>
		Ext.BLANK_IMAGE_URL = "static/images/s.gif";
		Ext.tip.QuickTipManager.init();
		Ext.onReady(function() {
		loginPage = Ext.create("Hope.app.login", {showCaptcha:isShowCaptcha});
		loginPage.show(Ext.getBody());
		});
	</script>
</html>
