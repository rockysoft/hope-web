-- MySQL dump 10.13  Distrib 5.1.58, for Win32 (ia32)
--
-- Host: localhost    Database: hope
-- ------------------------------------------------------
-- Server version	5.1.58-community

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `log`
--

DROP TABLE IF EXISTS `log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '流水号',
  `user_id` int(11) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  `content` varchar(8000) NOT NULL DEFAULT '' COMMENT '日志内容',
  `operation` varchar(250) NOT NULL DEFAULT '' COMMENT '用户所做的操作',
  PRIMARY KEY (`id`),
  KEY `log_ibfk_1` (`user_id`),
  CONSTRAINT `log_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='审计日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log`
--

LOCK TABLES `log` WRITE;
/*!40000 ALTER TABLE `log` DISABLE KEYS */;
INSERT INTO `log` VALUES (1,1,'2015-04-12 13:41:09','saveUser[参数1，类型：User，值：(getEmail : 6@a.com)(getSalt : 714e1f8f5c659801)(getPlainPassword : 6)(getRoleList : [com.github.rockysoft.entity.Role@36793])(getLoginName : 6)(getOrgId : 3)(getRealName : 6)(getSex : 0)(getGmtCreate : Sun Apr 12 21:41:08 CST 2015)(getGmtModified : Sun Apr 12 21:41:08 CST 2015)(getId : 9)(getPassword : 7d8d03fe27d47304404723b2f4a98601e23e455a)(getStatus : 1)]','添加'),(2,1,'2015-04-12 13:45:33','deleteUser[参数1，类型：Integer，值：]','刪除'),(3,1,'2015-04-12 13:46:01','updateUser[参数1，类型：User，值：(getEmail : 4@a.com)(getPlainPassword : )(getRoleList : [com.github.rockysoft.entity.Role@144256a])(getLoginName : 4)(getOrgId : 4)(getRealName : 4)(getSex : 0)(getErrorCount : 0)(getGmtModified : Sun Apr 12 21:46:01 CST 2015)(getId : 7)(getPassword : )(getStatus : 1)]','修改'),(4,1,'2015-04-15 02:51:13','saveMenu[参数1，类型：Menu，值：(getUrl : Hope.app.sys.logs)(getSort : 5)(getParentId : 2)(getButtons : export)(getController : Log)(getId : 13)(getText : 审计日志)(getChildren : [])]','添加'),(5,1,'2015-04-15 02:51:42','saveRoleMenus[参数1，类型：Integer，值：][参数2，类型：String，值：(getBytes : [B@af0c94)]','添加'),(6,1,'2015-04-15 02:51:52','saveRoleMenus[参数1，类型：Integer，值：][参数2，类型：String，值：(getBytes : [B@2656c3)]','添加'),(7,1,'2015-04-16 09:41:16','saveMenu[参数1，类型：Menu，值：(getUrl : Hope.app.sys.loginlogs)(getSort : 6)(getParentId : 2)(getButtons : export)(getController : Log)(getId : 14)(getText : 登录日志)(getChildren : [])]','添加'),(8,1,'2015-04-16 09:41:47','saveRoleMenus[参数1，类型：Integer，值：][参数2，类型：String，值：(getBytes : [B@13954c3)]','添加'),(9,1,'2015-04-28 08:14:18','updateLogin[参数1，类型：User，值：(getLastLoginTime : Tue Apr 28 16:14:18 CST 2015)(getLastLoginIp : 127.0.0.1)(getId : 1)]','修改'),(10,1,'2015-04-28 08:14:49','updateLogin[参数1，类型：User，值：(getLastLoginTime : Tue Apr 28 16:14:48 CST 2015)(getLastLoginIp : 127.0.0.1)(getId : 1)]','修改'),(11,1,'2015-04-28 08:17:35','updateLogin[参数1，类型：User，值：(getLastLoginTime : Tue Apr 28 16:17:35 CST 2015)(getLastLoginIp : 127.0.0.1)(getId : 1)]','修改'),(12,1,'2015-04-28 08:20:45','updateLogin[参数1，类型：User，值：(getLastLoginTime : Tue Apr 28 16:20:45 CST 2015)(getLastLoginIp : 127.0.0.1)(getId : 1)]','修改'),(13,4,'2015-11-07 02:26:42','updateLogin[参数1，类型：User，值：(getLastLoginTime : Sat Nov 07 10:26:42 CST 2015)(getLastLoginIp : 127.0.0.1)(getId : 4)]','修改'),(14,4,'2015-11-07 02:27:57','updateLogin[参数1，类型：User，值：(getLastLoginTime : Sat Nov 07 10:27:57 CST 2015)(getLastLoginIp : 127.0.0.1)(getId : 4)]','修改'),(15,1,'2015-11-07 02:28:27','updateLogin[参数1，类型：User，值：(getLastLoginTime : Sat Nov 07 10:28:27 CST 2015)(getLastLoginIp : 127.0.0.1)(getId : 1)]','修改'),(16,1,'2015-11-07 03:17:31','updateLogin[参数1，类型：User，值：(getLastLoginTime : Sat Nov 07 11:17:31 CST 2015)(getLastLoginIp : 127.0.0.1)(getId : 1)]','修改');
/*!40000 ALTER TABLE `log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login_log`
--

DROP TABLE IF EXISTS `login_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COMMENT='登录日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login_log`
--

LOCK TABLES `login_log` WRITE;
/*!40000 ALTER TABLE `login_log` DISABLE KEYS */;
INSERT INTO `login_log` VALUES (21,NULL,'admin','127.0.0.1',1,2,'2015-04-24 10:19:11'),(22,1,'admin','127.0.0.1',1,1,'2015-04-24 10:19:19'),(23,1,'admin','127.0.0.1',2,3,'2015-04-24 10:19:30'),(24,1,'admin','127.0.0.1',1,1,'2015-04-24 10:19:34'),(25,1,'admin','127.0.0.1',2,3,'2015-04-24 10:20:02'),(26,NULL,'admin','127.0.0.1',1,2,'2015-04-28 07:09:14'),(27,1,'admin','127.0.0.1',1,1,'2015-04-28 07:09:18'),(28,1,'admin','127.0.0.1',2,3,'2015-04-28 07:57:39'),(29,1,'admin','127.0.0.1',1,1,'2015-04-28 07:57:47'),(30,1,'admin','127.0.0.1',1,1,'2015-04-28 08:09:08'),(31,1,'admin','127.0.0.1',2,3,'2015-04-28 08:09:26'),(32,1,'admin','127.0.0.1',1,1,'2015-04-28 08:09:41'),(33,1,'admin','127.0.0.1',1,1,'2015-04-28 08:10:56'),(34,1,'admin','127.0.0.1',2,3,'2015-04-28 08:11:05'),(35,1,'admin','127.0.0.1',1,1,'2015-04-28 08:11:10'),(36,1,'admin','127.0.0.1',2,3,'2015-04-28 08:14:13'),(37,1,'admin','127.0.0.1',1,1,'2015-04-28 08:14:18'),(38,1,'admin','127.0.0.1',2,3,'2015-04-28 08:14:45'),(39,1,'admin','127.0.0.1',1,1,'2015-04-28 08:14:48'),(40,1,'admin','127.0.0.1',2,3,'2015-04-28 08:16:43'),(41,1,'admin','127.0.0.1',1,1,'2015-04-28 08:17:35'),(42,1,'admin','127.0.0.1',2,3,'2015-04-28 08:20:38'),(43,1,'admin','127.0.0.1',1,1,'2015-04-28 08:20:45'),(44,1,'admin','127.0.0.1',2,3,'2015-04-28 08:20:58'),(45,NULL,'admin','127.0.0.1',1,2,'2015-11-07 02:23:28'),(46,NULL,'admin','127.0.0.1',1,2,'2015-11-07 02:23:34'),(47,NULL,'admin','127.0.0.1',1,2,'2015-11-07 02:23:38'),(48,NULL,'admin','127.0.0.1',1,2,'2015-11-07 02:24:40'),(49,NULL,'admin','127.0.0.1',1,2,'2015-11-07 02:25:09'),(50,NULL,'admin','127.0.0.1',1,2,'2015-11-07 02:25:20'),(51,NULL,'admin','127.0.0.1',1,2,'2015-11-07 02:25:51'),(52,NULL,'admin','127.0.0.1',1,2,'2015-11-07 02:26:05'),(53,NULL,'admin','127.0.0.1',1,2,'2015-11-07 02:26:20'),(54,4,'a1','127.0.0.1',1,1,'2015-11-07 02:26:42'),(55,4,'a1','127.0.0.1',2,3,'2015-11-07 02:27:02'),(56,NULL,'admin','127.0.0.1',1,2,'2015-11-07 02:27:08'),(57,NULL,'admin','127.0.0.1',1,2,'2015-11-07 02:27:14'),(58,NULL,'admin','127.0.0.1',1,2,'2015-11-07 02:27:22'),(59,NULL,'admin','127.0.0.1',1,2,'2015-11-07 02:27:39'),(60,4,'a1','127.0.0.1',1,1,'2015-11-07 02:27:57'),(61,4,'a1','127.0.0.1',2,3,'2015-11-07 02:28:04'),(62,NULL,'admin','127.0.0.1',1,2,'2015-11-07 02:28:18'),(63,1,'admin','127.0.0.1',1,1,'2015-11-07 02:28:27'),(64,1,'admin','127.0.0.1',1,1,'2015-11-07 03:17:31');
/*!40000 ALTER TABLE `login_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES (1,NULL,'图形报表根菜单','Chartbar','图形报表',0,'MENU',NULL,'',NULL,1,0),(2,NULL,'系统管理','Cog','系统管理',1,'MENU',NULL,'',NULL,1,0),(3,NULL,'资源管理菜单','Pagepaste','资源管理',2,'MENU',NULL,NULL,NULL,1,0),(4,'Simple.view.user.List1','饼状图图形报表','Chartpie','饼状图',0,'URL','','create,update,delete,sync',1,1,1),(5,'Simple.view.permission.List1','线性图图形报表','Chartline','线状图',1,'URL','','create,update,delete,sync',1,1,1),(6,'Simple.view.DeptMain1','曲线图图形报表','Chartcurve','曲线图',2,'URL','','create,update,delete,sync',1,1,1),(7,'Hope.app.sys.roleactions','权限管理','Reportuser','权限管理',2,'COMPONENT','permission','create,update,delete,sync',2,1,1),(8,'Hope.app.sys.roles','角色管理','Shield','角色管理',3,'COMPONENT','role','create,update,delete,sync,export',2,1,1),(9,'Hope.app.sys.organizations','部门管理','Chartorganisation','部门管理',1,'COMPONENT','organization','create,update,delete',2,1,1),(10,'Hope.app.sys.users','账号管理','Group','账号管理',0,'COMPONENT','user','create,update,delete,sync',2,1,1),(11,'','数据字典','Bookopen','数据字典',0,'COMPONENT','sysConfig','create,update,delete,sync',3,1,1),(12,'Hope.app.sys.rolemenus','菜单管理','Outline','菜单管理',4,'COMPONENT','menu','create,update,delete,sync',2,1,1),(13,'Hope.app.sys.logs',NULL,NULL,'审计日志',5,NULL,'Log','export',2,NULL,NULL),(14,'Hope.app.sys.loginlogs',NULL,NULL,'登录日志',6,NULL,'LoginLog','export',2,NULL,NULL);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organization`
--

DROP TABLE IF EXISTS `organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organization`
--

LOCK TABLES `organization` WRITE;
/*!40000 ALTER TABLE `organization` DISABLE KEYS */;
INSERT INTO `organization` VALUES (1,'xx公司',NULL,NULL,1),(2,'财务部','7772222',1,1),(3,'人力资源部','柔柔弱弱',1,2),(4,'市场部','0',1,3),(5,'研发部',NULL,1,4),(6,'研发一部','111111',5,1),(7,'研发二部','5556666999',5,2);
/*!40000 ALTER TABLE `organization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(40) NOT NULL COMMENT '权限名称',
  `description` varchar(255) DEFAULT NULL COMMENT '权限描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,'user:update','修改用户1'),(2,'user:delete','注销用户4444'),(3,'role:view','浏览角色1'),(4,'role:create','创建角色'),(5,'role:update','修改角色000'),(6,'role:delete','注销角色'),(7,'user:view','浏览用户'),(8,'user:create','创建用户'),(9,'organization:create','创建部门'),(10,'organization:update','修改部门'),(11,'organization:delete','删除部门'),(12,'organization:view','浏览部门'),(13,'permission:create','创建权限'),(14,'permission:update','修改权限'),(15,'permission:delete','删除权限'),(16,'menu:create','创建菜单'),(17,'menu:update','修改菜单'),(18,'menu:delete','删除菜单');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(64) DEFAULT NULL COMMENT '角色名称',
  `description` varchar(128) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Administrators','管理员'),(2,'Users','修改代码测试123A'),(3,'测试角色','测试角色');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_menu`
--

DROP TABLE IF EXISTS `role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  KEY `menu_id` (`menu_id`),
  CONSTRAINT `role_menu_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `role_menu_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_menu`
--

LOCK TABLES `role_menu` WRITE;
/*!40000 ALTER TABLE `role_menu` DISABLE KEYS */;
INSERT INTO `role_menu` VALUES (15,2,1),(16,2,4),(17,2,5),(18,2,6),(19,3,2),(20,3,10),(21,3,9),(22,3,7),(23,3,8),(58,1,1),(59,1,4),(60,1,5),(61,1,6),(62,1,2),(63,1,10),(64,1,9),(65,1,7),(66,1,8),(67,1,12),(68,1,13),(69,1,14),(70,1,3),(71,1,11);
/*!40000 ALTER TABLE `role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permission`
--

DROP TABLE IF EXISTS `role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permission`
--

LOCK TABLES `role_permission` WRITE;
/*!40000 ALTER TABLE `role_permission` DISABLE KEYS */;
INSERT INTO `role_permission` VALUES (4,1,4),(5,1,5),(6,1,6),(7,1,7),(8,1,8),(9,2,8),(10,2,7),(11,2,1),(12,2,2),(13,3,3),(14,3,4),(15,3,5),(16,3,6),(17,1,1),(18,1,4),(19,1,5),(20,1,6),(21,1,7),(22,1,8),(23,1,2),(24,1,3),(25,1,4),(26,1,5),(27,1,6),(28,1,7),(29,1,8),(30,1,1),(31,1,2),(32,1,3),(33,1,9),(34,1,10),(35,1,11),(36,1,12),(37,1,4),(38,1,5),(39,1,6),(40,1,7),(41,1,8),(42,1,1),(43,1,2),(44,1,3),(45,1,9),(46,1,10),(47,1,11),(48,1,12),(49,1,13),(50,1,14),(51,1,15),(52,1,16),(53,1,17),(54,1,18);
/*!40000 ALTER TABLE `role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,2,'admin','90dc23253021c713053cb915765c3052bafa0891','201f9dec2a970c12','管理员',1,'boychen111@21cn.com','','123456789',0,'2015-11-07 11:17:31','127.0.0.1',NULL,'0000-00-00 00:00:00','2015-04-03 10:41:57',1),(4,4,'a1','d864a4dded527d467fc2bd24a26f0cb3372c4bb7','910b1352d9605e93','1',2,'1@a.com',NULL,NULL,0,'2015-11-07 10:27:57','127.0.0.1',NULL,'2014-11-09 00:07:26','2014-11-09 00:07:26',1),(5,2,'2','1f000cfc12cebb82ff0e05357bfa09aeb3bda29c','ae153f67dd3763ea','2',1,'2@b.com',NULL,NULL,0,NULL,NULL,NULL,'2014-11-09 11:11:49','2014-11-09 11:11:49',1),(6,2,'3','798d17c56b0586b14df898560b2bd825bdc4f906','6dfe2ac5ec997f49','3',0,'3@a.com',NULL,NULL,0,NULL,NULL,NULL,'2015-04-12 21:25:57','2015-04-12 21:25:57',1),(7,4,'4','9a253be7e03af184d5503191963a2283d9aaacc8','df9f099b3433f71b','4',0,'4@a.com',NULL,NULL,0,NULL,NULL,NULL,'2015-04-12 21:29:56','2015-04-12 21:29:56',1),(9,3,'6','7d8d03fe27d47304404723b2f4a98601e23e455a','714e1f8f5c659801','6',0,'6@a.com',NULL,NULL,0,NULL,NULL,NULL,'2015-04-12 21:41:08','2015-04-12 21:41:08',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (2,1,1),(3,1,2),(9,3,3),(10,2,1),(22,4,2),(23,5,1),(24,6,2),(26,8,1),(27,9,1),(28,7,1);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-11-09 23:12:27
