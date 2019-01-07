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
-- Table structure for table `QRTZ_BLOB_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_BLOB_TRIGGERS`
--

LOCK TABLES `QRTZ_BLOB_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_BLOB_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_BLOB_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_CALENDARS`
--

DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `QRTZ_CALENDARS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_CALENDARS`
--

LOCK TABLES `QRTZ_CALENDARS` WRITE;
/*!40000 ALTER TABLE `QRTZ_CALENDARS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_CALENDARS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_CRON_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(200) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_CRON_TRIGGERS`
--

LOCK TABLES `QRTZ_CRON_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_CRON_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_CRON_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_FIRED_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_FIRED_TRIGGERS`
--

LOCK TABLES `QRTZ_FIRED_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_FIRED_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_FIRED_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_JOB_DETAILS`
--

DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `QRTZ_JOB_DETAILS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_JOB_DETAILS`
--

LOCK TABLES `QRTZ_JOB_DETAILS` WRITE;
/*!40000 ALTER TABLE `QRTZ_JOB_DETAILS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_JOB_DETAILS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_LOCKS`
--

DROP TABLE IF EXISTS `QRTZ_LOCKS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `QRTZ_LOCKS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_LOCKS`
--

LOCK TABLES `QRTZ_LOCKS` WRITE;
/*!40000 ALTER TABLE `QRTZ_LOCKS` DISABLE KEYS */;
INSERT INTO `QRTZ_LOCKS` VALUES ('MyScheduler','STATE_ACCESS'),('MyScheduler','TRIGGER_ACCESS');
/*!40000 ALTER TABLE `QRTZ_LOCKS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_PAUSED_TRIGGER_GRPS`
--

DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_PAUSED_TRIGGER_GRPS`
--

LOCK TABLES `QRTZ_PAUSED_TRIGGER_GRPS` WRITE;
/*!40000 ALTER TABLE `QRTZ_PAUSED_TRIGGER_GRPS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_PAUSED_TRIGGER_GRPS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_SCHEDULER_STATE`
--

DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_SCHEDULER_STATE`
--

LOCK TABLES `QRTZ_SCHEDULER_STATE` WRITE;
/*!40000 ALTER TABLE `QRTZ_SCHEDULER_STATE` DISABLE KEYS */;
INSERT INTO `QRTZ_SCHEDULER_STATE` VALUES ('MyScheduler','sven-ThinkPad-E4501546847804194',1546855430237,15000);
/*!40000 ALTER TABLE `QRTZ_SCHEDULER_STATE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_SIMPLE_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_SIMPLE_TRIGGERS`
--

LOCK TABLES `QRTZ_SIMPLE_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_SIMPLE_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_SIMPLE_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_SIMPROP_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_SIMPROP_TRIGGERS`
--

LOCK TABLES `QRTZ_SIMPROP_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_SIMPROP_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_SIMPROP_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `QRTZ_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`sched_name`, `job_name`, `job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_TRIGGERS`
--

LOCK TABLES `QRTZ_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

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
  `principal_id` varchar(200) DEFAULT NULL COMMENT '负责人',
  `manager_id` varchar(32) DEFAULT NULL COMMENT '负责人',
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
INSERT INTO `sys_dept` VALUES ('6f8337342dc24e88b9fbc2aec2c0b440',NULL,'人事部',NULL,NULL,NULL,NULL,NULL,NULL,NULL);
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
  `dict_key_rank` int(11) DEFAULT NULL COMMENT '键排序',
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
-- Table structure for table `sys_job`
--

DROP TABLE IF EXISTS `sys_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_job` (
  `id` varchar(32) NOT NULL COMMENT '任务id',
  `bean_name` varchar(100) NOT NULL COMMENT 'spring bean名称',
  `method_name` varchar(100) NOT NULL COMMENT '方法名',
  `method_params` varchar(200) DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) NOT NULL COMMENT 'cron表达式',
  `status` tinyint(4) NOT NULL COMMENT '任务状态  0：正常  1：暂停',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_job`
--

LOCK TABLES `sys_job` WRITE;
/*!40000 ALTER TABLE `sys_job` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_job_log`
--

DROP TABLE IF EXISTS `sys_job_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_job_log` (
  `id` varchar(32) NOT NULL COMMENT '任务日志id',
  `job_id` varchar(32) NOT NULL COMMENT '任务id',
  `bean_name` varchar(100) NOT NULL COMMENT 'spring bean名称',
  `method_name` varchar(100) NOT NULL COMMENT '方法名',
  `method_params` varchar(200) DEFAULT NULL COMMENT '参数',
  `ret_status` tinyint(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `ret_remark` text COMMENT '失败信息',
  `period_time` decimal(11,0) DEFAULT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_job_log`
--

LOCK TABLES `sys_job_log` WRITE;
/*!40000 ALTER TABLE `sys_job_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_job_log` ENABLE KEYS */;
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
INSERT INTO `sys_menu` VALUES ('0986485003eb4ac7bad0460fc6ae7d94','4cfa4a29183d4cda9a11954f272d31fb','修改字典',NULL,'system:dict:update',NULL,'1',NULL,NULL,NULL,NULL,NULL),('09a4ce59641343f394352e1b88c2364f','42c9f16445c643b5b1421d9b03d1d2be','用户管理','system/user','system:user:list','','0',1,NULL,NULL,NULL,NULL),('16afaccebb2f49188890cc0b90187b93','bfab6f8d126240acaa8531d0c58202dd','新增任务',NULL,'system:job:add',NULL,'1',NULL,NULL,NULL,NULL,NULL),('17a6c45a548d44bdb32d52ae25c641be','2d3f1593f5964fde84e1a00346c13cc3','删除角色',NULL,'system:role:delete',NULL,'1',NULL,NULL,NULL,NULL,NULL),('200c46a1fa00486391eebba26ef29fbc',NULL,'系统监控',NULL,NULL,'zmdi zmdi-shield-security','0',2,NULL,NULL,NULL,NULL),('2191124c0ed14d97873ec3c5a42e0fec','2bcc85101aa94fc0a80aa8afcb1c6764','新增部门',NULL,'system:dept:add',NULL,'1',NULL,NULL,NULL,NULL,NULL),('274dc2b2a45d419fac36c2f99951b8bc','ea1a2bddfbf8493ca3558454dd8b3173','删除菜单',NULL,'system:menu:delete',NULL,'1',NULL,NULL,NULL,NULL,NULL),('2bcc85101aa94fc0a80aa8afcb1c6764','42c9f16445c643b5b1421d9b03d1d2be','部门管理','system/dept','system:dept:list','','0',4,NULL,NULL,NULL,NULL),('2d3f1593f5964fde84e1a00346c13cc3','42c9f16445c643b5b1421d9b03d1d2be','角色管理','system/role','system:role:list','','0',2,NULL,NULL,NULL,NULL),('3cfd35e7c03543399057b2ee13d63b23','2bcc85101aa94fc0a80aa8afcb1c6764','删除部门',NULL,'system:dept:delete',NULL,'1',NULL,NULL,NULL,NULL,NULL),('4132363d405943b7b81e57f62b2c9666','bfab6f8d126240acaa8531d0c58202dd','暂停任务',NULL,'system:job:pause',NULL,'1',NULL,NULL,NULL,NULL,NULL),('42c9f16445c643b5b1421d9b03d1d2be',NULL,'系统管理',NULL,NULL,'zmdi zmdi-settings','0',1,NULL,NULL,NULL,NULL),('4750feaa8e0941079543d2c1931eb854','200c46a1fa00486391eebba26ef29fbc','在线用户','system/session','system:session:list','','0',1,NULL,NULL,NULL,NULL),('4cfa4a29183d4cda9a11954f272d31fb','42c9f16445c643b5b1421d9b03d1d2be','字典管理','system/dict','system:dict:list','','0',NULL,NULL,NULL,NULL,NULL),('4d0e875891954b689c3885b24f128d05','cacd61b3b9c24914bc65d86a089067dd','删除日志',NULL,'system:log:delete',NULL,'1',NULL,NULL,NULL,NULL,NULL),('5409adb0b9fb4771a73945f373edcb6b','bfab6f8d126240acaa8531d0c58202dd','立即执行任务',NULL,'system:job:run',NULL,'1',NULL,NULL,NULL,NULL,NULL),('543916292f37453f9b79c58fa4f4892a','2d3f1593f5964fde84e1a00346c13cc3','修改角色',NULL,'system:role:update',NULL,'1',NULL,NULL,NULL,NULL,NULL),('64f00ee996234a2481d0cd6bd65805bf','4cfa4a29183d4cda9a11954f272d31fb','新增字典',NULL,'system:dict:add',NULL,'1',NULL,NULL,NULL,NULL,NULL),('6d6bb4902fbb4dd1b237785f7bb1c587','09a4ce59641343f394352e1b88c2364f','删除用户',NULL,'system:user:delete',NULL,'1',NULL,NULL,NULL,NULL,NULL),('747f1da614c54fddbc374ccc3ce052c0','4cfa4a29183d4cda9a11954f272d31fb','删除字典',NULL,'system:dict:delete',NULL,'1',NULL,NULL,NULL,NULL,NULL),('7636b701fe9a442d90ad8cd2377ce6a5','09a4ce59641343f394352e1b88c2364f','新增用户',NULL,'system:user:add',NULL,'1',NULL,NULL,NULL,NULL,NULL),('7862e796d2514fb2b6b0f289972bc353','fd2efdc1eba645f48974dba53367690a','调度日志','system/jobLog','jobsystem:log:list','','0',NULL,NULL,NULL,NULL,NULL),('7ac0468a41c840a7a6e865e84122d9a3','ea1a2bddfbf8493ca3558454dd8b3173','新增菜单',NULL,'system:menu:add',NULL,'1',NULL,NULL,NULL,NULL,NULL),('89c76ccf82dc49ab994682dbd24502ed','09a4ce59641343f394352e1b88c2364f','修改用户',NULL,'system:user:update',NULL,'1',NULL,NULL,NULL,NULL,NULL),('8e8bbbc4731f40139bb7617d69c363c7','bfab6f8d126240acaa8531d0c58202dd','修改任务',NULL,'system:job:update',NULL,'1',NULL,NULL,NULL,NULL,NULL),('bafabb6036124b6d8c73df727d839b75','bfab6f8d126240acaa8531d0c58202dd','恢复任务',NULL,'system:job:resume',NULL,'1',NULL,NULL,NULL,NULL,NULL),('bfab6f8d126240acaa8531d0c58202dd','fd2efdc1eba645f48974dba53367690a','定时任务','system/job','system:job:list','','0',NULL,NULL,NULL,NULL,NULL),('c39c9137518d462f97412f327ed7e37d','200c46a1fa00486391eebba26ef29fbc','Redis终端','redis/terminal','system:redis:terminal','','0',NULL,NULL,NULL,NULL,NULL),('c9d5a461e12547a09240faa864657fc9','2d3f1593f5964fde84e1a00346c13cc3','新增角色',NULL,'system:role:add',NULL,'1',NULL,NULL,NULL,NULL,NULL),('cacd61b3b9c24914bc65d86a089067dd','200c46a1fa00486391eebba26ef29fbc','系统日志','system/log','system:log:list','','0',3,NULL,NULL,NULL,NULL),('ea1a2bddfbf8493ca3558454dd8b3173','42c9f16445c643b5b1421d9b03d1d2be','菜单管理','system/menu','system:menu:list','','0',3,NULL,NULL,NULL,NULL),('ebb40557a81444cba858c9bc6ff0b6ab','bfab6f8d126240acaa8531d0c58202dd','删除任务',NULL,'system:job:delete',NULL,'1',NULL,NULL,NULL,NULL,NULL),('f49616523ffe45ceae6c0e016bf8487e','4750feaa8e0941079543d2c1931eb854','踢出用户',NULL,'system:user:kickout',NULL,'1',NULL,NULL,NULL,NULL,NULL),('f83505a2a57845dca1d26bf099263021','200c46a1fa00486391eebba26ef29fbc','Redis监控','system/redis/info','system:redis:list','','0',NULL,NULL,NULL,NULL,NULL),('fd2efdc1eba645f48974dba53367690a',NULL,'任务调度',NULL,NULL,'zmdi zmdi-alarm','0',NULL,NULL,NULL,NULL,NULL),('fe808b9153884e7b9a68a15c0ba37599','2bcc85101aa94fc0a80aa8afcb1c6764','修改部门',NULL,'system:dept:update',NULL,'1',NULL,NULL,NULL,NULL,NULL),('ff99eefd1e4242eabec097d3f6e97880','ea1a2bddfbf8493ca3558454dd8b3173','修改菜单',NULL,'system:menu:update',NULL,'1',NULL,NULL,NULL,NULL,NULL);
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
INSERT INTO `sys_role` VALUES ('47a251e72490461d9ce0afc9db1c0464','管理员','管理员',NULL,NULL,NULL,NULL);
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
INSERT INTO `sys_role_menu` VALUES ('0eafac7b62d74d6487b052fe75d4045c','47a251e72490461d9ce0afc9db1c0464','274dc2b2a45d419fac36c2f99951b8bc',NULL,NULL,NULL,NULL),('0fef4cd4381142eb8de8937267946287','47a251e72490461d9ce0afc9db1c0464','89c76ccf82dc49ab994682dbd24502ed',NULL,NULL,NULL,NULL),('10261c14362d44ab8042b8c0985fde0f','47a251e72490461d9ce0afc9db1c0464','8e8bbbc4731f40139bb7617d69c363c7',NULL,NULL,NULL,NULL),('148be25295444e6a9d9d8bc00f19a323','47a251e72490461d9ce0afc9db1c0464','4132363d405943b7b81e57f62b2c9666',NULL,NULL,NULL,NULL),('21205277fdff4efe86fa8503f495ea47','47a251e72490461d9ce0afc9db1c0464','747f1da614c54fddbc374ccc3ce052c0',NULL,NULL,NULL,NULL),('236ef473b27d49239ac00d0baa104fad','47a251e72490461d9ce0afc9db1c0464','fd2efdc1eba645f48974dba53367690a',NULL,NULL,NULL,NULL),('33081833bb09467ab5e8e934787ea572','47a251e72490461d9ce0afc9db1c0464','2191124c0ed14d97873ec3c5a42e0fec',NULL,NULL,NULL,NULL),('422a67dbd2b84d168a926d47cd3a9b55','47a251e72490461d9ce0afc9db1c0464','17a6c45a548d44bdb32d52ae25c641be',NULL,NULL,NULL,NULL),('459cddbca0f24923a26974c728c32440','47a251e72490461d9ce0afc9db1c0464','42c9f16445c643b5b1421d9b03d1d2be',NULL,NULL,NULL,NULL),('4b3a6b8d2715452292e86bd49e6c26ff','47a251e72490461d9ce0afc9db1c0464','f83505a2a57845dca1d26bf099263021',NULL,NULL,NULL,NULL),('4db09afab09e437a83b60c7c3a9596c0','47a251e72490461d9ce0afc9db1c0464','09a4ce59641343f394352e1b88c2364f',NULL,NULL,NULL,NULL),('4e318dceddb244c6a37858998eb19cfe','47a251e72490461d9ce0afc9db1c0464','5409adb0b9fb4771a73945f373edcb6b',NULL,NULL,NULL,NULL),('553c22fe216c43869882397c02b7b022','47a251e72490461d9ce0afc9db1c0464','4cfa4a29183d4cda9a11954f272d31fb',NULL,NULL,NULL,NULL),('5eeaea94d6464705addbe8e8af263b76','47a251e72490461d9ce0afc9db1c0464','0986485003eb4ac7bad0460fc6ae7d94',NULL,NULL,NULL,NULL),('62b42071f9dd45fb9571aaa5acd3dd20','47a251e72490461d9ce0afc9db1c0464','543916292f37453f9b79c58fa4f4892a',NULL,NULL,NULL,NULL),('6acd25831d314c5da35192db7415f3b6','47a251e72490461d9ce0afc9db1c0464','bfab6f8d126240acaa8531d0c58202dd',NULL,NULL,NULL,NULL),('7162ff8d9acb414ea54991517f464b94','47a251e72490461d9ce0afc9db1c0464','6d6bb4902fbb4dd1b237785f7bb1c587',NULL,NULL,NULL,NULL),('71a41673ed05439bad9c98584567fe79','47a251e72490461d9ce0afc9db1c0464','7636b701fe9a442d90ad8cd2377ce6a5',NULL,NULL,NULL,NULL),('833c692344e94174a1d2c282f3bc18e3','47a251e72490461d9ce0afc9db1c0464','200c46a1fa00486391eebba26ef29fbc',NULL,NULL,NULL,NULL),('8a94fa1de5c54e55a04d969c69f8e496','47a251e72490461d9ce0afc9db1c0464','ebb40557a81444cba858c9bc6ff0b6ab',NULL,NULL,NULL,NULL),('8cc98e8394044fb8a8acf3c607f7b0d7','47a251e72490461d9ce0afc9db1c0464','cacd61b3b9c24914bc65d86a089067dd',NULL,NULL,NULL,NULL),('9d23490116f34e7e98989e3ad58fa6fe','47a251e72490461d9ce0afc9db1c0464','ff99eefd1e4242eabec097d3f6e97880',NULL,NULL,NULL,NULL),('abb5560e3dbb44f8a45bace98680bf0b','47a251e72490461d9ce0afc9db1c0464','fe808b9153884e7b9a68a15c0ba37599',NULL,NULL,NULL,NULL),('b5e752f1add94c42bdbe9c4e1be6f7fc','47a251e72490461d9ce0afc9db1c0464','c9d5a461e12547a09240faa864657fc9',NULL,NULL,NULL,NULL),('c0cf94d1d21c403eac5fdf1607badf96','47a251e72490461d9ce0afc9db1c0464','2d3f1593f5964fde84e1a00346c13cc3',NULL,NULL,NULL,NULL),('c274f315ae764f48baecc4786a24fecd','47a251e72490461d9ce0afc9db1c0464','16afaccebb2f49188890cc0b90187b93',NULL,NULL,NULL,NULL),('d166e463b91f4772a5595bf60637204b','47a251e72490461d9ce0afc9db1c0464','7ac0468a41c840a7a6e865e84122d9a3',NULL,NULL,NULL,NULL),('d5d55fc09baf44fba6fc3ac96397735d','47a251e72490461d9ce0afc9db1c0464','4d0e875891954b689c3885b24f128d05',NULL,NULL,NULL,NULL),('df46e02fcb564813a38ebcccf23edeea','47a251e72490461d9ce0afc9db1c0464','64f00ee996234a2481d0cd6bd65805bf',NULL,NULL,NULL,NULL),('e20c6e554eb74704bc4c55889dbe1341','47a251e72490461d9ce0afc9db1c0464','3cfd35e7c03543399057b2ee13d63b23',NULL,NULL,NULL,NULL),('e2f99a5072e94069b26b6f05eddb53a7','47a251e72490461d9ce0afc9db1c0464','f49616523ffe45ceae6c0e016bf8487e',NULL,NULL,NULL,NULL),('e876cc5996bd440fa7427f2fcbdbaa86','47a251e72490461d9ce0afc9db1c0464','c39c9137518d462f97412f327ed7e37d',NULL,NULL,NULL,NULL),('ea37bedea6a74f6fab080006400835ab','47a251e72490461d9ce0afc9db1c0464','4750feaa8e0941079543d2c1931eb854',NULL,NULL,NULL,NULL),('eb3255ea743b4951925a4582281600f8','47a251e72490461d9ce0afc9db1c0464','2bcc85101aa94fc0a80aa8afcb1c6764',NULL,NULL,NULL,NULL),('ed3ea9b788564c13851f816443ed7748','47a251e72490461d9ce0afc9db1c0464','ea1a2bddfbf8493ca3558454dd8b3173',NULL,NULL,NULL,NULL),('f5db635890d14e0c8c079d93cb2fac27','47a251e72490461d9ce0afc9db1c0464','7862e796d2514fb2b6b0f289972bc353',NULL,NULL,NULL,NULL),('fcaa8cdc823f4271adfae9d0939c8cdf','47a251e72490461d9ce0afc9db1c0464','bafabb6036124b6d8c73df727d839b75',NULL,NULL,NULL,NULL);
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
  `title` varchar(200) DEFAULT NULL COMMENT 'title',
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
INSERT INTO `sys_user` VALUES ('9c45baf5af5449eb9c4c5dddde7a149a','MrBird','57d6c5a9e682052666fdf08b0d4fa721','6f8337342dc24e88b9fbc2aec2c0b440',NULL,'mrbird@hotmail.com','13455533222','0','default.jpg','green',1,'我是作者。','2019-01-07 15:53:31',NULL,NULL,NULL,NULL,NULL);
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
  `op_time` int(11) DEFAULT NULL COMMENT '消耗时间',
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
INSERT INTO `sys_user_role` VALUES ('573cf35e3eef41258a7bd70f78374092','9c45baf5af5449eb9c4c5dddde7a149a','47a251e72490461d9ce0afc9db1c0464',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_job`
--

DROP TABLE IF EXISTS `t_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_job` (
  `id` varchar(32) NOT NULL COMMENT '任务id',
  `bean_name` varchar(100) NOT NULL COMMENT 'spring bean名称',
  `method_name` varchar(100) NOT NULL COMMENT '方法名',
  `method_params` varchar(200) DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) NOT NULL COMMENT 'cron表达式',
  `status` tinyint(4) NOT NULL COMMENT '任务状态  0：正常  1：暂停',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_job`
--

LOCK TABLES `t_job` WRITE;
/*!40000 ALTER TABLE `t_job` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_job_log`
--

DROP TABLE IF EXISTS `t_job_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_job_log` (
  `id` varchar(32) NOT NULL COMMENT '任务日志id',
  `job_id` varchar(32) NOT NULL COMMENT '任务id',
  `bean_name` varchar(100) NOT NULL COMMENT 'spring bean名称',
  `method_name` varchar(100) NOT NULL COMMENT '方法名',
  `method_params` varchar(200) DEFAULT NULL COMMENT '参数',
  `ret_status` tinyint(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `ret_remark` text COMMENT '失败信息',
  `period_time` decimal(11,0) DEFAULT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_job_log`
--

LOCK TABLES `t_job_log` WRITE;
/*!40000 ALTER TABLE `t_job_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_job_log` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-07 19:01:32
