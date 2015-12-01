/*
MySQL Data Transfer
Source Host: localhost
Source Database: hope
Target Host: localhost
Target Database: hope
Date: 2015-12-1 20:50:18
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for log
-- ----------------------------
CREATE TABLE `log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '流水号',
  `user_id` int(11) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  `content` varchar(8000) NOT NULL DEFAULT '' COMMENT '日志内容',
  `operation` varchar(250) NOT NULL DEFAULT '' COMMENT '用户所做的操作',
  PRIMARY KEY (`id`),
  KEY `log_ibfk_1` (`user_id`),
  CONSTRAINT `log_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='审计日志表';

-- ----------------------------
-- Table structure for login_log
-- ----------------------------
CREATE TABLE `login_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '流水号',
  `user_id` int(11) DEFAULT NULL COMMENT '用户流水号',
  `login_name` varchar(100) NOT NULL COMMENT '登录名',
  `ip` varchar(50) NOT NULL COMMENT 'IP',
  `type` tinyint(1) NOT NULL COMMENT '日志类型',
  `status` tinyint(1) NOT NULL COMMENT '日志状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '发生时间',
  PRIMARY KEY (`id`),
  KEY `login_log_ibfk_1` (`user_id`),
  CONSTRAINT `login_log_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8 COMMENT='登录日志表';

-- ----------------------------
-- Table structure for menu
-- ----------------------------
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `url` varchar(255) DEFAULT NULL COMMENT '链接',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `icon_cls` varchar(255) DEFAULT NULL COMMENT '图标样式',
  `text` varchar(255) DEFAULT NULL COMMENT '文本',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `type` varchar(255) DEFAULT NULL COMMENT '类型',
  `controller` varchar(50) DEFAULT NULL COMMENT '控制器',
  `buttons` varchar(100) DEFAULT NULL COMMENT '按钮',
  `parent_id` int(11) DEFAULT NULL COMMENT '父ID',
  `is_visible` tinyint(1) DEFAULT NULL COMMENT '是否可见',
  `leaf` tinyint(1) DEFAULT NULL COMMENT '是否叶节点',
  PRIMARY KEY (`id`),
  KEY `FK5E13532B60A42591` (`parent_id`),
  CONSTRAINT `menu_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for organization
-- ----------------------------
CREATE TABLE `organization` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(64) NOT NULL COMMENT '机构名称',
  `description` varchar(64) DEFAULT NULL COMMENT '机构描述',
  `parent_id` int(11) DEFAULT NULL COMMENT '父机构',
  `sort` int(3) NOT NULL DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`),
  CONSTRAINT `organization_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `organization` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(40) NOT NULL COMMENT '权限名称',
  `description` varchar(255) DEFAULT NULL COMMENT '权限描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role
-- ----------------------------
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(64) DEFAULT NULL COMMENT '角色名称',
  `description` varchar(128) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
CREATE TABLE `role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  KEY `menu_id` (`menu_id`),
  CONSTRAINT `role_menu_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `role_menu_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
CREATE TABLE `role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `permission_id` int(11) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  KEY `permission_id` (`permission_id`),
  CONSTRAINT `role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `role_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8 COMMENT='角色-权限关系表';

-- ----------------------------
-- Table structure for user
-- ----------------------------
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `org_id` int(11) NOT NULL,
  `login_name` varchar(64) NOT NULL COMMENT '登录账号',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `salt` varchar(16) NOT NULL COMMENT 'SALT',
  `real_name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `sex` int(1) NOT NULL DEFAULT '0' COMMENT '性别',
  `email` varchar(64) DEFAULT NULL COMMENT '电子信箱',
  `mobile` varchar(32) DEFAULT NULL COMMENT '移动电话',
  `office_phone` varchar(32) DEFAULT NULL COMMENT '办公电话',
  `error_count` int(2) DEFAULT '0' COMMENT '登录验证失败次数',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `last_login_ip` varchar(32) DEFAULT NULL COMMENT '上次登录IP',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `gmt_create` datetime NOT NULL COMMENT '记录创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '记录修改时间',
  `status` int(1) DEFAULT '1' COMMENT '账号状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_login_name` (`login_name`),
  KEY `org_id` (`org_id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`org_id`) REFERENCES `organization` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Function structure for queryChildrenDeptInfo
-- ----------------------------
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `queryChildrenDeptInfo`(deptId INT) RETURNS varchar(4000) CHARSET utf8
BEGIN
DECLARE sTemp VARCHAR(4000);
DECLARE sTempChd VARCHAR(4000);

SET sTemp = '$';
SET sTempChd = cast(deptId as char);

WHILE sTempChd is not NULL DO
SET sTemp = CONCAT(sTemp,',',sTempChd);
SELECT group_concat(dept_id) INTO sTempChd FROM department where FIND_IN_SET(dept_parent_id,sTempChd)>0;
END WHILE;
return sTemp;
END;;
DELIMITER ;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `log` VALUES ('1', '1', '2015-04-12 21:41:09', 'saveUser[参数1，类型：User，值：(getEmail : 6@a.com)(getSalt : 714e1f8f5c659801)(getPlainPassword : 6)(getRoleList : [com.github.rockysoft.entity.Role@36793])(getLoginName : 6)(getOrgId : 3)(getRealName : 6)(getSex : 0)(getGmtCreate : Sun Apr 12 21:41:08 CST 2015)(getGmtModified : Sun Apr 12 21:41:08 CST 2015)(getId : 9)(getPassword : 7d8d03fe27d47304404723b2f4a98601e23e455a)(getStatus : 1)]', '添加');
INSERT INTO `log` VALUES ('2', '1', '2015-04-12 21:45:33', 'deleteUser[参数1，类型：Integer，值：]', '刪除');
INSERT INTO `log` VALUES ('3', '1', '2015-04-12 21:46:01', 'updateUser[参数1，类型：User，值：(getEmail : 4@a.com)(getPlainPassword : )(getRoleList : [com.github.rockysoft.entity.Role@144256a])(getLoginName : 4)(getOrgId : 4)(getRealName : 4)(getSex : 0)(getErrorCount : 0)(getGmtModified : Sun Apr 12 21:46:01 CST 2015)(getId : 7)(getPassword : )(getStatus : 1)]', '修改');
INSERT INTO `log` VALUES ('4', '1', '2015-04-15 10:51:13', 'saveMenu[参数1，类型：Menu，值：(getUrl : Hope.app.sys.logs)(getSort : 5)(getParentId : 2)(getButtons : export)(getController : Log)(getId : 13)(getText : 审计日志)(getChildren : [])]', '添加');
INSERT INTO `log` VALUES ('5', '1', '2015-04-15 10:51:42', 'saveRoleMenus[参数1，类型：Integer，值：][参数2，类型：String，值：(getBytes : [B@af0c94)]', '添加');
INSERT INTO `log` VALUES ('6', '1', '2015-04-15 10:51:52', 'saveRoleMenus[参数1，类型：Integer，值：][参数2，类型：String，值：(getBytes : [B@2656c3)]', '添加');
INSERT INTO `log` VALUES ('7', '1', '2015-04-16 17:41:16', 'saveMenu[参数1，类型：Menu，值：(getUrl : Hope.app.sys.loginlogs)(getSort : 6)(getParentId : 2)(getButtons : export)(getController : Log)(getId : 14)(getText : 登录日志)(getChildren : [])]', '添加');
INSERT INTO `log` VALUES ('8', '1', '2015-04-16 17:41:47', 'saveRoleMenus[参数1，类型：Integer，值：][参数2，类型：String，值：(getBytes : [B@13954c3)]', '添加');
INSERT INTO `log` VALUES ('9', '1', '2015-04-28 16:14:18', 'updateLogin[参数1，类型：User，值：(getLastLoginTime : Tue Apr 28 16:14:18 CST 2015)(getLastLoginIp : 127.0.0.1)(getId : 1)]', '修改');
INSERT INTO `log` VALUES ('10', '1', '2015-04-28 16:14:49', 'updateLogin[参数1，类型：User，值：(getLastLoginTime : Tue Apr 28 16:14:48 CST 2015)(getLastLoginIp : 127.0.0.1)(getId : 1)]', '修改');
INSERT INTO `log` VALUES ('11', '1', '2015-04-28 16:17:35', 'updateLogin[参数1，类型：User，值：(getLastLoginTime : Tue Apr 28 16:17:35 CST 2015)(getLastLoginIp : 127.0.0.1)(getId : 1)]', '修改');
INSERT INTO `log` VALUES ('12', '1', '2015-04-28 16:20:45', 'updateLogin[参数1，类型：User，值：(getLastLoginTime : Tue Apr 28 16:20:45 CST 2015)(getLastLoginIp : 127.0.0.1)(getId : 1)]', '修改');
INSERT INTO `log` VALUES ('13', '4', '2015-11-07 10:26:42', 'updateLogin[参数1，类型：User，值：(getLastLoginTime : Sat Nov 07 10:26:42 CST 2015)(getLastLoginIp : 127.0.0.1)(getId : 4)]', '修改');
INSERT INTO `log` VALUES ('14', '4', '2015-11-07 10:27:57', 'updateLogin[参数1，类型：User，值：(getLastLoginTime : Sat Nov 07 10:27:57 CST 2015)(getLastLoginIp : 127.0.0.1)(getId : 4)]', '修改');
INSERT INTO `log` VALUES ('15', '1', '2015-11-07 10:28:27', 'updateLogin[参数1，类型：User，值：(getLastLoginTime : Sat Nov 07 10:28:27 CST 2015)(getLastLoginIp : 127.0.0.1)(getId : 1)]', '修改');
INSERT INTO `log` VALUES ('16', '1', '2015-11-07 11:17:31', 'updateLogin[参数1，类型：User，值：(getLastLoginTime : Sat Nov 07 11:17:31 CST 2015)(getLastLoginIp : 127.0.0.1)(getId : 1)]', '修改');
INSERT INTO `log` VALUES ('17', '1', '2015-11-12 22:39:37', 'updateLogin[参数1，类型：User，值：(getLastLoginTime : Thu Nov 12 22:39:37 CST 2015)(getLastLoginIp : 127.0.0.1)(getId : 1)]', '修改');
INSERT INTO `log` VALUES ('18', '1', '2015-11-18 21:33:48', 'updateLogin[参数1，类型：User，值：(getLastLoginTime : Wed Nov 18 21:33:48 CST 2015)(getLastLoginIp : 127.0.0.1)(getId : 1)]', '修改');
INSERT INTO `log` VALUES ('19', '1', '2015-11-22 22:09:32', 'updateLogin[参数1，类型：User，值：(getLastLoginTime : Sun Nov 22 22:09:32 CST 2015)(getLastLoginIp : 127.0.0.1)(getId : 1)]', '修改');
INSERT INTO `log` VALUES ('20', '1', '2015-11-22 22:19:05', 'updateLogin[参数1，类型：User，值：(getLastLoginTime : Sun Nov 22 22:19:05 CST 2015)(getLastLoginIp : 127.0.0.1)(getId : 1)]', '修改');
INSERT INTO `log` VALUES ('21', '1', '2015-11-22 22:30:33', 'saveMenu[参数1，类型：Menu，值：(getUrl : /druid/index.html)(getSort : 7)(getParentId : 2)(getButtons : create,update,delete)(getId : 15)(getText : Druid监控)(getChildren : [])]', '添加');
INSERT INTO `log` VALUES ('22', '1', '2015-11-22 22:30:55', 'saveRoleMenus[参数1，类型：Integer，值：][参数2，类型：String，值：(getBytes : [B@4141e8)]', '添加');
INSERT INTO `log` VALUES ('23', '1', '2015-11-24 22:10:53', 'updateLogin[参数1，类型：User，值：(getLastLoginTime : Tue Nov 24 22:10:53 CST 2015)(getLastLoginIp : 127.0.0.1)(getId : 1)]', '修改');
INSERT INTO `login_log` VALUES ('21', null, 'admin', '127.0.0.1', '1', '2', '2015-04-24 18:19:11');
INSERT INTO `login_log` VALUES ('22', '1', 'admin', '127.0.0.1', '1', '1', '2015-04-24 18:19:19');
INSERT INTO `login_log` VALUES ('23', '1', 'admin', '127.0.0.1', '2', '3', '2015-04-24 18:19:30');
INSERT INTO `login_log` VALUES ('24', '1', 'admin', '127.0.0.1', '1', '1', '2015-04-24 18:19:34');
INSERT INTO `login_log` VALUES ('25', '1', 'admin', '127.0.0.1', '2', '3', '2015-04-24 18:20:02');
INSERT INTO `login_log` VALUES ('26', null, 'admin', '127.0.0.1', '1', '2', '2015-04-28 15:09:14');
INSERT INTO `login_log` VALUES ('27', '1', 'admin', '127.0.0.1', '1', '1', '2015-04-28 15:09:18');
INSERT INTO `login_log` VALUES ('28', '1', 'admin', '127.0.0.1', '2', '3', '2015-04-28 15:57:39');
INSERT INTO `login_log` VALUES ('29', '1', 'admin', '127.0.0.1', '1', '1', '2015-04-28 15:57:47');
INSERT INTO `login_log` VALUES ('30', '1', 'admin', '127.0.0.1', '1', '1', '2015-04-28 16:09:08');
INSERT INTO `login_log` VALUES ('31', '1', 'admin', '127.0.0.1', '2', '3', '2015-04-28 16:09:26');
INSERT INTO `login_log` VALUES ('32', '1', 'admin', '127.0.0.1', '1', '1', '2015-04-28 16:09:41');
INSERT INTO `login_log` VALUES ('33', '1', 'admin', '127.0.0.1', '1', '1', '2015-04-28 16:10:56');
INSERT INTO `login_log` VALUES ('34', '1', 'admin', '127.0.0.1', '2', '3', '2015-04-28 16:11:05');
INSERT INTO `login_log` VALUES ('35', '1', 'admin', '127.0.0.1', '1', '1', '2015-04-28 16:11:10');
INSERT INTO `login_log` VALUES ('36', '1', 'admin', '127.0.0.1', '2', '3', '2015-04-28 16:14:13');
INSERT INTO `login_log` VALUES ('37', '1', 'admin', '127.0.0.1', '1', '1', '2015-04-28 16:14:18');
INSERT INTO `login_log` VALUES ('38', '1', 'admin', '127.0.0.1', '2', '3', '2015-04-28 16:14:45');
INSERT INTO `login_log` VALUES ('39', '1', 'admin', '127.0.0.1', '1', '1', '2015-04-28 16:14:48');
INSERT INTO `login_log` VALUES ('40', '1', 'admin', '127.0.0.1', '2', '3', '2015-04-28 16:16:43');
INSERT INTO `login_log` VALUES ('41', '1', 'admin', '127.0.0.1', '1', '1', '2015-04-28 16:17:35');
INSERT INTO `login_log` VALUES ('42', '1', 'admin', '127.0.0.1', '2', '3', '2015-04-28 16:20:38');
INSERT INTO `login_log` VALUES ('43', '1', 'admin', '127.0.0.1', '1', '1', '2015-04-28 16:20:45');
INSERT INTO `login_log` VALUES ('44', '1', 'admin', '127.0.0.1', '2', '3', '2015-04-28 16:20:58');
INSERT INTO `login_log` VALUES ('45', null, 'admin', '127.0.0.1', '1', '2', '2015-11-07 10:23:28');
INSERT INTO `login_log` VALUES ('46', null, 'admin', '127.0.0.1', '1', '2', '2015-11-07 10:23:34');
INSERT INTO `login_log` VALUES ('47', null, 'admin', '127.0.0.1', '1', '2', '2015-11-07 10:23:38');
INSERT INTO `login_log` VALUES ('48', null, 'admin', '127.0.0.1', '1', '2', '2015-11-07 10:24:40');
INSERT INTO `login_log` VALUES ('49', null, 'admin', '127.0.0.1', '1', '2', '2015-11-07 10:25:09');
INSERT INTO `login_log` VALUES ('50', null, 'admin', '127.0.0.1', '1', '2', '2015-11-07 10:25:20');
INSERT INTO `login_log` VALUES ('51', null, 'admin', '127.0.0.1', '1', '2', '2015-11-07 10:25:51');
INSERT INTO `login_log` VALUES ('52', null, 'admin', '127.0.0.1', '1', '2', '2015-11-07 10:26:05');
INSERT INTO `login_log` VALUES ('53', null, 'admin', '127.0.0.1', '1', '2', '2015-11-07 10:26:20');
INSERT INTO `login_log` VALUES ('54', '4', 'a1', '127.0.0.1', '1', '1', '2015-11-07 10:26:42');
INSERT INTO `login_log` VALUES ('55', '4', 'a1', '127.0.0.1', '2', '3', '2015-11-07 10:27:02');
INSERT INTO `login_log` VALUES ('56', null, 'admin', '127.0.0.1', '1', '2', '2015-11-07 10:27:08');
INSERT INTO `login_log` VALUES ('57', null, 'admin', '127.0.0.1', '1', '2', '2015-11-07 10:27:14');
INSERT INTO `login_log` VALUES ('58', null, 'admin', '127.0.0.1', '1', '2', '2015-11-07 10:27:22');
INSERT INTO `login_log` VALUES ('59', null, 'admin', '127.0.0.1', '1', '2', '2015-11-07 10:27:39');
INSERT INTO `login_log` VALUES ('60', '4', 'a1', '127.0.0.1', '1', '1', '2015-11-07 10:27:57');
INSERT INTO `login_log` VALUES ('61', '4', 'a1', '127.0.0.1', '2', '3', '2015-11-07 10:28:04');
INSERT INTO `login_log` VALUES ('62', null, 'admin', '127.0.0.1', '1', '2', '2015-11-07 10:28:18');
INSERT INTO `login_log` VALUES ('63', '1', 'admin', '127.0.0.1', '1', '1', '2015-11-07 10:28:27');
INSERT INTO `login_log` VALUES ('64', '1', 'admin', '127.0.0.1', '1', '1', '2015-11-07 11:17:31');
INSERT INTO `login_log` VALUES ('65', '1', 'admin', '127.0.0.1', '2', '3', '2015-11-12 22:37:58');
INSERT INTO `login_log` VALUES ('66', '1', 'admin', '127.0.0.1', '1', '1', '2015-11-12 22:39:37');
INSERT INTO `login_log` VALUES ('67', '1', 'admin', '127.0.0.1', '2', '3', '2015-11-14 22:38:33');
INSERT INTO `login_log` VALUES ('68', '1', 'admin', '127.0.0.1', '1', '1', '2015-11-18 21:33:48');
INSERT INTO `login_log` VALUES ('69', '1', 'admin', '127.0.0.1', '2', '3', '2015-11-22 22:09:25');
INSERT INTO `login_log` VALUES ('70', '1', 'admin', '127.0.0.1', '1', '1', '2015-11-22 22:09:32');
INSERT INTO `login_log` VALUES ('71', '1', 'admin', '127.0.0.1', '2', '3', '2015-11-22 22:19:00');
INSERT INTO `login_log` VALUES ('72', '1', 'admin', '127.0.0.1', '1', '1', '2015-11-22 22:19:05');
INSERT INTO `login_log` VALUES ('73', '1', 'admin', '127.0.0.1', '2', '3', '2015-11-24 19:46:18');
INSERT INTO `login_log` VALUES ('74', '1', 'admin', '127.0.0.1', '1', '1', '2015-11-24 22:10:53');
INSERT INTO `login_log` VALUES ('75', '1', 'admin', '127.0.0.1', '2', '3', '2015-11-27 23:38:38');
INSERT INTO `menu` VALUES ('1', null, '图形报表根菜单', 'icon-chart', '图形报表', '0', 'MENU', null, '', null, '1', '0');
INSERT INTO `menu` VALUES ('2', null, '系统管理', 'icon-system', '系统管理', '1', 'MENU', null, '', null, '1', '0');
INSERT INTO `menu` VALUES ('3', null, '资源管理菜单', 'icon-help', '资源管理', '2', 'MENU', null, null, null, '1', '0');
INSERT INTO `menu` VALUES ('4', 'Simple.view.user.List1', '饼状图图形报表', 'icon-help', '饼状图', '0', 'URL', '', 'create,update,delete,sync', '1', '1', '1');
INSERT INTO `menu` VALUES ('5', 'Simple.view.permission.List1', '线性图图形报表', 'icon-help', '线状图', '1', 'URL', '', 'create,update,delete,sync', '1', '1', '1');
INSERT INTO `menu` VALUES ('6', 'Simple.view.DeptMain1', '曲线图图形报表', 'icon-help', '曲线图', '2', 'URL', '', 'create,update,delete,sync', '1', '1', '1');
INSERT INTO `menu` VALUES ('7', 'Hope.app.sys.roleactions', '权限管理', 'icon-grant', '权限管理', '2', 'COMPONENT', 'permission', 'create,update,delete,sync', '2', '1', '1');
INSERT INTO `menu` VALUES ('8', 'Hope.app.sys.roles', '角色管理', 'icon-group', '角色管理', '3', 'COMPONENT', 'role', 'create,update,delete,sync,export', '2', '1', '1');
INSERT INTO `menu` VALUES ('9', 'Hope.app.sys.organizations', '部门管理', 'icon-help', '部门管理', '1', 'COMPONENT', 'organization', 'create,update,delete', '2', '1', '1');
INSERT INTO `menu` VALUES ('10', 'Hope.app.sys.users', '账号管理', 'icon-user', '账号管理', '0', 'COMPONENT', 'user', 'create,update,delete,sync', '2', '1', '1');
INSERT INTO `menu` VALUES ('11', '', '数据字典', 'icon-help', '数据字典', '0', 'COMPONENT', 'sysConfig', 'create,update,delete,sync', '3', '1', '1');
INSERT INTO `menu` VALUES ('12', 'Hope.app.sys.rolemenus', '菜单管理', 'icon-help', '菜单管理', '4', 'COMPONENT', 'menu', 'create,update,delete,sync', '2', '1', '1');
INSERT INTO `menu` VALUES ('13', 'Hope.app.sys.logs', null, 'icon-user', '审计日志', '5', 'COMPONENT', 'Log', 'export', '2', '1', '1');
INSERT INTO `menu` VALUES ('14', 'Hope.app.sys.loginlogs', null, 'icon-user', '登录日志', '6', 'COMPONENT', 'LoginLog', 'export', '2', '1', '1');
INSERT INTO `menu` VALUES ('15', 'druid/index.html', null, 'icon-activity', 'Druid监控', '7', 'URL', null, 'create,update,delete', '2', '1', '1');
INSERT INTO `organization` VALUES ('1', 'xx公司', null, null, '1');
INSERT INTO `organization` VALUES ('2', '财务部', '7772222', '1', '1');
INSERT INTO `organization` VALUES ('3', '人力资源部', '柔柔弱弱', '1', '2');
INSERT INTO `organization` VALUES ('4', '市场部', '0', '1', '3');
INSERT INTO `organization` VALUES ('5', '研发部', null, '1', '4');
INSERT INTO `organization` VALUES ('6', '研发一部', '111111', '5', '1');
INSERT INTO `organization` VALUES ('7', '研发二部', '5556666999', '5', '2');
INSERT INTO `permission` VALUES ('1', 'user:update', '修改用户1');
INSERT INTO `permission` VALUES ('2', 'user:delete', '注销用户4444');
INSERT INTO `permission` VALUES ('3', 'role:view', '浏览角色1');
INSERT INTO `permission` VALUES ('4', 'role:create', '创建角色');
INSERT INTO `permission` VALUES ('5', 'role:update', '修改角色000');
INSERT INTO `permission` VALUES ('6', 'role:delete', '注销角色');
INSERT INTO `permission` VALUES ('7', 'user:view', '浏览用户');
INSERT INTO `permission` VALUES ('8', 'user:create', '创建用户');
INSERT INTO `permission` VALUES ('9', 'organization:create', '创建部门');
INSERT INTO `permission` VALUES ('10', 'organization:update', '修改部门');
INSERT INTO `permission` VALUES ('11', 'organization:delete', '删除部门');
INSERT INTO `permission` VALUES ('12', 'organization:view', '浏览部门');
INSERT INTO `permission` VALUES ('13', 'permission:create', '创建权限');
INSERT INTO `permission` VALUES ('14', 'permission:update', '修改权限');
INSERT INTO `permission` VALUES ('15', 'permission:delete', '删除权限');
INSERT INTO `permission` VALUES ('16', 'menu:create', '创建菜单');
INSERT INTO `permission` VALUES ('17', 'menu:update', '修改菜单');
INSERT INTO `permission` VALUES ('18', 'menu:delete', '删除菜单');
INSERT INTO `role` VALUES ('1', 'Administrators', '管理员');
INSERT INTO `role` VALUES ('2', 'Users', '修改代码测试123A');
INSERT INTO `role` VALUES ('3', '测试角色', '测试角色');
INSERT INTO `role_menu` VALUES ('15', '2', '1');
INSERT INTO `role_menu` VALUES ('16', '2', '4');
INSERT INTO `role_menu` VALUES ('17', '2', '5');
INSERT INTO `role_menu` VALUES ('18', '2', '6');
INSERT INTO `role_menu` VALUES ('19', '3', '2');
INSERT INTO `role_menu` VALUES ('20', '3', '10');
INSERT INTO `role_menu` VALUES ('21', '3', '9');
INSERT INTO `role_menu` VALUES ('22', '3', '7');
INSERT INTO `role_menu` VALUES ('23', '3', '8');
INSERT INTO `role_menu` VALUES ('72', '1', '1');
INSERT INTO `role_menu` VALUES ('73', '1', '4');
INSERT INTO `role_menu` VALUES ('74', '1', '5');
INSERT INTO `role_menu` VALUES ('75', '1', '6');
INSERT INTO `role_menu` VALUES ('76', '1', '2');
INSERT INTO `role_menu` VALUES ('77', '1', '10');
INSERT INTO `role_menu` VALUES ('78', '1', '9');
INSERT INTO `role_menu` VALUES ('79', '1', '7');
INSERT INTO `role_menu` VALUES ('80', '1', '8');
INSERT INTO `role_menu` VALUES ('81', '1', '12');
INSERT INTO `role_menu` VALUES ('82', '1', '13');
INSERT INTO `role_menu` VALUES ('83', '1', '14');
INSERT INTO `role_menu` VALUES ('84', '1', '15');
INSERT INTO `role_menu` VALUES ('85', '1', '3');
INSERT INTO `role_menu` VALUES ('86', '1', '11');
INSERT INTO `role_permission` VALUES ('4', '1', '4');
INSERT INTO `role_permission` VALUES ('5', '1', '5');
INSERT INTO `role_permission` VALUES ('6', '1', '6');
INSERT INTO `role_permission` VALUES ('7', '1', '7');
INSERT INTO `role_permission` VALUES ('8', '1', '8');
INSERT INTO `role_permission` VALUES ('9', '2', '8');
INSERT INTO `role_permission` VALUES ('10', '2', '7');
INSERT INTO `role_permission` VALUES ('11', '2', '1');
INSERT INTO `role_permission` VALUES ('12', '2', '2');
INSERT INTO `role_permission` VALUES ('13', '3', '3');
INSERT INTO `role_permission` VALUES ('14', '3', '4');
INSERT INTO `role_permission` VALUES ('15', '3', '5');
INSERT INTO `role_permission` VALUES ('16', '3', '6');
INSERT INTO `role_permission` VALUES ('17', '1', '1');
INSERT INTO `role_permission` VALUES ('18', '1', '4');
INSERT INTO `role_permission` VALUES ('19', '1', '5');
INSERT INTO `role_permission` VALUES ('20', '1', '6');
INSERT INTO `role_permission` VALUES ('21', '1', '7');
INSERT INTO `role_permission` VALUES ('22', '1', '8');
INSERT INTO `role_permission` VALUES ('23', '1', '2');
INSERT INTO `role_permission` VALUES ('24', '1', '3');
INSERT INTO `role_permission` VALUES ('25', '1', '4');
INSERT INTO `role_permission` VALUES ('26', '1', '5');
INSERT INTO `role_permission` VALUES ('27', '1', '6');
INSERT INTO `role_permission` VALUES ('28', '1', '7');
INSERT INTO `role_permission` VALUES ('29', '1', '8');
INSERT INTO `role_permission` VALUES ('30', '1', '1');
INSERT INTO `role_permission` VALUES ('31', '1', '2');
INSERT INTO `role_permission` VALUES ('32', '1', '3');
INSERT INTO `role_permission` VALUES ('33', '1', '9');
INSERT INTO `role_permission` VALUES ('34', '1', '10');
INSERT INTO `role_permission` VALUES ('35', '1', '11');
INSERT INTO `role_permission` VALUES ('36', '1', '12');
INSERT INTO `role_permission` VALUES ('37', '1', '4');
INSERT INTO `role_permission` VALUES ('38', '1', '5');
INSERT INTO `role_permission` VALUES ('39', '1', '6');
INSERT INTO `role_permission` VALUES ('40', '1', '7');
INSERT INTO `role_permission` VALUES ('41', '1', '8');
INSERT INTO `role_permission` VALUES ('42', '1', '1');
INSERT INTO `role_permission` VALUES ('43', '1', '2');
INSERT INTO `role_permission` VALUES ('44', '1', '3');
INSERT INTO `role_permission` VALUES ('45', '1', '9');
INSERT INTO `role_permission` VALUES ('46', '1', '10');
INSERT INTO `role_permission` VALUES ('47', '1', '11');
INSERT INTO `role_permission` VALUES ('48', '1', '12');
INSERT INTO `role_permission` VALUES ('49', '1', '13');
INSERT INTO `role_permission` VALUES ('50', '1', '14');
INSERT INTO `role_permission` VALUES ('51', '1', '15');
INSERT INTO `role_permission` VALUES ('52', '1', '16');
INSERT INTO `role_permission` VALUES ('53', '1', '17');
INSERT INTO `role_permission` VALUES ('54', '1', '18');
INSERT INTO `user` VALUES ('1', '2', 'admin', '90dc23253021c713053cb915765c3052bafa0891', '201f9dec2a970c12', '管理员', '1', 'boychen111@21cn.com', '', '123456789', '0', '2015-11-24 22:10:53', '127.0.0.1', null, '0000-00-00 00:00:00', '2015-04-03 10:41:57', '1');
INSERT INTO `user` VALUES ('4', '4', 'a1', 'd864a4dded527d467fc2bd24a26f0cb3372c4bb7', '910b1352d9605e93', '1', '2', '1@a.com', null, null, '0', '2015-11-07 10:27:57', '127.0.0.1', null, '2014-11-09 00:07:26', '2014-11-09 00:07:26', '1');
INSERT INTO `user` VALUES ('5', '2', '2', '1f000cfc12cebb82ff0e05357bfa09aeb3bda29c', 'ae153f67dd3763ea', '2', '1', '2@b.com', null, null, '0', null, null, null, '2014-11-09 11:11:49', '2014-11-09 11:11:49', '1');
INSERT INTO `user` VALUES ('6', '2', '3', '798d17c56b0586b14df898560b2bd825bdc4f906', '6dfe2ac5ec997f49', '3', '0', '3@a.com', null, null, '0', null, null, null, '2015-04-12 21:25:57', '2015-04-12 21:25:57', '1');
INSERT INTO `user` VALUES ('7', '4', '4', '9a253be7e03af184d5503191963a2283d9aaacc8', 'df9f099b3433f71b', '4', '0', '4@a.com', null, null, '0', null, null, null, '2015-04-12 21:29:56', '2015-04-12 21:29:56', '1');
INSERT INTO `user` VALUES ('9', '3', '6', '7d8d03fe27d47304404723b2f4a98601e23e455a', '714e1f8f5c659801', '6', '0', '6@a.com', null, null, '0', null, null, null, '2015-04-12 21:41:08', '2015-04-12 21:41:08', '1');
INSERT INTO `user_role` VALUES ('2', '1', '1');
INSERT INTO `user_role` VALUES ('3', '1', '2');
INSERT INTO `user_role` VALUES ('9', '3', '3');
INSERT INTO `user_role` VALUES ('10', '2', '1');
INSERT INTO `user_role` VALUES ('22', '4', '2');
INSERT INTO `user_role` VALUES ('23', '5', '1');
INSERT INTO `user_role` VALUES ('24', '6', '2');
INSERT INTO `user_role` VALUES ('26', '8', '1');
INSERT INTO `user_role` VALUES ('27', '9', '1');
INSERT INTO `user_role` VALUES ('28', '7', '1');
