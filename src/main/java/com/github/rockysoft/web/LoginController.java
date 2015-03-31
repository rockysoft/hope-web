package com.github.rockysoft.web;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.propertyeditors.LocaleEditor;
import org.springframework.web.servlet.ModelAndView; 
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;
import java.util.Locale;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.rockysoft.service.AccountService;

/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，
 * 
 * 真正登录的POST请求由Filter完成,
 * 
 */
@Controller
public class LoginController {
	
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

//	@Resource(name = "imageCaptchaService")
//    private ImageCaptchaService imageCaptchaService;
    /**
     * 生成验证码图片io流
     */
//    @RequestMapping(value = "/captchaCode")
//    public void ImageCaptcha(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        System.out.println("-------------------  /generateImage");
//        byte[] captchaChallengeAsJpeg = null;
//        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
//        try {
//            String sessionid = request.getSession().getId();
//            BufferedImage challenge = imageCaptchaService.getImageChallengeForID(sessionid, request.getLocale());
//            JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(jpegOutputStream);
//            jpegEncoder.encode(challenge);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
//        response.setHeader("Cache-Control", "no-store");
//        response.setHeader("Pragma", "no-cache");
//        response.setDateHeader("Expires", 0);
//        response.setContentType("image/jpeg");
//        ServletOutputStream responseOutputStream = response.getOutputStream();
//        responseOutputStream.write(captchaChallengeAsJpeg);
//        responseOutputStream.flush();
//        responseOutputStream.close();
//    }
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
//		User user = accountManager.findUserByLoginName("admin");
//		if (user != null) {
//			user.setPlainPassword("admin");
//			byte[] salt = Encodes.decodeHex(user.getSalt());
//			byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, 1024);
//			user.setPassword(Encodes.encodeHex(hashPassword));
//			userDao.save(user);
//		} else {
//			 
//		}
		System.out.println("进入login().....");
		return "login";
	}

	/**
	 * 成功登录进入页面,如果未成果则跳转到signUp页面重新登录.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model) {
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
//		logger.trace(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
//		String msg = parseException(request);
//		map.put("msg", msg);
//		map.put("username", username);

//		return FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME != null ? "signUp" : "login";
		return "login";
	}
	
//	@RequestMapping(method = { RequestMethod.POST, RequestMethod.HEAD }, headers = "x-requested-with=XMLHttpRequest")
//	    public @ResponseBody
//	    String failDialog(HttpServletRequest request) {
//	        String msg = parseException(request);
//	        AjaxObject ajaxObject = new AjaxObject(msg);
//	        ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
//	        ajaxObject.setCallbackType("");
//	        return ajaxObject.toString();
//	    }
//
//	
//	private String parseException(HttpServletRequest request) {
//		        String error = (String) request
//		                .getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
//		        String msg = "其他错误！";
//		        if (error != null) {
//		            if ("org.apache.shiro.authc.UnknownAccountException".equals(error))
//		                msg = "未知帐号错误！";
//		            else if ("org.apache.shiro.authc.IncorrectCredentialsException"
//		                    .equals(error))
//		                msg = "密码错误！";
//		            else if ("com.ygsoft.security.shiro.IncorrectCaptchaException"
//		                    .equals(error))
//		                msg = "验证码错误！";
//		            else if ("org.apache.shiro.authc.AuthenticationException"
//		                    .equals(error))
//		                msg = "认证失败！";
//		            else if ("org.apache.shiro.authc.DisabledAccountException"
//		                    .equals(error))
//		                msg = "账号被冻结！";
//		        }
//		        return "登录失败，" + msg;
//		    }


	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/help", method = RequestMethod.GET)
	public String help() {
		return "help";
	}
	
	@RequestMapping(value = "/locked", method = RequestMethod.GET)
	public String locked() {
		return "locked";
	}

	@RequestMapping(value = "/changelanguage", method = RequestMethod.POST)
	public @ResponseBody String locked(@RequestParam String new_lang, HttpServletRequest request, HttpServletResponse response) {
		String msg = "";
     
     try
     {
         LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);  
         if (localeResolver == null) {  
             throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");  
         } 
         
         LocaleEditor localeEditor = new LocaleEditor();  
         localeEditor.setAsText(new_lang);
         localeResolver.setLocale(request, response, (Locale)localeEditor.getValue());  
                                 
         msg = "Change Language Success!";
     }
     catch(Exception ex)
     {
         msg = "error";
     }
     return msg;
		//return "locked";
	}
}
