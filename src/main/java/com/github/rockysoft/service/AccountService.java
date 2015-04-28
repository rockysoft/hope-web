package com.github.rockysoft.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.domain.Sort.Direction;
//import org.springframework.data.jpa.domain.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.rockysoft.constant.AccountConstant;
import com.github.rockysoft.entity.Criteria;
import com.github.rockysoft.entity.Department;
import com.github.rockysoft.entity.Menu;
import com.github.rockysoft.entity.MenuTree;
import com.github.rockysoft.entity.Organization;
import com.github.rockysoft.entity.Permission;
import com.github.rockysoft.entity.Principal;
import com.github.rockysoft.entity.Resource;
import com.github.rockysoft.entity.Role;
import com.github.rockysoft.entity.RoleMenu;
import com.github.rockysoft.entity.RolePermission;
import com.github.rockysoft.entity.User;
import com.github.rockysoft.entity.UserRole;
import com.github.rockysoft.framework.utils.Digests;
import com.github.rockysoft.framework.utils.Encodes;
import com.github.rockysoft.mapper.DepartmentMapper;
import com.github.rockysoft.mapper.MenuMapper;
import com.github.rockysoft.mapper.OrganizationMapper;
import com.github.rockysoft.mapper.PermissionMapper;
import com.github.rockysoft.mapper.ResourceMapper;
import com.github.rockysoft.mapper.RoleMapper;
import com.github.rockysoft.mapper.UserMapper;
import com.github.rockysoft.mapper.UserRoleMapper;
//import com.github.rockysoft.service.ShiroDbRealm.ShiroUser;
import com.google.common.collect.Lists;


/**
 * 安全相关实体的管理类,包括用户和权限组.
 * 
 * @author calvin
 */
// Spring Bean的标识.
@Service
// 默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class AccountService {

	private static Logger logger = LoggerFactory.getLogger(AccountService.class);

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private PermissionMapper permissionMapper;
	@Autowired
	private MenuMapper menuMapper;
	@Autowired
	private ResourceMapper resourceMapper;
	@Autowired
	private DepartmentMapper departmentMapper;
	@Autowired
	private OrganizationMapper organizationMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;
	@Autowired
	private ShiroDbRealm shiroDbRealm;
	
	private static final int SALT_SIZE = 8;
	private static final int HASH_INTERATIONS = 1024;	
	
//	private List<Menu> _allMenus;

	// -- User Manager --//
	/**
	 * 根据用户ID获得用户对象
	 * 
	 * @param id
	 * @return
	 */
	public User getUser(int id) {
		return userMapper.selectByPrimaryKey(id);
	}

	/**
	 * 获得当前登录User
	 * 
	 * @return
	 */
	/*
	public User getCurrentUser() {
		ShiroUser sUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return userMapper.selectByPrimaryKey(sUser != null ? sUser.getId() : null);
	}
	*/
	public Principal getCurrentUser(){
		  Subject subject=SecurityUtils.getSubject();
		  if (subject != null && subject.getPrincipal() != null && subject.getPrincipal() instanceof Principal) {
		    return (Principal)subject.getPrincipal();
		  }
		  return null;
	}

	/**
	 * 保存用户
	 * 
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void saveUser(User user) {
		user.setGmtCreate(new Date());
		user.setGmtModified(new Date());
		user.setStatus(1);
		if (StringUtils.isNotBlank(user.getPlainPassword())) {
			entryptPassword(user);
		}
		userMapper.insertSelective(user);
		
//		userRoleMapper.insert(record);
		saveUserRoles(user.getId(), user.getRoleList());
		
		shiroDbRealm.clearCachedAuthorizationInfo(user.getEmail());
	}
	
	@Transactional(readOnly = false) 
//	public void saveUserRoles(int userId, Integer[] roleIds) {
	public void saveUserRoles(int userId, List<Role> roles) {
		userMapper.deleteUserRolesByUserId(userId);
		UserRole userRole = new UserRole();
		userRole.setUserId(userId);
		for (Role role: roles) {
			userRole.setRoleId(role.getId());
			userMapper.insertUserRole(userRole);
		}
	}
	
	/**
	 * 更新User
	 * 
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void updateUser(User user) {

		if (StringUtils.isNotBlank(user.getPlainPassword())) {
			//entryptPassword(user);
			byte[] salt = Digests.generateSalt(SALT_SIZE);
			user.setSalt(Encodes.encodeHex(salt));

			byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
			user.setPassword(Encodes.encodeHex(hashPassword));
		}
		user.setGmtModified(new Date());
		userMapper.updateByPrimaryKeySelective(user);
		
		saveUserRoles(user.getId(), user.getRoleList());

//		shiroDbRealm.clearCachedAuthorizationInfo(user.getLoginName());
	}
	
	
	/**
	 * 更新User
	 * 
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void changeProfile(User user) {
		user.setGmtModified(new Date());
		userMapper.updateByPrimaryKeySelective(user);
	}
	
	/**
	 * 更新User
	 * 
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void updateLogin(User user) {
		userMapper.updateByPrimaryKeySelective(user);
	}

	
	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}

	/**
	 * 获得部门领导列表 (根据Type字段区分用户类型.用户类型：1-管理员；2-申请人；3-审核人.)
	 * 
	 * @param type
	 */
//	public List<User> getLeaderListByType(Integer type) {
//		return userMapper.findByType(type);
//	}

	/**
	 * 删除用户,如果尝试删除超级管理员将抛出异常.
	 */
	@Transactional(readOnly = false)
	public boolean deleteUser(Integer id) {
		if (this.isSupervisor(id)) {
			logger.warn("操作员{}尝试删除超级管理员用户", SecurityUtils.getSubject().getPrincipal());
			return false;
		} else {
			userMapper.deleteByPrimaryKey(id);
			return true;
		}
	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Integer id) {
//		return id.equals("1");
		return id==1;
	}

	/**
	 * 判断是否是默认的Role
	 * 
	 * @param id
	 *            roleId
	 * @return
	 */
	private boolean isDefeatRole(Integer id) {
//		return id.equals("1") || id.equals("2") || id.equals("3");
		return id==1 || id ==2 || id ==3;
	}

//	public Page<User> getAllUser(int page, int size, String name) {
//		Pageable pageable = new PageRequest(page, size, new Sort(Direction.ASC, "id"));
//		if ("".equals(name)) {
//			return userMapper.findAll(pageable);
//		} else {
//			return userMapper.findAllByNameLike("%" + name + "%", pageable);
//		}
//	}
	
	public int countByExample(Criteria example) {
		int count = this.userMapper.countByExample(example);
		logger.debug("count: {}", count);
		return count;
	}
	
	public List<User> selectByExample(Criteria example) {
		return this.userMapper.selectByExample(example);
	}

//	public User findUserByEmail(String email) {
//		return userMapper.findByEmail(email);
//	}
	
	/**
	 * 根据登录名获得所属的User
	 * 
	 * @param loginName
	 * @return
	 */
	public User findUserByLoginName(String loginName) {
		System.out.println("begin findUserByLoginName...");
		//User user = userMapper.selectByPrimaryKey("ce2d2db8a9c61030bf81466734cd68a3");
		//System.out.println("user====="+user.getUserId());
//		User user = userMapper.findUserByLoginName(loginName);
//		System.out.println("user.loginName====="+user.getLoginName());
//		System.out.println("user.password====="+user.getPassword());
//		System.out.println("user.salt====="+user.getSalt());
//		return user;
		return userMapper.findUserByLoginName(loginName);
	}

//	public Role findRoleByName(String name) {
//		return roleMapper.findByName(name);
//
//	}

	// -- Group Manager --//
	public Role getRole(Integer id) {
		return roleMapper.selectByPrimaryKey(id);
	}
	
	public Permission getPermission(Integer id) {
		return permissionMapper.selectByPrimaryKey(id);
	}
	
	public User getUser(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

//	public List<Role> getAllRole() {
//		return (List<Role>) roleMapper.findAll((new Sort(Direction.ASC, "id")));
//	}

//	public Page<Role> getAllRole(int page, int size) {
//		Pageable pageable = new PageRequest(page, size, new Sort(Direction.ASC, "id"));
//		return roleMapper.findAll(pageable);
//	}

	public int countRoleByExample(Criteria example) {
		int count = this.roleMapper.countByExample(example);
		logger.debug("count: {}", count);
		return count;
	}

//	public BaseRoles selectByPrimaryKey(String roleId) {
//		return this.baseRolesMapper.selectByPrimaryKey(roleId);
//	}

	public List<Role> selectRoleByExample(Criteria example) {
		return this.roleMapper.selectByExample(example);
	}
	
	@Transactional(readOnly = false)
	public void saveRole(Role entity) {
		roleMapper.insert(entity);
		shiroDbRealm.clearAllCachedAuthorizationInfo();
	}
	
	/**
	 * 更新Role
	 * 
	 * @param role
	 */
	@Transactional(readOnly = false)
	public void updateRole(Role role) {
		roleMapper.updateByPrimaryKey(role);
	}

	@Transactional(readOnly = false)
	public boolean deleteRole(Integer id) {
		if (this.isDefeatRole(id)) {
			logger.warn("操作员{}尝试删除默认权限组", SecurityUtils.getSubject().getPrincipal());
			return false;
		} else {
			roleMapper.deleteByPrimaryKey(id);
			shiroDbRealm.clearAllCachedAuthorizationInfo();
			return true;
		}
	}
	
	@Transactional(readOnly = false) 
	public void saveRolePermissions(int roleId, String permissionIds) {
		
		roleMapper.deleteRolePermissionByRoleId(roleId);
		if (permissionIds != null && !permissionIds.isEmpty()) {
		String[] permissionIdArr = permissionIds.split(",");
		if (permissionIdArr.length>0) {
		RolePermission rolePermission = new RolePermission();
		for (String permissionIdStr : permissionIdArr) {
			rolePermission.setRoleId(roleId);
//			int permissionId = Integer.parseInt(permissionIdStr);
			rolePermission.setPermissionId(Integer.parseInt(permissionIdStr));
			roleMapper.insertRolePermission(rolePermission);
		}
		}
		}
	}

	public List<Permission> getPermissionListbyRoleId(Integer roleId) {
		return this.permissionMapper.getPermissionListByRoleId(roleId);
	}
	
	public int countPermissionByExample(Criteria example) {
		int count = this.permissionMapper.countByExample(example);
		logger.debug("count: {}", count);
		return count;
	}

	public List<Permission> selectPermissionByExample(Criteria example) {
		return this.permissionMapper.selectByExample(example);
	}
	
	@Transactional(readOnly = false)
	public void savePermission(Permission entity) {
//		permissionMapper.insert(entity);
		permissionMapper.insertSelective(entity);
		shiroDbRealm.clearAllCachedAuthorizationInfo();
	}
	
	/**
	 * 更新Permission
	 * 
	 * @param permission
	 */
	@Transactional(readOnly = false)
	public void updatePermission(Permission permission) {
		permissionMapper.updateByPrimaryKey(permission);
	}

	@Transactional(readOnly = false)
	public boolean deletePermission(Integer permissionId) {
		return permissionMapper.deleteByPrimaryKey(permissionId)==1;
//			shiroDbRealm.clearAllCachedAuthorizationInfo();
//			return true;
	}
	
	
	public List<String> getPermissionsByUserId(Integer userId) {
		List<String> permissionNameList = Lists.newArrayList();
		List<Role> roleList = roleMapper.getRoleListByUserId(userId);
		//if (roleList.isEmpty()) {
			for (Role role : roleList) {
				List<Permission> permissionList = permissionMapper.getPermissionListByRoleId(role.getId());
//				List<String> permissionNameList = Lists.newArrayList();
				if (!permissionList.isEmpty()) {
					for (Permission permission : permissionList) {
						permissionNameList.add(permission.getName());
					}
				} else {
					return null;
				}
				// 基于Permission的权限信息
//				info.addStringPermissions(role.getPermissionList());
//				info.addStringPermissions(permissionNameList);
			}
			//上面这个循环考虑直接一个关联查询一次查出用户所属角色的所有权限。
			return permissionNameList;
		//} else {
		//	return null;
		//}
	}

	public Menu getMenu(int id) {
		return menuMapper.selectByPrimaryKey(id);
	}
	
	public int countMenuByExample(Criteria example) {
		int count = this.menuMapper.countByExample(example);
		logger.debug("count: {}", count);
		return count;
	}

	public List<Menu> selectMenuByExample(Criteria example) {
		return this.menuMapper.selectByExample(example);
	}
	
	@Transactional(readOnly = false)
	public void saveMenu(Menu entity) {
//		resourceMapper.insert(entity);
		menuMapper.insertSelective(entity);
		shiroDbRealm.clearAllCachedAuthorizationInfo();
	}
	
	/**
	 * 更新Resource
	 * 
	 * @param resource
	 */
	@Transactional(readOnly = false)
	public void updateMenu(Menu entity) {
		menuMapper.updateByPrimaryKey(entity);
	}

	@Transactional(readOnly = false)
	public boolean deleteMenu(Integer resourceId) {
		return menuMapper.deleteByPrimaryKey(resourceId)==1;
//			shiroDbRealm.clearAllCachedAuthorizationInfo();
//			return true;
	}
	
	
	@Transactional(readOnly = false) 
	public void saveRoleMenus(int roleId, String menuIds) {
		
		roleMapper.deleteRoleMenuByRoleId(roleId);
		if (menuIds != null && !menuIds.isEmpty()) {
		String[] menuIdArr = menuIds.split(",");
		if (menuIdArr.length>0) {
		RoleMenu roleMenu = new RoleMenu();
		for (String menuIdStr : menuIdArr) {
			roleMenu.setRoleId(roleId);
			roleMenu.setMenuId(Integer.parseInt(menuIdStr));
			roleMapper.insertRoleMenu(roleMenu);
		}
		}
		}
	}
	
	public List<Menu> GetAuthorizedMenuTree(Integer userId) {
//		String roleId  = "3D84F5FEB9D44E28B5D91710C637283A";//管理员角色
//		int roleId = 1;
//		List<Menu> _allMenus = Lists.newArrayList();
		List<Role> roleList = roleMapper.getRoleListByUserId(userId);
		if (!roleList.isEmpty()) {
			List<Integer> roleIds = Lists.newArrayList();
			for (Role role : roleList) {
				roleIds.add(role.getId());
			}
			List<Menu> _allMenus = this.menuMapper.getMenuListByRoleIds(roleIds);
			return this.GetAllMenuList(_allMenus);
		} else {
			return null;
		}
	}
	
	public List<Menu> getMenuListbyRoleId(Integer roleId) {
		return this.menuMapper.getMenuListByRoleId(roleId);
	}
	
	public List<Menu> getMenuListbyRoleIds(List<Integer> roleIds) {
		return this.menuMapper.getMenuListByRoleIds(roleIds);
	}
	
	public List<Menu> GetAllMenu(Criteria example) {
		return this.menuMapper.selectByExample(example);
	}
	
	public List<Menu> GetMenuList(Criteria example) {
		List<Menu> _allMenus = this.menuMapper.selectByExample(example);
		return this.GetAllMenuList(_allMenus);
	}
	public List<Menu> GetAllMenuList(List<Menu> _allMenus) { //(Criteria example) {
//		return this.menuMapper.selectByExample(example);
		List<Menu> menuList = new ArrayList<Menu>();
//        _allMenus = this.menuMapper.selectByExample(example); //RoleMenuModel.GetInstance.GetAll();
        List<Menu> list = new ArrayList<Menu>();
        
        for (Menu m: _allMenus)
	    {
//			if (m==null) 
//				System.out.println("m is null!!!!!!!!!!!!!");
			if (m.getParentId()==null) {				
				list.add(m);
			} 
	    }
//        System.out.println("排序前：");
//        for (Menu l : list) {
//        	System.out.println("menu.sort="+l.getSort());
//        }
		Collections.sort(list, new Comparator<Menu>(){
			   //重写排序规则
			   public int compare(Menu o1, Menu o2) {
				   if (o1.getSort()>o2.getSort()) {
					   return 1;
				   }else if(o1.getSort()<o2.getSort()){
				        return -1;
				    }else{
				        return 0;
				    }
			   }
		});
//		System.out.println("排序后：");
//        for (Menu l : list) {
//        	System.out.println("menu.sort="+l.getSort());
//        }
        
        //one_level_menus =_allMenus;
        for (Menu menu : list)
        {   //System.out.println("-》menu.sort="+menu.getSort());
            menu.setChildren(this.GetChildMenu(menu.getId(), _allMenus));
            if (menu.getChildren().size() == 0)
                menu.setLeaf(1);// .setLeaf = true;
            else
                //menu.expanded = true;
            	menu.setExpanded(1);
            menuList.add(menu);
        }

        return menuList;//this.ExtTreeGridData(list);
	}
	
	public List<Menu> GetChildMenu(int parentId, List<Menu> _allMenus) {
		List<Menu> children = new ArrayList<Menu>();
		List<Menu> list = new ArrayList<Menu>();
		for (Menu m: _allMenus)
	    {
//			if (m==null) 
//				System.out.println("m is null!!!!!!!!!!!!!");
			if (m.getParentId()==null) {	
				continue;
			}
			if (m.getParentId().equals(parentId)) {				
				list.add(m);
			} 
	    }
		Collections.sort(list, new Comparator<Menu>(){
			   //重写排序规则
			   public int compare(Menu o1, Menu o2) {
				   if (o1.getSort()>o2.getSort()) {
					   return 1;
				   }else if(o1.getSort()<o2.getSort()){
				        return -1;
				    }else{
				        return 0;
				    }
			   }
		});
		
         for (Menu menu : list)
         {
             menu.setChildren(this.GetChildMenu(menu.getId(), _allMenus));
             if (menu.getChildren().size() == 0)
                 menu.setLeaf(1);// = true;
             else
//                 menu.expanded = true;
             	menu.setExpanded(1);
             children.add(menu);
         }

         return children;
	}
	
	public List<MenuTree> GetMenuTree(Criteria example, int roleId) {
		List<Menu> authorizedMenus = this.getMenuListbyRoleId(roleId);
		return this.GetMenuTree(this.GetAllMenu(example), authorizedMenus);
	}
	
	public List<MenuTree> GetMenuTree(List<Menu> menus, List<Menu> authorizedMenus)
	{
		List<MenuTree> result = new ArrayList<MenuTree>();
		MenuTree rootNode = new MenuTree();
		rootNode.setId(0);
		rootNode.setChildren(new ArrayList<MenuTree>());
		
		this.Recursion(rootNode, result, menus, authorizedMenus);
		return result;
	}
	
	//递归
	private void Recursion(MenuTree parentNode, List<MenuTree> result, List<Menu> list, List<Menu> authorizedMenus)
	{
	    //var childs = list.Where(p => p.ParentId == parentNode.id).OrderBy(p => p.SortOrder);
		List<Menu> childs = new ArrayList<Menu>();
		for (Menu m: list)
	    {
			if (m==null) 
				System.out.println("m is null!!!!!!!!!!!!!");
			if (parentNode==null) 
				System.out.println("parentNode is null!!!!!!!!!!!!!");
			if (m.getParentId()==null) {
				if (parentNode.getId()==0)
					childs.add(m);
//			} else if (m.getParentId().equals(parentNode.getId())) {
			} else if (m.getParentId()==parentNode.getId()) {
				childs.add(m);
			}
	    }
		Collections.sort(childs, new Comparator<Menu>(){
			   //重写排序规则
			   public int compare(Menu o1, Menu o2) {
				   if (o1.getSort()>o2.getSort()) {
					   return 1;
				   }else if(o1.getSort()<o2.getSort()){
				        return -1;
				    }else{
				        return 0;
				    }
			   }
		});
	    for (Menu item: childs)
	    {
	        //var childCount = list.Count(p => p.ParentId == item.Id);
	    	//查询item节点的子节点个数
	    	int childCount = 0;
	    	for (Menu menu: list) {
	    		if (menu.getParentId()==null) 
	    			continue;
	    		if (menu.getParentId().equals(item.getId()))
	    			childCount++;
	    	}
	    	/*
	        var child = new MenuTree
	        {
	            id = item.Id,
	            SortOrder = item.SortOrder,
	            text = item.MenuName,
	            ControllerName=item.ControllerName,
	            Buttons=item.MenuButtons,
	            HasConfigs=item.HasConfigs,
	            expanded = true,
	            leaf = childCount == 0,
	            url = item.MenuLink,
	            MenuConfig=item.MenuConfig,
	            children = new List<MenuTree>()
	        };
	        */
	        MenuTree child = new MenuTree();
	        child.setId(item.getId());
	        child.setSort(item.getSort());
	        child.setText(item.getText());
	        child.setButtons(item.getButtons());
	        child.setUrl(item.getUrl());
	        child.setChildren(new ArrayList<MenuTree>());
	        child.setLeaf(childCount == 0);
	        child.setExpanded(true);
/*
	        if (role != null)
	        {
	            child.@checked =role.Menus.Any(p => p.Id == item.Id) ? true: false;
	        	child.setChecked(checked); 
	        }
	        */
	      //如果角色能看此菜单，则为true;
	        boolean isChecked = false;
	        if (!authorizedMenus.isEmpty()) {
	        	
	        		System.out.println("contains is null!!!!!!!!!!!!!!!!!!"+authorizedMenus.contains(item));
	        	//child.setChecked(authorizedMenus.contains(item));
	        		isChecked = authorizedMenus.contains(item);
	        }
	        child.setChecked(isChecked);
	        if (item.getParentId()==null) //根节点
	            result.add(child);
	        else
	            parentNode.getChildren().add(child);

	        Recursion(child, result, list, authorizedMenus);
	    }
	}
	
	public int countResourceByExample(Criteria example) {
		int count = this.resourceMapper.countByExample(example);
		logger.debug("count: {}", count);
		return count;
	}

	public List<Resource> selectResourceByExample(Criteria example) {
		return this.resourceMapper.selectByExample(example);
	}
	
	@Transactional(readOnly = false)
	public void saveResource(Resource entity) {
//		resourceMapper.insert(entity);
		resourceMapper.insertSelective(entity);
		shiroDbRealm.clearAllCachedAuthorizationInfo();
	}
	
	/**
	 * 更新Resource
	 * 
	 * @param resource
	 */
	@Transactional(readOnly = false)
	public void updateResource(Resource resource) {
		resourceMapper.updateByPrimaryKey(resource);
	}

	@Transactional(readOnly = false)
	public boolean deleteResource(Integer resourceId) {
		return resourceMapper.deleteByPrimaryKey(resourceId)==1;
//			shiroDbRealm.clearAllCachedAuthorizationInfo();
//			return true;
	}
	
	
	@Transactional(readOnly = false) 
	public void saveRoleResources(int roleId, String resourceIds) {
		
		roleMapper.deleteRolePermissionByRoleId(roleId);
		if (resourceIds != null && !resourceIds.isEmpty()) {
		String[] permissionIdArr = resourceIds.split(",");
		if (permissionIdArr.length>0) {
		RolePermission rolePermission = new RolePermission();
		for (String permissionIdStr : permissionIdArr) {
			rolePermission.setRoleId(roleId);
			rolePermission.setPermissionId(Integer.parseInt(permissionIdStr));
			roleMapper.insertRolePermission(rolePermission);
		}
		}
		}
	}
	
	
	
	
	public List<Resource> getResourceIdsListbyRoleId(String roleId) {
		return this.resourceMapper.getResourceListByRoleId(roleId);
	}
	

	
	
	public int countDepartmentByExample(Criteria example) {
		int count = this.departmentMapper.countByExample(example);
		logger.debug("count: {}", count);
		return count;
	}

	public List<Department> selectDepartmentByExample(Criteria example) {
		return this.departmentMapper.selectByExample(example);
	}
	
	@Transactional(readOnly = false)
	public void saveDepartment(Department entity) {
//		departmentMapper.insert(entity);
		departmentMapper.insertSelective(entity);
		shiroDbRealm.clearAllCachedAuthorizationInfo();
	}
	
	/**
	 * 更新Role
	 * 
	 * @param role
	 */
	@Transactional(readOnly = false)
	public void updateDepartment(Department Department) {
		departmentMapper.updateByPrimaryKey(Department);
	}

	@Transactional(readOnly = false)
	public boolean deleteDepartment(String DepartmentId) {
		return departmentMapper.deleteByPrimaryKey(DepartmentId)==1;
//			shiroDbRealm.clearAllCachedAuthorizationInfo();
//			return true;
	}
	
	public Organization getOrganization(Integer id) {
		return organizationMapper.selectByPrimaryKey(id);
	}
	
	public List<Organization> selectOrganizationByExample(Criteria example) {
		return this.organizationMapper.selectByExample(example);
	}
	
	@Transactional(readOnly = false)
	public void saveOrganization(Organization entity) {
//		departmentMapper.insert(entity);
		organizationMapper.insertSelective(entity);
		shiroDbRealm.clearAllCachedAuthorizationInfo();
	}
	
	/**
	 * 更新Role
	 * 
	 * @param role
	 */
	@Transactional(readOnly = false)
	public void updateOrganization(Organization entity) {
		organizationMapper.updateByPrimaryKey(entity);
	}

	@Transactional(readOnly = false)
	public boolean deleteOrganization(Integer id) {
		return organizationMapper.deleteByPrimaryKey(id)==1;
//			shiroDbRealm.clearAllCachedAuthorizationInfo();
//			return true;
	}
	
	public List<Organization> GetOrganizationList(Criteria example) {
		List<Organization> _allOrganizations = this.organizationMapper.selectByExample(example);
		return this.GetAllOrganizationList(_allOrganizations);
	}
	public List<Organization> GetAllOrganizationList(List<Organization> _allOrganizations) { //(Criteria example) {
//		return this.menuMapper.selectByExample(example);
		List<Organization> organizationList = new ArrayList<Organization>();
//        _allMenus = this.menuMapper.selectByExample(example); //RoleMenuModel.GetInstance.GetAll();
        List<Organization> list = new ArrayList<Organization>();
        
        for (Organization m: _allOrganizations)
	    {
//			if (m==null) 
//				System.out.println("m is null!!!!!!!!!!!!!");
			if (m.getParentId()==null) {				
				list.add(m);
			} 
	    }
//        System.out.println("排序前：");
//        for (Menu l : list) {
//        	System.out.println("menu.sort="+l.getSort());
//        }
		Collections.sort(list, new Comparator<Organization>(){
			   //重写排序规则
			   public int compare(Organization o1, Organization o2) {
				   if (o1.getSort()>o2.getSort()) {
					   return 1;
				   }else if(o1.getSort()<o2.getSort()){
				        return -1;
				    }else{
				        return 0;
				    }
			   }
		});
//		System.out.println("排序后：");
//        for (Menu l : list) {
//        	System.out.println("menu.sort="+l.getSort());
//        }
        
        //one_level_menus =_allMenus;
        for (Organization o : list)
        {   //System.out.println("-》menu.sort="+menu.getSort());
            o.setChildren(this.GetChildOrganization(o.getId(), _allOrganizations));
            if (o.getChildren().size() == 0)
                o.setLeaf(1);// .setLeaf = true;
            else
                //menu.expanded = true;
            	o.setExpanded(1);
            organizationList.add(o);
        }

        return organizationList;//this.ExtTreeGridData(list);
	}
	
	public List<Organization> GetChildOrganization(int parentId, List<Organization> _allOrganizations) {
		List<Organization> children = new ArrayList<Organization>();
		List<Organization> list = new ArrayList<Organization>();
		for (Organization m: _allOrganizations)
	    {
//			if (m==null) 
//				System.out.println("m is null!!!!!!!!!!!!!");
			if (m.getParentId()==null) {	
				continue;
			}
			if (m.getParentId().equals(parentId)) {				
				list.add(m);
			} 
	    }
		Collections.sort(list, new Comparator<Organization>(){
			   //重写排序规则
			   public int compare(Organization o1, Organization o2) {
				   if (o1.getSort()>o2.getSort()) {
					   return 1;
				   }else if(o1.getSort()<o2.getSort()){
				        return -1;
				    }else{
				        return 0;
				    }
			   }
		});
		
         for (Organization o : list)
         {
             o.setChildren(this.GetChildOrganization(o.getId(), _allOrganizations));
             if (o.getChildren().size() == 0)
                 o.setLeaf(1);// = true;
             else
//                 menu.expanded = true;
             	o.setExpanded(1);
             children.add(o);
         }

         return children;
	}
	
	@Transactional(readOnly = false)
	public int changePwd(Integer userId, String newPwd) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		System.out.println("-------------->"+Encodes.encodeHex(salt));
		System.out.println("-------------->"+Encodes.encodeHex(newPwd.getBytes()));
		byte[] hashPassword = Digests.sha1(newPwd.getBytes(), salt, HASH_INTERATIONS);
//		User user = new User();
//		user.setId();
//		user.setPassword(password);
//		user.setSalt(salt);
		Map<String, Object> parametersMap =  new HashMap<String, Object>();
		parametersMap.put("id", userId);
		parametersMap.put("password", Encodes.encodeHex(hashPassword));
		parametersMap.put("salt", Encodes.encodeHex(salt));
		parametersMap.put("gmtModified", new Date());
		return this.userMapper.updatePwd(parametersMap);
	}
	
	
//	@Resource
//	public void setUserMapper(UserMapper userMapper) {
//		this.userMapper = userMapper;
//	}
//
//	@Resource
//	public void setRoleMapper(RoleMapper roleMapper) {
//		this.roleMapper = roleMapper;
//	}

//	@Resource
//	public void setShiroDbRealm(ShiroDbRealm shiroDbRealm) {
//		this.shiroDbRealm = shiroDbRealm;
//	}
	
	
	public static void main(String[] args) {
		//System.out.println(System.getProperty("user.dir"));
//		byte[] salt = Digests.generateSalt(8);15b136cda2ac0ce4
		//user.setSalt(Encodes.encodeHex(salt));0f59a8e5fbf158c8
		byte[] salt = Encodes.decodeHex("06d433a4217cd3df");
		System.out.println(Encodes.encodeHex(salt));
		System.out.println(Encodes.encodeHex("1".getBytes()));
		byte[] hashPassword = Digests.sha1("1".getBytes(), salt, HASH_INTERATIONS);
		System.out.println(Encodes.encodeHex(hashPassword));
//		user.setPassword(Encodes.encodeHex(hashPassword));
		List<Integer> x = new ArrayList<Integer>();
		x.add(10);
		int y=20;
		AccountService.process(x, y);
		for (Integer item: x)
			System.out.println("x="+item);
	}
	
	public static void process(List<Integer> x, int y) {
		x.add(y);
	}
}
