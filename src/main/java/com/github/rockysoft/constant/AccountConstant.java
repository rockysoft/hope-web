package com.github.rockysoft.constant;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * Account 模块的常量
 * 
 * @author liukai
 * 
 */
public class AccountConstant {
	
	public static final String KEY_CAPTCHA = "SE_KEY_MM_CODE";

	/**
	 * 默认密码:111111
	 */
	public static final String DEFAULT_PASSWORD = "111111";

	/**
	 * 定义一个userId为0的常量,用以区分 通过邮件 还是 页面审批的情景.
	 * 
	 * <pre>
	 * 1. userId = 0 表示页面审批,取当前用户的ID.
	 * 2. userId > 0 表示邮件审批,取userId.
	 * </pre>
	 * 
	 */
	public static final Integer FROM_PAGE_USER_ID = 0;

	/**
	 * 加密方式 : SHA-1
	 */
	public static final String HASH_ALGORITHM = "SHA-1";

	/**
	 * 迭代次数 : 1024
	 */
	public static final int HASH_INTERATIONS = 1024;

	/**
	 * salt的数组大小 : 8
	 */
	public static final int SALT_SIZE = 8;

	/**
	 * 默认的Group类型
	 * 
	 * <pre>
	 * 1.admin 超级管理员
	 * 2.apply 申请人 
	 * 3.audit 审批人
	 * 4.om_a 运维人A
	 * 5.om_B 运维人B
	 * </pre>
	 * 
	 * @author liukai
	 * 
	 */
	public enum DefaultGroups implements ICommonEnum {

		admin(1), apply(2), audit(3), om_a(4), om_b(5);

		public static final Map<Integer, String> map = Maps.newLinkedHashMap();

		static {
			for (DefaultGroups e : DefaultGroups.values()) {

				map.put(e.code, e.name());

			}
		}

		public static String get(Integer code) {
			return map.get(code);
		}

		private int code;

		private DefaultGroups(int code) {
			this.code = code;
		}

		@Override
		public Integer toInteger() {
			return this.code;
		}

		@Override
		public String toString() {
			return String.valueOf(this.code);
		}

	}

	/**
	 * 用户状态
	 * 
	 * <pre>
	 * 1:enabled 有效
	 * 0:disabled 无效
	 * </pre>
	 * 
	 * @author liukai
	 * 
	 */
	public enum UserStatus implements ICommonEnum {

		DISABLED(0), ENABLED(1);

		public static final Map<Integer, String> map = Maps.newLinkedHashMap();

		// 构造器只能私有private,绝对不允许有public构造器.

		static {
			for (UserStatus e : UserStatus.values()) {

				map.put(e.code, e.name());

			}
		}

		// 该方法根据传入的参数,返回enum中的文本. 实现该方法后
		// Map集合,将enum中的值迭代至Map集合中,key为enum的输入参数.value为enum的值.最后用Map集合的get方法获得获得value
		// 也可以直接用 .map方法返回一个HashMap的返回值

		public static String get(Integer code) {
			return map.get(code);
		}

		private int code;

		private UserStatus(int code) {
			this.code = code;
		}

		// 覆写toString 方法，在该方法中返回从构造函数中传入的参数

		@Override
		public Integer toInteger() {
			return this.code;
		}

		// 同toString方法,不过返回的是Integer类型.可查看toInteger的说明得到更详细的信息

		@Override
		public String toString() {
			return String.valueOf(this.code);
		}

	}

	/**
	 * 用户类型
	 * 
	 * <pre>
	 * 1-管理员 admin
	 * 2-申请人 apply
	 * 3-审核人 audit
	 * 4-运维人 operation
	 * </pre>
	 * 
	 * @author liukai
	 * 
	 */
	public enum UserTypes implements ICommonEnum {

		admin(1), apply(2), audit(3), operation(4);

		public static final Map<Integer, String> map = Maps.newLinkedHashMap();

		static {
			for (UserTypes e : UserTypes.values()) {

				map.put(e.code, e.name());

			}
		}

		public static String get(Integer code) {
			return map.get(code);
		}

		private int code;

		private UserTypes(int code) {
			this.code = code;
		}

		@Override
		public Integer toInteger() {
			return this.code;
		}

		@Override
		public String toString() {
			return String.valueOf(this.code);
		}

	}

}
