/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 50158
Source Host           : localhost:3306
Source Database       : hope

Target Server Type    : MYSQL
Target Server Version : 50158
File Encoding         : 65001

Date: 2015-03-30 15:21:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', null, '图形报表根菜单', 'Chartbar', '图形报表', '0', 'MENU', null, '', null, '1', '0');
INSERT INTO `menu` VALUES ('2', null, '系统管理', 'Cog', '系统管理', '1', 'MENU', null, '', null, '1', '0');
INSERT INTO `menu` VALUES ('3', null, '资源管理菜单', 'Pagepaste', '资源管理', '2', 'MENU', null, null, null, '1', '0');
INSERT INTO `menu` VALUES ('4', 'Simple.view.user.List1', '饼状图图形报表', 'Chartpie', '饼状图', '0', 'URL', '', 'Add,Edit,Delete,Sync', '1', '1', '1');
INSERT INTO `menu` VALUES ('5', 'Simple.view.permission.List1', '线性图图形报表', 'Chartline', '线状图', '1', 'URL', '', 'Add,Edit,Delete,Sync', '1', '1', '1');
INSERT INTO `menu` VALUES ('6', 'Simple.view.DeptMain1', '曲线图图形报表', 'Chartcurve', '曲线图', '2', 'URL', '', 'Add,Edit,Delete,Sync', '1', '1', '1');
INSERT INTO `menu` VALUES ('7', 'Hope.app.sys.roleactions', '权限管理', 'Reportuser', '权限管理', '2', 'COMPONENT', 'RoleAction', 'Add,Edit,Delete,Sync', '2', '1', '1');
INSERT INTO `menu` VALUES ('8', 'Hope.app.sys.roles', '角色管理', 'Shield', '角色管理', '3', 'COMPONENT', 'Role', 'Add,Edit,Delete,Sync,Export', '2', '1', '1');
INSERT INTO `menu` VALUES ('9', 'Hope.app.sys.organizations', '部门管理', 'Chartorganisation', '部门管理', '1', 'COMPONENT', 'Organization', 'Add,Edit,Delete,Sync', '2', '1', '1');
INSERT INTO `menu` VALUES ('10', 'Hope.app.sys.users', '账号管理', 'Group', '账号管理', '0', 'COMPONENT', 'User', 'Add,Edit,Delete,Sync', '2', '1', '1');
INSERT INTO `menu` VALUES ('11', '', '数据字典', 'Bookopen', '数据字典', '0', 'COMPONENT', 'SysConfig', 'Add,Edit,Delete,Sync', '3', '1', '1');
INSERT INTO `menu` VALUES ('12', 'Hope.app.sys.rolemenus', '菜单管理', 'Outline', '菜单管理', '4', 'COMPONENT', 'RoleMenu', 'Add,Edit,Delete,Sync', '2', '1', '1');

-- ----------------------------
-- Table structure for organization
-- ----------------------------
DROP TABLE IF EXISTS `organization`;
CREATE TABLE `organization` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(64) NOT NULL COMMENT '机构名称',
  `description` varchar(64) DEFAULT NULL COMMENT '机构描述',
  `parent_id` int(11) DEFAULT NULL COMMENT '父机构',
  `sort` int(3) NOT NULL DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`),
  CONSTRAINT `organization_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `organization` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of organization
-- ----------------------------
INSERT INTO `organization` VALUES ('1', 'xx公司', null, null, '1');
INSERT INTO `organization` VALUES ('2', '财务部', '7772222', '1', '1');
INSERT INTO `organization` VALUES ('3', '人力资源部', '柔柔弱弱', '1', '2');
INSERT INTO `organization` VALUES ('4', '市场部', '0', '1', '3');
INSERT INTO `organization` VALUES ('5', '研发部', null, '1', '4');
INSERT INTO `organization` VALUES ('6', '研发一部', '111111', '5', '1');
INSERT INTO `organization` VALUES ('7', '研发二部', '5556666999', '5', '2');
INSERT INTO `organization` VALUES ('8', '12312', 'swfsf', '5', '3');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(40) NOT NULL COMMENT '权限名称',
  `description` varchar(255) DEFAULT NULL COMMENT '权限描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', 'user:edit', '修改用户1');
INSERT INTO `permission` VALUES ('2', 'user:delete', '注销用户4444');
INSERT INTO `permission` VALUES ('3', 'role:view', '浏览角色1');
INSERT INTO `permission` VALUES ('4', 'role:create', '创建角色');
INSERT INTO `permission` VALUES ('5', 'role:edit', '修改角色000');
INSERT INTO `permission` VALUES ('6', 'role:delete', '注销角色');
INSERT INTO `permission` VALUES ('7', 'user:view', '浏览用户');
INSERT INTO `permission` VALUES ('8', 'user:create', '创建用户');
INSERT INTO `permission` VALUES ('9', 'a:save', 'sfsdfsd');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(64) DEFAULT NULL COMMENT '角色名称',
  `description` varchar(128) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '管理员', '管理员');
INSERT INTO `role` VALUES ('2', '测试1', '修改代码测试123A');
INSERT INTO `role` VALUES ('3', '测试角色', '测试角色');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  KEY `menu_id` (`menu_id`),
  CONSTRAINT `role_menu_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `role_menu_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('15', '2', '1');
INSERT INTO `role_menu` VALUES ('16', '2', '4');
INSERT INTO `role_menu` VALUES ('17', '2', '5');
INSERT INTO `role_menu` VALUES ('18', '2', '6');
INSERT INTO `role_menu` VALUES ('19', '3', '2');
INSERT INTO `role_menu` VALUES ('20', '3', '10');
INSERT INTO `role_menu` VALUES ('21', '3', '9');
INSERT INTO `role_menu` VALUES ('22', '3', '7');
INSERT INTO `role_menu` VALUES ('23', '3', '8');
INSERT INTO `role_menu` VALUES ('24', '1', '1');
INSERT INTO `role_menu` VALUES ('25', '1', '4');
INSERT INTO `role_menu` VALUES ('26', '1', '2');
INSERT INTO `role_menu` VALUES ('27', '1', '10');
INSERT INTO `role_menu` VALUES ('28', '1', '9');
INSERT INTO `role_menu` VALUES ('29', '1', '7');
INSERT INTO `role_menu` VALUES ('30', '1', '8');
INSERT INTO `role_menu` VALUES ('31', '1', '12');
INSERT INTO `role_menu` VALUES ('32', '1', '3');
INSERT INTO `role_menu` VALUES ('33', '1', '11');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `permission_id` int(11) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  KEY `permission_id` (`permission_id`),
  CONSTRAINT `role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `role_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='角色-权限关系表';

-- ----------------------------
-- Records of role_permission
-- ----------------------------
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

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '2', 'admin', '721d083e6f2b7882ed04f698e31afca451252ddb', '15b136cda2ac0ce4', '管理员', '1', 'boychen@21cn.com', '', '123456789', '0', null, null, null, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '1');
INSERT INTO `user` VALUES ('4', '4', 'a1', 'd864a4dded527d467fc2bd24a26f0cb3372c4bb7', '910b1352d9605e93', '1', '2', '1@a.com', null, null, '0', null, null, null, '2014-11-09 00:07:26', '2014-11-09 00:07:26', '1');
INSERT INTO `user` VALUES ('5', '2', '2', '1f000cfc12cebb82ff0e05357bfa09aeb3bda29c', 'ae153f67dd3763ea', '2', '1', '2@b.com', null, null, '0', null, null, null, '2014-11-09 11:11:49', '2014-11-09 11:11:49', '1');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('2', '1', '1');
INSERT INTO `user_role` VALUES ('3', '1', '2');
INSERT INTO `user_role` VALUES ('9', '3', '3');
INSERT INTO `user_role` VALUES ('10', '2', '1');
INSERT INTO `user_role` VALUES ('11', '6', '3');
INSERT INTO `user_role` VALUES ('12', '7', '1');
INSERT INTO `user_role` VALUES ('22', '4', '2');
INSERT INTO `user_role` VALUES ('23', '5', '1');
