package com.github.rockysoft.common.shiro;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.rockysoft.entity.User;
import com.github.rockysoft.service.AccountService;
import com.github.rockysoft.service.ShiroDbRealm.ShiroUser;

public class FormAuthenticationCaptchaFilter extends FormAuthenticationFilter {
	
	/** 
     * 默认验证码参数名称 
     */  
	private static final String DEFAULT_CAPTCHA_PARAM = "captcha";
	
	/** 
     * 登录次数超出allowLoginNum时，存储在session记录是否展示验证码的key默认名称 
     */  
    public static final String DEFAULT_SHOW_CAPTCHA_KEY_ATTRIBUTE = "showCaptcha";
    
    /** 
     * 默认在session中存储的登录次数名称 
     */  
    private static final String DEFAULT_LOGIN_NUM_KEY_ATTRIBUTE = "loginNum";  
 
    private String captchaParam = DEFAULT_CAPTCHA_PARAM;
    
 // 在session中的存储验证码的key名称  
    private String sessionCaptchaKeyAttribute = "SE_KEY_MM_CODE";//DEFAULT_CAPTCHA_PARAM;
    
    // 在session中存储的登录次数名称  
    private String loginNumKeyAttribute = DEFAULT_LOGIN_NUM_KEY_ATTRIBUTE;
    
    // 登录次数超出allowLoginNum时，存储在session记录是否展示验证码的key名称  
    private String sessionShowCaptchaKeyAttribute = DEFAULT_SHOW_CAPTCHA_KEY_ATTRIBUTE;
    
    // 允许登录次数，当登录次数大于该数值时，会在页面中显示验证码  
    private Integer allowLoginNum = 3;
    
    @Autowired  
    private AccountService accountService; 
 
    /** 
     * 获取验证码提交的参数名称 
     *  
     * @return String 
     */  
    public String getCaptchaParam() {
 
        return captchaParam;
 
    }
    
    /** 
     * 获取设置在session中的存储验证码的key名称 
     *  
     * @return Sting 
     */  
    public String getSessionCaptchaKeyAttribute()  
    {  
        return sessionCaptchaKeyAttribute;  
    }
    
    /** 
     * 设置在session中的存储验证码的key名称 
     *  
     * @param sessionCaptchaKeyAttribute 
     *            存储验证码的key名称 
     */  
    public void setSessionCaptchaKeyAttribute(String sessionCaptchaKeyAttribute)  
    {  
        this.sessionCaptchaKeyAttribute = sessionCaptchaKeyAttribute;  
    }
    
    /** 
     * 获取在session中存储的登录次数名称 
     *  
     * @return Stromg 
     */  
    public String getLoginNumKeyAttribute()  
    {  
        return loginNumKeyAttribute;  
    }
    
    /** 
     * 设置在session中存储的登录次数名称 
     *  
     * @param loginNumKeyAttribute 
     *            登录次数名称 
     */  
    public void setLoginNumKeyAttribute(String loginNumKeyAttribute)  
    {  
        this.loginNumKeyAttribute = loginNumKeyAttribute;  
    } 
 
    /** 
     * 获取用户输入的验证码 
     *  
     * @param request 
     *            ServletRequest 
     *  
     * @return String 
     */  
    protected String getCaptcha(ServletRequest request) {
 
        return WebUtils.getCleanParam(request, getCaptchaParam());
 
    }
    
    /** 
     * 获取登录次数超出allowLoginNum时，存储在session记录是否展示验证码的key名称 
     *  
     * @return String 
     */  
    public String getSessionShowCaptchaKeyAttribute()  
    {  
        return sessionShowCaptchaKeyAttribute;  
    }  
    
    /** 
     * 获取允许登录次数 
     *  
     * @return Integer 
     */  
    public Integer getAllowLoginNum()  
    {  
        return allowLoginNum;  
    }  
  
    /** 
     * 设置允许登录次数，当登录次数大于该数值时，会在页面中显示验证码 
     *  
     * @param allowLoginNum 
     *            允许登录次数 
     */  
    public void setAllowLoginNum(Integer allowLoginNum)  
    {  
        this.allowLoginNum = allowLoginNum;  
    }
    
    /** 
     * 重写父类方法，在shiro执行登录时先对比验证码，正确后在登录，否则直接登录失败 
     */  
    @Override  
    protected boolean executeLogin(ServletRequest request,  
            ServletResponse response) throws Exception  
    {  
  
        Session session = getSubject(request, response).getSession();  
        // 获取登录次数  
        Integer number = (Integer) session  
                .getAttribute(getLoginNumKeyAttribute());  
  
        // 首次登录，将该数量记录在session中  
        if (number == null)  
        {  
            number = new Integer(1);  
            session.setAttribute(getLoginNumKeyAttribute(), number);  
        }
        
     // 如果失败登录次数大于allowLoginNum时，验证验证码  
        else if (number > getAllowLoginNum() - 1)  
        {  
        // 获取当前验证码  
        String currentCaptcha = (String) session.getAttribute(getSessionCaptchaKeyAttribute());  
        // 获取用户输入的验证码  
        String submitCaptcha = getCaptcha(request);  
        // 如果验证码不匹配，登录失败  

        if ((submitCaptcha == null || submitCaptcha.length() == 0)  
                || !currentCaptcha.equalsIgnoreCase(submitCaptcha))  
        {  
            return onLoginFailure(this.createToken(request, response),  
                    new AccountException("验证码不正确"), request, response);  
        }  
        }
        return super.executeLogin(request, response);  
    }
  
    /** 
     * 重写父类方法，当登录失败将异常信息设置到request的attribute中 
     */  
    @Override  
    protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {  
        if (ae instanceof IncorrectCredentialsException)  
        {  
            request.setAttribute(getFailureKeyAttribute(), "用户名密码不正确");  
        } else  
        {  
            request.setAttribute(getFailureKeyAttribute(), ae.getMessage());  
        }  
  
    }  
    
    /** 
     * 重写父类方法，当登录成功后，将allowLoginNum（允许登录次）设置为0，重置下一次登录的状态 
     */  
    @Override  
    protected boolean onLoginSuccess(AuthenticationToken token,  
            Subject subject, ServletRequest request, ServletResponse response)  
            throws Exception {  
        Session session = subject.getSession(false);  
  
        session.removeAttribute(getLoginNumKeyAttribute());  
        session.removeAttribute(getSessionShowCaptchaKeyAttribute());  
  
        /*
        CommonVariableModel cvm = (CommonVariableModel) subject.getPrincipal();  
        String role = null;  
        if (cvm.getUser() != null)  
        {  
            role = cvm.getUser().getRole();  
        } else if (cvm.getManager() != null)  
        {  
            role = cvm.getManager().getRole();  
        }  
        HttpUser user = new HttpUser(role);  
        ManagerRole role1 = managerRoleService.findByCode(role); 
        user.appendRole(((UsernamePasswordToken) token).getUsername(),  
                role1.getId());  
        session.setAttribute(CommonAction.MANAGER_SESSION_CODE, user);  
        */
        ShiroUser su = (ShiroUser)subject.getPrincipal();
        User user = accountService.findUserByLoginName(su.getLoginName()); 
        session.setAttribute("CURRENT_USER", user);
        
		//不是ajax请求
		if (!"XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request)
				.getHeader("X-Requested-With"))) {
			//issueSuccessRedirect(request, response);
			return super.onLoginSuccess(token, subject, request, response);
		}        
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("{success:true,message:'登入成功'}");
        out.flush();
        out.close();
        return false; 
    }
    
    /** 
     * 重写父类方法，当登录失败次数大于allowLoginNum（允许登录次）时，将显示验证码 
     */  
	@Override
	protected boolean onLoginFailure(AuthenticationToken token,
			AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		// try
		// {
		// WebUtils.issueRedirect(request, response, getLoginUrl());
		// } catch (IOException e1)
		// {
		//
		// e1.printStackTrace();
		// }

		Session session = getSubject(request, response).getSession(false);

		Integer number = (Integer) session
				.getAttribute(getLoginNumKeyAttribute());
		// System.out.println("0----------------------------------->"+number);
		// 如果失败登录次数大于allowLoginNum时，展示验证码
		if (number > getAllowLoginNum() - 1) { // System.out.println("1----------------------------------->"+number);
			session.setAttribute(getSessionShowCaptchaKeyAttribute(), true);
			// session.setAttribute(getLoginNumKeyAttribute(), ++number);
		}

		session.setAttribute(getLoginNumKeyAttribute(), ++number);
		
		//不是ajax请求
		if (!"XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request)
				.getHeader("X-Requested-With"))) {
				setFailureAttribute(request, e);
				return true;
		}

		try {
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			String message = e.getClass().getSimpleName();
			if ("IncorrectCredentialsException".equals(message)) {
				out.println("{success:false,message:'密码错误'}");
			} else if ("UnknownAccountException".equals(message)) {
				out.println("{success:false,message:'账号不存在'}");
			} else if ("LockedAccountException".equals(message)) {
				out.println("{success:false,message:'账号被锁定'}");
			} else if ("CaptchaException".equals(message)) {
				out.println("{success:false,message:'验证码错误'}");
			} else {
				out.println("{success:false,message:'未知错误:"+ e.getMessage() +"'}");
			}
			out.flush();
			out.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;
	}  
 
    /** 
     * 重写父类方法，创建一个自定义的{@link UsernamePasswordCaptchaToken} 
     */  
    @Override  
    protected AuthenticationToken createToken(
 
    ServletRequest request, ServletResponse response) {
 
        String username = getUsername(request);
 
        String password = getPassword(request);
 
        String captcha = getCaptcha(request);
 
        boolean rememberMe = isRememberMe(request);
 
        String host = getHost(request);
 
        return new UsernamePasswordCaptchaToken(username, password.toCharArray(), rememberMe, host, captcha);
 
    }
 
}