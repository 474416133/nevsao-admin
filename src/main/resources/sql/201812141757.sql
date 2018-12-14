-- MySQL dump 10.13  Distrib 8.0.13, for Linux (x86_64)
--
-- Host: localhost    Database: nevsao_admin
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sys_dept`
--

DROP TABLE IF EXISTS `sys_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_dept` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父部门',
  `name` varchar(64) DEFAULT NULL COMMENT '名字',
  `order_seq` int(10) DEFAULT NULL COMMENT '排序',
  `creater_id` varchar(32) DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modifier_id` varchar(32) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_INDEX_NAME` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept`
--

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;
INSERT INTO `sys_dept` VALUES ('1','0','开发部',NULL,NULL,'2018-01-04 15:42:26',NULL,NULL),('2','1','开发一部',NULL,NULL,'2018-01-04 15:42:34',NULL,NULL),('3','1','开发二部',NULL,NULL,'2018-01-04 15:42:29',NULL,NULL),('4','0','市场部',NULL,NULL,'2018-01-04 15:42:36',NULL,NULL),('5','0','人事部',NULL,NULL,'2018-01-04 15:42:32',NULL,NULL),('6','0','测试部',NULL,NULL,'2018-01-04 15:42:38',NULL,NULL);
/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict`
--

DROP TABLE IF EXISTS `sys_dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_dict` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `dict_key` varchar(32) DEFAULT NULL COMMENT '字典key',
  `dict_value` varchar(64) DEFAULT NULL COMMENT '字典value',
  `column_name` varchar(32) DEFAULT NULL COMMENT '列名',
  `table_name` varchar(32) DEFAULT NULL COMMENT '表名',
  `creater_id` varchar(32) DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modifier_id` varchar(32) DEFAULT NULL COMMENT '修改人id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_INDEX_DICT_KEY` (`dict_key`,`column_name`,`table_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict`
--

LOCK TABLES `sys_dict` WRITE;
/*!40000 ALTER TABLE `sys_dict` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_dict` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_menu` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父菜单',
  `name` varchar(64) DEFAULT NULL COMMENT '名字',
  `url` varchar(200) DEFAULT NULL COMMENT '地址',
  `perms` varchar(200) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(64) DEFAULT NULL COMMENT '图标',
  `menu_type` char(1) DEFAULT NULL COMMENT '类型，0/1=菜单/按钮',
  `order_seq` int(10) DEFAULT NULL COMMENT '排序',
  `creater_id` varchar(32) DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modifier_id` varchar(32) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_INDEX_NAME` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES ('1','0','系统管理',NULL,NULL,'zmdi zmdi-settings','0',1,NULL,'2017-12-27 16:39:07',NULL,NULL),('10','2','系统日志','log','log:list','','0',3,NULL,'2017-12-27 17:00:50','2018-04-25 09:02:18',NULL),('101','0','任务调度',NULL,NULL,'zmdi zmdi-alarm','0',NULL,NULL,'2018-02-24 15:52:57',NULL,NULL),('102','101','定时任务','job','job:list','','0',NULL,NULL,'2018-02-24 15:53:53','2018-04-25 09:05:12',NULL),('103','102','新增任务',NULL,'job:add',NULL,'1',NULL,NULL,'2018-02-24 15:55:10',NULL,NULL),('104','102','修改任务',NULL,'job:update',NULL,'1',NULL,NULL,'2018-02-24 15:55:53',NULL,NULL),('105','102','删除任务',NULL,'job:delete',NULL,'1',NULL,NULL,'2018-02-24 15:56:18',NULL,NULL),('106','102','暂停任务',NULL,'job:pause',NULL,'1',NULL,NULL,'2018-02-24 15:57:08',NULL,NULL),('107','102','恢复任务',NULL,'job:resume',NULL,'1',NULL,NULL,'2018-02-24 15:58:21',NULL,NULL),('108','102','立即执行任务',NULL,'job:run',NULL,'1',NULL,NULL,'2018-02-24 15:59:45',NULL,NULL),('109','101','调度日志','jobLog','jobLog:list','','0',NULL,NULL,'2018-02-24 16:00:45','2018-04-25 09:05:25',NULL),('11','3','新增用户',NULL,'user:add',NULL,'1',NULL,NULL,'2017-12-27 17:02:58',NULL,NULL),('113','2','Redis监控','redis/info','redis:list','','0',NULL,NULL,'2018-06-28 14:29:42',NULL,NULL),('114','2','Redis终端','redis/terminal','redis:terminal','','0',NULL,NULL,'2018-06-28 15:35:21',NULL,NULL),('12','3','修改用户',NULL,'user:update',NULL,'1',NULL,NULL,'2017-12-27 17:04:07',NULL,NULL),('13','3','删除用户',NULL,'user:delete',NULL,'1',NULL,NULL,'2017-12-27 17:04:58',NULL,NULL),('14','4','新增角色',NULL,'role:add',NULL,'1',NULL,NULL,'2017-12-27 17:06:38',NULL,NULL),('15','4','修改角色',NULL,'role:update',NULL,'1',NULL,NULL,'2017-12-27 17:06:38',NULL,NULL),('16','4','删除角色',NULL,'role:delete',NULL,'1',NULL,NULL,'2017-12-27 17:06:38',NULL,NULL),('17','5','新增菜单',NULL,'menu:add',NULL,'1',NULL,NULL,'2017-12-27 17:08:02',NULL,NULL),('18','5','修改菜单',NULL,'menu:update',NULL,'1',NULL,NULL,'2017-12-27 17:08:02',NULL,NULL),('19','5','删除菜单',NULL,'menu:delete',NULL,'1',NULL,NULL,'2017-12-27 17:08:02',NULL,NULL),('2','0','系统监控',NULL,NULL,'zmdi zmdi-shield-security','0',2,NULL,'2017-12-27 16:45:51','2018-01-17 17:08:28',NULL),('20','6','新增部门',NULL,'dept:add',NULL,'1',NULL,NULL,'2017-12-27 17:09:24',NULL,NULL),('21','6','修改部门',NULL,'dept:update',NULL,'1',NULL,NULL,'2017-12-27 17:09:24',NULL,NULL),('22','6','删除部门',NULL,'dept:delete',NULL,'1',NULL,NULL,'2017-12-27 17:09:24',NULL,NULL),('23','8','踢出用户',NULL,'user:kickout',NULL,'1',NULL,NULL,'2017-12-27 17:11:13',NULL,NULL),('24','10','删除日志',NULL,'log:delete',NULL,'1',NULL,NULL,'2017-12-27 17:11:45',NULL,NULL),('3','1','用户管理','user','user:list','','0',1,NULL,'2017-12-27 16:47:13','2018-04-25 09:00:01',NULL),('4','1','角色管理','role','role:list','','0',2,NULL,'2017-12-27 16:48:09','2018-04-25 09:01:12',NULL),('5','1','菜单管理','menu','menu:list','','0',3,NULL,'2017-12-27 16:48:57','2018-04-25 09:01:30',NULL),('58','0','网络资源',NULL,NULL,'zmdi zmdi-globe-alt','0',NULL,NULL,'2018-01-12 15:28:48','2018-01-22 19:49:26',NULL),('59','58','天气查询','weather','weather:list','','0',NULL,NULL,'2018-01-12 15:40:02','2018-04-25 09:02:57',NULL),('6','1','部门管理','dept','dept:list','','0',4,NULL,'2017-12-27 16:57:33','2018-04-25 09:01:40',NULL),('61','58','每日一文','article','article:list','','0',NULL,NULL,'2018-01-15 17:17:14','2018-04-25 09:03:08',NULL),('64','1','字典管理','dict','dict:list','','0',NULL,NULL,'2018-01-18 10:38:25','2018-04-25 09:01:50',NULL),('65','64','新增字典',NULL,'dict:add',NULL,'1',NULL,NULL,'2018-01-18 19:10:08',NULL,NULL),('66','64','修改字典',NULL,'dict:update',NULL,'1',NULL,NULL,'2018-01-18 19:10:27',NULL,NULL),('67','64','删除字典',NULL,'dict:delete',NULL,'1',NULL,NULL,'2018-01-18 19:10:47',NULL,NULL),('8','2','在线用户','session','session:list','','0',1,NULL,'2017-12-27 16:59:33','2018-04-25 09:02:04',NULL),('81','58','影视资讯',NULL,NULL,NULL,'0',NULL,NULL,'2018-01-22 14:12:59',NULL,NULL),('82','81','正在热映','movie/hot','movie:hot','','0',NULL,NULL,'2018-01-22 14:13:47','2018-04-25 09:03:48',NULL),('83','81','即将上映','movie/coming','movie:coming','','0',NULL,NULL,'2018-01-22 14:14:36','2018-04-25 09:04:05',NULL),('86','58','One 一个',NULL,NULL,NULL,'0',NULL,NULL,'2018-01-26 09:42:41','2018-01-26 09:43:46',NULL),('87','86','绘画','one/painting','one:painting','','0',NULL,NULL,'2018-01-26 09:47:14','2018-04-25 09:04:17',NULL),('88','86','语文','one/yuwen','one:yuwen','','0',NULL,NULL,'2018-01-26 09:47:40','2018-04-25 09:04:30',NULL),('89','86','散文','one/essay','one:essay','','0',NULL,NULL,'2018-01-26 09:48:05','2018-04-25 09:04:42',NULL);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_role` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `name` varchar(64) DEFAULT NULL COMMENT '名字',
  `remark` varchar(200) DEFAULT NULL COMMENT '描述',
  `creater_id` varchar(32) DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modifier_id` varchar(32) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_INDEX_NAME` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES ('1','管理员','管理员',NULL,'2017-12-27 16:23:11','2018-02-24 16:01:45',NULL),('2','测试账号','测试账号',NULL,'2017-12-27 16:25:09','2018-01-23 09:11:11',NULL),('23','用户管理员','负责用户的增删改操作',NULL,'2018-01-09 15:32:41',NULL,NULL),('24','系统监控员','可查看系统监控信息，但不可操作',NULL,'2018-01-09 15:52:01','2018-03-07 19:05:33',NULL),('25','用户查看','查看用户，无相应操作权限',NULL,'2018-01-09 15:56:30',NULL,NULL),('3','注册账户','注册账户，只可查看，不可操作',NULL,'2017-12-29 16:00:15','2018-02-24 17:33:45',NULL),('63','影院工作者','可查看影视信息',NULL,'2018-02-06 08:48:28','2018-03-07 19:05:26',NULL),('64','天气预报员','可查看天气预报信息',NULL,'2018-02-27 08:47:04',NULL,NULL),('65','文章审核','文章类',NULL,'2018-02-27 08:48:01','2018-03-13 11:20:34',NULL);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_role_menu` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `role_id` varchar(32) DEFAULT NULL COMMENT '角色id',
  `menu_id` varchar(32) DEFAULT NULL COMMENT '菜单id',
  `creater_id` varchar(32) DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modifier_id` varchar(32) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_INDEX_ROLE_ID_MENU_ID` (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` VALUES ('1','3','58',NULL,NULL,NULL,NULL),('10','3','89',NULL,NULL,NULL,NULL),('100','1','113',NULL,NULL,NULL,NULL),('101','1','114',NULL,NULL,NULL,NULL),('11','3','1',NULL,NULL,NULL,NULL),('12','3','3',NULL,NULL,NULL,NULL),('13','3','4',NULL,NULL,NULL,NULL),('14','3','5',NULL,NULL,NULL,NULL),('15','3','6',NULL,NULL,NULL,NULL),('16','3','64',NULL,NULL,NULL,NULL),('17','3','2',NULL,NULL,NULL,NULL),('18','3','8',NULL,NULL,NULL,NULL),('19','3','10',NULL,NULL,NULL,NULL),('2','3','59',NULL,NULL,NULL,NULL),('20','3','101',NULL,NULL,NULL,NULL),('21','3','102',NULL,NULL,NULL,NULL),('22','3','109',NULL,NULL,NULL,NULL),('23','63','58',NULL,NULL,NULL,NULL),('24','63','81',NULL,NULL,NULL,NULL),('25','63','82',NULL,NULL,NULL,NULL),('26','63','83',NULL,NULL,NULL,NULL),('27','24','8',NULL,NULL,NULL,NULL),('28','24','2',NULL,NULL,NULL,NULL),('29','24','10',NULL,NULL,NULL,NULL),('3','3','61',NULL,NULL,NULL,NULL),('30','65','86',NULL,NULL,NULL,NULL),('31','65','88',NULL,NULL,NULL,NULL),('32','65','89',NULL,NULL,NULL,NULL),('33','65','58',NULL,NULL,NULL,NULL),('34','65','61',NULL,NULL,NULL,NULL),('35','2','81',NULL,NULL,NULL,NULL),('36','2','61',NULL,NULL,NULL,NULL),('37','2','24',NULL,NULL,NULL,NULL),('38','2','82',NULL,NULL,NULL,NULL),('39','2','83',NULL,NULL,NULL,NULL),('4','3','81',NULL,NULL,NULL,NULL),('40','2','58',NULL,NULL,NULL,NULL),('41','2','59',NULL,NULL,NULL,NULL),('42','2','2',NULL,NULL,NULL,NULL),('43','2','8',NULL,NULL,NULL,NULL),('44','2','10',NULL,NULL,NULL,NULL),('45','23','11',NULL,NULL,NULL,NULL),('46','23','12',NULL,NULL,NULL,NULL),('47','23','13',NULL,NULL,NULL,NULL),('48','23','3',NULL,NULL,NULL,NULL),('49','23','1',NULL,NULL,NULL,NULL),('5','3','82',NULL,NULL,NULL,NULL),('50','25','1',NULL,NULL,NULL,NULL),('51','25','3',NULL,NULL,NULL,NULL),('52','1','59',NULL,NULL,NULL,NULL),('53','1','2',NULL,NULL,NULL,NULL),('54','1','3',NULL,NULL,NULL,NULL),('55','1','67',NULL,NULL,NULL,NULL),('56','1','1',NULL,NULL,NULL,NULL),('57','1','4',NULL,NULL,NULL,NULL),('58','1','5',NULL,NULL,NULL,NULL),('59','1','6',NULL,NULL,NULL,NULL),('6','3','83',NULL,NULL,NULL,NULL),('60','1','20',NULL,NULL,NULL,NULL),('61','1','21',NULL,NULL,NULL,NULL),('62','1','22',NULL,NULL,NULL,NULL),('63','1','10',NULL,NULL,NULL,NULL),('64','1','8',NULL,NULL,NULL,NULL),('65','1','58',NULL,NULL,NULL,NULL),('66','1','66',NULL,NULL,NULL,NULL),('67','1','11',NULL,NULL,NULL,NULL),('68','1','12',NULL,NULL,NULL,NULL),('69','1','64',NULL,NULL,NULL,NULL),('7','3','86',NULL,NULL,NULL,NULL),('70','1','13',NULL,NULL,NULL,NULL),('71','1','14',NULL,NULL,NULL,NULL),('72','1','65',NULL,NULL,NULL,NULL),('73','1','15',NULL,NULL,NULL,NULL),('74','1','16',NULL,NULL,NULL,NULL),('75','1','17',NULL,NULL,NULL,NULL),('76','1','18',NULL,NULL,NULL,NULL),('77','1','23',NULL,NULL,NULL,NULL),('78','1','81',NULL,NULL,NULL,NULL),('79','1','82',NULL,NULL,NULL,NULL),('8','3','87',NULL,NULL,NULL,NULL),('80','1','83',NULL,NULL,NULL,NULL),('81','1','19',NULL,NULL,NULL,NULL),('82','1','24',NULL,NULL,NULL,NULL),('83','1','61',NULL,NULL,NULL,NULL),('84','1','86',NULL,NULL,NULL,NULL),('85','1','87',NULL,NULL,NULL,NULL),('86','1','88',NULL,NULL,NULL,NULL),('87','1','89',NULL,NULL,NULL,NULL),('88','1','101',NULL,NULL,NULL,NULL),('89','1','102',NULL,NULL,NULL,NULL),('9','3','88',NULL,NULL,NULL,NULL),('90','1','103',NULL,NULL,NULL,NULL),('91','1','104',NULL,NULL,NULL,NULL),('92','1','105',NULL,NULL,NULL,NULL),('93','1','106',NULL,NULL,NULL,NULL),('94','1','107',NULL,NULL,NULL,NULL),('95','1','108',NULL,NULL,NULL,NULL),('96','1','109',NULL,NULL,NULL,NULL),('97','1','110',NULL,NULL,NULL,NULL),('98','64','59',NULL,NULL,NULL,NULL),('99','64','58',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_user` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `username` varchar(16) DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `dept_id` varchar(32) DEFAULT NULL COMMENT '部门id',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(16) DEFAULT NULL COMMENT '手机',
  `gender` char(1) DEFAULT NULL COMMENT '性别',
  `avatar` varchar(200) DEFAULT NULL COMMENT '头像',
  `theme_using` varchar(64) DEFAULT NULL COMMENT '正在使用的主题id',
  `is_active` int(11) DEFAULT NULL COMMENT '账号状态 0/1=禁用/启用',
  `remark` varchar(200) DEFAULT NULL COMMENT '账号备注',
  `last_login_time` datetime DEFAULT NULL COMMENT '最近登录时间',
  `last_login_ip` int(10) DEFAULT NULL COMMENT '最近登录ip',
  `creater_id` varchar(32) DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modifier_id` varchar(32) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_INDEX_USERNAME` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES ('4','MrBird','42ee25d1e43e9f57119a00d0a39e5250','5','mrbird@hotmail.com','13455533222','0','default.jpg','green',1,'我是作者。','2018-04-02 17:29:32',NULL,NULL,'2017-12-27 15:47:19','2018-03-21 09:05:12',NULL);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_log`
--

DROP TABLE IF EXISTS `sys_user_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_user_log` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户id',
  `username` varchar(64) DEFAULT NULL COMMENT '用户名',
  `op_desc` varchar(200) DEFAULT NULL COMMENT '操作描述',
  `op_time` datetime DEFAULT NULL COMMENT '操作时间',
  `op_method` varchar(500) DEFAULT NULL COMMENT '操作方法',
  `op_params` varchar(1000) DEFAULT NULL COMMENT '操作参数',
  `op_ip` int(10) DEFAULT NULL COMMENT '操作ip',
  `op_location` varchar(64) DEFAULT NULL COMMENT '操作地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_log`
--

LOCK TABLES `sys_user_log` WRITE;
/*!40000 ALTER TABLE `sys_user_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_user_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_user_role` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户id',
  `role_id` varchar(32) DEFAULT NULL COMMENT '角色id',
  `creater_id` varchar(32) DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modifier_id` varchar(32) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_INDEX_USER_ID_ROLE_ID` (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES ('1','4','1',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-14 17:57:14
