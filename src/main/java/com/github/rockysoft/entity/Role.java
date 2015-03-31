package com.github.rockysoft.entity;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * 角色表
 */
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 角色ID
	 */
	private Integer id;

	/**
	 * 角色名称
	 */
	private String name;

	/**
	 * 角色描述
	 */
	private String description;
	
	
//	private List<Authority> authorityList = Lists.newArrayList();  
	private List<String> permissionList = Lists.newArrayList();

	/**
	 * @return 角色ID
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param roleId
	 *            角色ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return 角色名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param roleName
	 *            角色名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return 角色描述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param roleDesc
	 *            角色描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
//	 public List<User> getUser() {  
//	        return user;  
//	    }  
//	  
//	 public void setUser(List<User> user) {  
//	        this.user = user;  
//	 }  
	 
//	 @ElementCollection
//		@CollectionTable(name = "role_permission", joinColumns = { @JoinColumn(name = "role_id") })
//		@Column(name = "permission")
//		@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
		public List<String> getPermissionList() {
			return permissionList;
		}

		public void setPermissionList(List<String> permissionList) {
			this.permissionList = permissionList;
		}

//		@Transient
//		public String getPermissionNames() {
//			List<String> permissionNameList = Lists.newArrayList();
//			for (String permission : permissionList) {
//				permissionNameList.add(Permission.parse(permission).displayName);
//			}
//			return StringUtils.join(permissionNameList, ",");
//		}
//
//		@Override
//		public String toString() {
//			return ToStringBuilder.reflectionToString(this);
//		}
	  
}