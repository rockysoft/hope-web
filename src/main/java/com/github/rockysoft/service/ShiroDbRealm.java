package com.github.rockysoft.service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.rockysoft.common.shiro.CaptchaException;
import com.github.rockysoft.common.shiro.UsernamePasswordCaptchaToken;
import com.github.rockysoft.constant.AccountConstant;
import com.github.rockysoft.entity.Permission;
import com.github.rockysoft.entity.Principal;
import com.github.rockysoft.entity.Role;
import com.github.rockysoft.entity.User;
import com.github.rockysoft.framework.utils.Encodes;
import com.github.rockysoft.mapper.PermissionMapper;
import com.github.rockysoft.mapper.RoleMapper;
import com.github.rockysoft.mapper.UserMapper;

import com.google.common.base.Objects;
//import com.sobey.cmop.mvc.constant.AccountConstant;
//import com.sobey.cmop.mvc.entity.Group;
//import com.sobey.cmop.mvc.entity.User;
//import com.sobey.framework.utils.Encodes;
import com.google.common.collect.Lists;

/**
 * 自实现用户与权限查询.
 * 
 * @author liukai
 * 
 */
public class ShiroDbRealm extends AuthorizingRealm {

	private AccountService accountService;

	@Resource
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
//	@Autowired
//	private UserMapper userMapper;
//	@Autowired
//	private RoleMapper roleMapper;
//	@Autowired
//	private PermissionMapper permissionMapper;
	
	/**
	 * 认证回调函数, 登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
//		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		UsernamePasswordCaptchaToken token = (UsernamePasswordCaptchaToken) authcToken;
		String loginName = token.getUsername();
		if (loginName == null) {
		    throw new AccountException("Null usernames are not allowed by this realm.");
		}
		// 增加判断验证码逻辑
		/*
		String captcha = token.getCaptcha();
		String exitCode = (String) SecurityUtils.getSubject().getSession().getAttribute(AccountConstant.KEY_CAPTCHA);
		if (null == captcha || !captcha.equalsIgnoreCase(exitCode)) {
		    throw new CaptchaException("验证码错误");
		}
		*/
//		System.out.println("======>>>>>>>>>>>>>>>>>>>>>>>>>>>>1111111111111111");
//		System.out.println("======>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+loginName);
		User user = accountService.findUserByLoginName(loginName);
//		User user = userMapper.findUserByLoginName(loginName);
//		System.out.println("======>>>>>>>>>>>>>>>>>>>>>>>>>>>>2222222222222222");
//		System.out.println("======>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+loginName);
//		if (null == user) {System.out.println("======>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+loginName);
//			throw new UnknownAccountException("No account found for user [" + loginName + "]");
//	    }
//		System.out.println("user.getLoginName()======"+user.getLoginName());
//		System.out.println("ShiroDbRealm======>>>>>>>>>>>>>>>>>>>>>>>>>>>>loginName:"+loginName); 
//		System.out.println("ShiroDbRealm======>>>>>>>>>>>>>>>>>>>>>>>>>>>>getPassword:"+user.getPassword());
		token.setRememberMe(true);// 默认为自动登录.

		if (user != null) {
			if (user.getStatus()!=1){
				throw new AccountException("账号状态异常，请联系管理员.");
			}
				
			String saltStr = user.getSalt();
			if (saltStr == null) {
				throw new AccountException("Null salts are not allowed by this realm.");
			}
			byte[] salt = Encodes.decodeHex(saltStr);//System.out.println("ShiroDbRealm======>>>>>>>>>>>>>>>>>>>>>>>>>>>>saltStr:"+saltStr); 
			return new SimpleAuthenticationInfo(new Principal(user), user.getPassword(), ByteSource.Util.bytes(salt), getName());
		} else {
			throw new UnknownAccountException("No account found for user [" + loginName + "]");
//			return null;
		}
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Principal principal = (Principal) principals.getPrimaryPrincipal();
		User currentUser = principal.getUser();
		List<String> permissionNameList = accountService.getPermissionsByUserId(currentUser.getId());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		if (permissionNameList!=null && !permissionNameList.isEmpty()) {
			info.addStringPermissions(permissionNameList);
			return info;
		} else {
			return null;
		}
	}

	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		//AccountConstant.HASH_ALGORITHM
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher("SHA-1");
//		AccountConstant.HASH_INTERATIONS
		matcher.setHashIterations(1024);
//		matcher.setHashSalted(true);
		setCredentialsMatcher(matcher);
	}

	/**
	 * 更新用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清除所有用户授权信息缓存.
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}

	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable {
		private static final long serialVersionUID = -1181844008511965270L;
		private int id;
		private String loginName;
		private String name;

		public ShiroUser(int id, String loginName, String name) {
			this.id = id;
			this.loginName = loginName;
			this.name = name;
		}

		public int getId() {
			return id;
		}
		
		public String getLoginName() {
			return loginName;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return loginName;
		}

		/**
		 * 重载hashCode,只计算loginName;
		 */
		@Override
		public int hashCode() {
			return Objects.hashCode(loginName);
		}

		/**
		 * 重载equals,只计算loginName;
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
//			Principal principal = (Principal) principal;
			ShiroUser other = (ShiroUser) obj;
			if (loginName == null) {
				if (other.loginName != null) {
					return false;
				}
			} else if (!loginName.equals(other.loginName)) {
				return false;
			}
			return true;
		}

	}
}
