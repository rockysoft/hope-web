package com.github.rockysoft.aop;

import java.lang.reflect.Method;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.rockysoft.entity.Log;
import com.github.rockysoft.entity.Principal;
import com.github.rockysoft.entity.User;
import com.github.rockysoft.service.AccountService;
import com.github.rockysoft.service.LogService;

/**
 * 日志记录，添加、删除、修改方法AOP
 * 
 * @author Dan
 * 
 */

@Aspect
@Component
public class LogAspect {
	
	@Autowired
	private LogService logService;// 日志记录Service
	@Autowired
	private AccountService accountService;

	/**
	 * 添加业务逻辑方法切入点
	 */
	@Pointcut("execution(* com.github.rockysoft.service.*.save*(..))")
	public void insertServiceCall() {
		System.out.println("-----------------save--------------------");
	}

	/**
	 * 修改业务逻辑方法切入点
	 */
	@Pointcut("execution(* com.github.rockysoft.service.*.update*(..))")
	public void updateServiceCall() {
		System.out.println("-----------------update--------------------");
	}

	/**
	 * 刪除业务逻辑方法切入点
	 */
	@Pointcut("execution(* com.github.rockysoft.service.*.delete*(..))")
	public void deleteServiceCall() {
		System.out.println("-----------------update--------------------");
	}

	/**
	 * 用戶添加操作日志(后置通知)
	 * 
	 * @param joinPoint
	 * @param rtv
	 * @throws Throwable
	 */
	@AfterReturning(value = "insertServiceCall()", argNames = "rtv", returning = "rtv")
	public void insertServiceCallCalls(JoinPoint joinPoint, Object rtv) throws Throwable {
//		HttpServletRequest request = ServletActionContext.getRequest();
//		HttpSession session = request.getSession();
//		User u = (User) session.getAttribute(Constants.USER_SESSION);
		Principal principal = this.accountService.getCurrentUser();
		User u = principal.getUser();
		if (null == u) {
			return;
		}

		// 判断参数
		if (joinPoint.getArgs() == null) {// 没有参数
			return;
		}
		
		
		// 获取方法名
		String methodName = joinPoint.getSignature().getName();
		// 获取操作内容
		String opContent = adminOptionContent(joinPoint.getArgs(), methodName);
		// 创建日志对象
		Log log = new Log();
		log.setUserId(u.getId());// 设置用戶id
		log.setCreateTime(new Date());// 操作时间
		log.setContent(opContent);// 操作内容
		log.setOperation("添加");// 操作
		System.out.println("++++++++++++:"+rtv);
//		int ret = (Integer) rtv;  
//        if (ret >= 1) {  
//            log.setOperationResult("成功");  
//        } else {  
//            log.setOperationResult("失败");  
//        }  
		logService.log(log);// 添加日志
	}
	
	
	/**
	 * 用戶修改操作日志(后置通知)
	 * 
	 * @param joinPoint
	 * @param rtv
	 * @throws Throwable
	 */
	@AfterReturning(value = "updateServiceCall()", argNames = "rtv", returning = "rtv")
	public void updateServiceCallCalls(JoinPoint joinPoint, Object rtv) throws Throwable {
//		HttpServletRequest request = ServletActionContext.getRequest();
//		HttpSession session = request.getSession();
//		User u = (User) session.getAttribute(Constants.USER_SESSION);
		Principal principal = this.accountService.getCurrentUser();
		User u = principal.getUser();
		if (null == u) {
			return;
		}
		// 判断参数
		if (joinPoint.getArgs() == null) {// 没有参数
			return;
		}
		// 获取方法名
		String methodName = joinPoint.getSignature().getName();
		// 获取操作内容
		String opContent = adminOptionContent(joinPoint.getArgs(), methodName);
		// 创建日志对象
		Log log = new Log();
		log.setUserId(u.getId());// 设置用戶id
		log.setCreateTime(new Date());// 操作时间
		log.setContent(opContent);// 操作内容
		log.setOperation("修改");// 操作
		System.out.println("++++++++++++:"+rtv);
//		int ret = (Integer) rtv;  
//        if (ret >= 1) {  
//            log.setOperationResult("成功");  
//        } else {  
//            log.setOperationResult("失败");  
//        }  
		logService.log(log);// 添加日志
	}
	
	
	/**
	 * 用戶刪除操作日志(后置通知)
	 * 
	 * @param joinPoint
	 * @param rtv
	 * @throws Throwable
	 */
	@AfterReturning(value = "deleteServiceCall()", argNames = "rtv", returning = "rtv")
	public void deleteServiceCallCalls(JoinPoint joinPoint, Object rtv) throws Throwable {
//		HttpServletRequest request = ServletActionContext.getRequest();
//		HttpSession session = request.getSession();
//		User u = (User) session.getAttribute(Constants.USER_SESSION);
		Principal principal = this.accountService.getCurrentUser();
		User u = principal.getUser();
		if (null == u) {
			return;
		}
		// 判断参数
		if (joinPoint.getArgs() == null) {// 没有参数
			return;
		}
		// 获取方法名
		String methodName = joinPoint.getSignature().getName();
		// 获取操作内容
		String opContent = adminOptionContent(joinPoint.getArgs(), methodName);
		// 创建日志对象
		Log log = new Log();
		log.setUserId(u.getId());// 设置用戶id
		log.setCreateTime(new Date());// 操作时间
		log.setContent(opContent);// 操作内容
		log.setOperation("刪除");// 操作
		System.out.println("++++++++++++:"+rtv);
//		int ret = (Integer) rtv;  
//        if (ret >= 1) {  
//            log.setOperationResult("成功");  
//        } else {  
//            log.setOperationResult("失败");  
//        }  
		logService.log(log);// 添加日志
	}
	

	/**
	 * 使用Java反射来获取被拦截方法(insert、update)的参数值， 将参数值拼接为操作内容
	 */
	public String adminOptionContent(Object[] args, String mName) throws Exception {
		if (args == null) {
			return null;
		}
		StringBuffer rs = new StringBuffer();
		rs.append(mName);
		String className = null;
		int index = 1;
		// 遍历参数对象
		for (Object info : args) {
			// 获取对象类型
			className = info.getClass().getName();
			className = className.substring(className.lastIndexOf(".") + 1);
			rs.append("[参数" + index + "，类型：" + className + "，值：");
			// 获取对象的所有方法
			Method[] methods = info.getClass().getDeclaredMethods();
			// 遍历方法，判断get方法
			for (Method method : methods) {
				String methodName = method.getName();
				// 判断是不是get方法
				if (methodName.indexOf("get") == -1) {// 不是get方法
					continue;// 不处理
				}
				Object rsValue = null;
				try {
					// 调用get方法，获取返回值
					rsValue = method.invoke(info);
					if (rsValue == null) {// 没有返回值
						continue;
					}
				} catch (Exception e) {
					continue;
				}
				// 将值加入内容中
				rs.append("(" + methodName + " : " + rsValue + ")");
			}
			rs.append("]");
			index++;
		}
		return rs.toString();
	}


}
