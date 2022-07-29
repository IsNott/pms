/*
 Navicat Premium Data Transfer

 Source Server         : 173服务器
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : gd_project

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 14/04/2022 21:04:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for attend
-- ----------------------------
DROP TABLE IF EXISTS `attend`;
CREATE TABLE `attend`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `emp_no` bigint NULL DEFAULT NULL COMMENT '员工编号',
  `work_atten` datetime(6) NULL DEFAULT NULL COMMENT '上班打卡',
  `leave_atten` datetime(6) NULL DEFAULT NULL COMMENT '下班打卡',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of attend
-- ----------------------------
INSERT INTO `attend` VALUES (1, 6, '2022-03-01 08:00:19.000000', '2022-03-01 17:30:32.000000');
INSERT INTO `attend` VALUES (2, 6, '2022-03-02 08:00:19.000000', '2022-03-02 17:30:32.000000');
INSERT INTO `attend` VALUES (3, 6, '2022-03-03 08:10:19.000000', '2022-03-03 17:30:32.000000');
INSERT INTO `attend` VALUES (4, 6, '2022-03-04 08:03:19.000000', '2022-03-04 17:30:32.000000');
INSERT INTO `attend` VALUES (5, 6, '2022-03-05 08:04:19.000000', '2022-03-05 17:30:32.000000');
INSERT INTO `attend` VALUES (6, 6, '2022-03-06 08:06:19.000000', '2022-03-06 17:30:32.000000');
INSERT INTO `attend` VALUES (7, 7, '2022-03-01 08:00:19.000000', '2022-03-01 17:30:32.000000');
INSERT INTO `attend` VALUES (8, 7, '2022-03-02 08:00:19.000000', '2022-03-02 17:30:32.000000');
INSERT INTO `attend` VALUES (9, 7, '2022-03-03 08:10:19.000000', '2022-03-03 17:30:32.000000');
INSERT INTO `attend` VALUES (10, 7, '2022-03-04 08:03:19.000000', '2022-03-04 17:30:32.000000');
INSERT INTO `attend` VALUES (11, 8, '2022-03-01 08:00:19.000000', '2022-03-01 17:30:32.000000');
INSERT INTO `attend` VALUES (12, 8, '2022-03-02 08:00:19.000000', '2022-03-02 17:30:32.000000');
INSERT INTO `attend` VALUES (13, 8, '2022-03-03 08:10:19.000000', '2022-03-03 17:30:32.000000');
INSERT INTO `attend` VALUES (14, 8, '2022-03-04 08:03:19.000000', '2022-03-04 17:30:32.000000');
INSERT INTO `attend` VALUES (15, 21, '2022-03-01 08:00:19.000000', '2022-03-01 17:30:32.000000');
INSERT INTO `attend` VALUES (16, 21, '2022-03-02 08:00:19.000000', '2022-03-02 17:30:32.000000');
INSERT INTO `attend` VALUES (17, 21, '2022-03-03 08:10:19.000000', '2022-03-03 17:30:32.000000');
INSERT INTO `attend` VALUES (18, 21, '2022-03-04 08:03:19.000000', '2022-03-04 17:30:32.000000');
INSERT INTO `attend` VALUES (19, 22, '2022-03-01 08:00:19.000000', '2022-03-01 17:30:32.000000');
INSERT INTO `attend` VALUES (20, 22, '2022-03-02 08:00:19.000000', '2022-03-02 17:30:32.000000');
INSERT INTO `attend` VALUES (21, 22, '2022-03-03 08:10:19.000000', '2022-03-03 17:30:32.000000');
INSERT INTO `attend` VALUES (22, 22, '2022-03-04 08:03:19.000000', '2022-03-04 17:30:32.000000');

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `dept_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '部门名称',
  `dept_local` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '部门位置',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10010 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '部门表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES (10001, '市场部', '广州省广州市天河区广州大道');
INSERT INTO `department` VALUES (10002, '产品部', '广州省广州市天河区广州大道');
INSERT INTO `department` VALUES (10003, '技术部', '广州省广州市天河区广州大道');
INSERT INTO `department` VALUES (10004, '行政部', '广州省广州市天河区广州大道');
INSERT INTO `department` VALUES (10005, '财务部', '广州省广州市天河区广州大道');
INSERT INTO `department` VALUES (10009, '产品二部', '广州市黄埔大道');

-- ----------------------------
-- Table structure for emp
-- ----------------------------
DROP TABLE IF EXISTS `emp`;
CREATE TABLE `emp`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `deptno` bigint NULL DEFAULT NULL COMMENT '部门id',
  `manager_no` bigint NULL DEFAULT NULL COMMENT '上级编号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '员工姓名',
  `tel` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '员工电话',
  `statu` int NULL DEFAULT 0 COMMENT '出勤状态 =1时请假，默认为0正常出勤',
  `entry_time` datetime(6) NULL DEFAULT NULL COMMENT '入职时间',
  `deflag` int NULL DEFAULT 0 COMMENT '在职状态 =1时离职，默认为0在职',
  `del_time` datetime(6) NULL DEFAULT NULL COMMENT '离职时间，默认为null',
  `position` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '职位',
  `gender` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '性别',
  `birthday` datetime(6) NULL DEFAULT NULL COMMENT '出生日期',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `dep`(`deptno`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '员工表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of emp
-- ----------------------------
INSERT INTO `emp` VALUES (5, 10009, 5, '那喃喃11', '13213241234', 0, '2022-01-23 00:00:00.000000', 0, '2022-04-09 12:59:47.678000', 'tv项目设计师1', '1', '2000-01-04 00:00:00.000000');
INSERT INTO `emp` VALUES (6, 10003, 8, '走走', '13420151324', 0, '2022-02-13 13:38:47.000000', 0, NULL, '超级管理员', '1', '2000-10-28 23:05:47.000000');
INSERT INTO `emp` VALUES (7, 10001, 8, '测试', '13213213211', 0, '2022-04-03 20:21:15.000000', 0, '2022-04-13 21:38:05.616000', '普通员工', '1', '2022-04-06 20:21:27.000000');
INSERT INTO `emp` VALUES (8, 10004, NULL, 'King', '13241531234', 0, '2022-01-04 10:38:21.000000', 0, NULL, '总经理', '1', '2021-12-29 10:38:41.000000');
INSERT INTO `emp` VALUES (9, 10002, 8, '测试经理', '13213213231', 0, '2022-04-03 20:21:15.000000', 0, NULL, '产品经理', '1', '2022-04-11 20:21:27.000000');
INSERT INTO `emp` VALUES (10, 10005, 8, '测试经理', '19913213231', 0, '2022-04-03 20:21:15.000000', 0, NULL, '财务顾问', '1', '2022-04-10 15:21:27.000000');
INSERT INTO `emp` VALUES (14, 10001, 7, '测试经理', '19924678214', 0, '2022-04-02 00:00:00.000000', 0, NULL, '测试', '1', '2022-04-01 00:00:00.000000');
INSERT INTO `emp` VALUES (18, 10001, 7, '测试经理', '19947381234', 0, '2022-04-21 00:00:00.000000', 0, NULL, '走走走', '1', '2010-12-27 00:00:00.000000');
INSERT INTO `emp` VALUES (19, 10001, 7, 'Zzzwlong', '19924637821', 0, '2022-04-19 00:00:00.000000', 0, NULL, '组织', '1', '2022-04-05 00:00:00.000000');
INSERT INTO `emp` VALUES (20, 10001, 7, '测试', '19924637821', 0, '2022-04-01 00:00:00.000000', 0, NULL, '哟哟哟', '1', '2022-03-29 00:00:00.000000');
INSERT INTO `emp` VALUES (21, 10001, 7, '测试', '19924637821', 0, '2022-04-01 00:00:00.000000', 0, NULL, '哟哟哟', '1', '2022-03-29 00:00:00.000000');
INSERT INTO `emp` VALUES (22, 10001, 7, '测试经理', '19924738123', 0, '2022-04-20 00:00:00.000000', 0, NULL, '测试', '1', '2022-03-31 00:00:00.000000');

-- ----------------------------
-- Table structure for emp_detail
-- ----------------------------
DROP TABLE IF EXISTS `emp_detail`;
CREATE TABLE `emp_detail`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `emp_no` bigint NOT NULL COMMENT '员工编号',
  `nation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '国籍',
  `ethnic` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '民族',
  `political` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '政治面貌',
  `hometown` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '籍贯',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系地址',
  `marital` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '婚姻状况 0未婚 1已婚',
  `contract` int NULL DEFAULT 0 COMMENT '合同类型 0劳务合同 1劳动合同',
  `contract_years` int NULL DEFAULT NULL COMMENT '合同年限',
  `school` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '毕业院校',
  `education` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学历',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '职称',
  `id_card` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '身份证号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '员工详细信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of emp_detail
-- ----------------------------
INSERT INTO `emp_detail` VALUES (1, 5, '中国', '汉族', '共青团员', '广东韶关', '广东省广州市海珠区广州大道', '0', 1, 3, '广州学院', '本科', '系统集成项目工程师', '440204200010101234');
INSERT INTO `emp_detail` VALUES (2, 6, '中国', '汉族', '共青团员', '广东韶关', '广东省广州市海珠区广州大道', '1', 0, 5, '广州学院', '本科', '无', '440204200010101235');
INSERT INTO `emp_detail` VALUES (3, 7, '中国', '汉族', '共青团员', '广东广州', '广东省广州市海珠区广州大道', '1', 0, 5, '广州学院', '本科', 'Java开发中级工程师', '440204200010107863');
INSERT INTO `emp_detail` VALUES (4, 8, '中国', '汉族', '共产党员', '广东广州', '广东省广州市海珠区广州大道', '1', 0, 5, '广州学院', '本科', '系统设计工程师', '441204200010107863');
INSERT INTO `emp_detail` VALUES (5, 9, '中国', '汉族', '共产党员', '广东广州', '广东省广州市海珠区广州大道', '1', 0, 5, '广州学院', '本科', '无', '447654200010102356');
INSERT INTO `emp_detail` VALUES (6, 10, '中国', '汉族', '共产党员', '广东广州', '广东省广州市海珠区广州大道', '1', 0, 5, '广州学院', '本科', '中级会计师', '449204200010108970');
INSERT INTO `emp_detail` VALUES (9, 21, '中国', '汉', '群众', '广东韶关', '广东韶关', '0', 0, 1, '广州学院', '本科', '无', '44024020001029781X');
INSERT INTO `emp_detail` VALUES (10, 21, '中国', '汉', '群众', '广东韶关', '广东韶关', '0', 0, 1, '广州学院', '本科', '无', '44024020001029781X');
INSERT INTO `emp_detail` VALUES (11, 21, '中国', '汉', '群众', '广东韶关', '广东韶关', '0', 0, 1, '广州学院', '本科', '无', '44024020001029781X');
INSERT INTO `emp_detail` VALUES (12, 22, '中国', '汉', '群众', '广东', '广东韶关', '1', 0, 1, '广州学院', '本科', '无', '44020420001028783X');

-- ----------------------------
-- Table structure for emp_sal
-- ----------------------------
DROP TABLE IF EXISTS `emp_sal`;
CREATE TABLE `emp_sal`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `salary` double(20, 2) NULL DEFAULT NULL COMMENT '基础工资',
  `empno` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '员工编号，对应emp中的id',
  `subsidy` double(20, 2) NULL DEFAULT NULL COMMENT '员工津贴',
  `ot_sal` double(20, 2) NULL DEFAULT 20.00 COMMENT '加班费时薪',
  `over_time` double(10, 2) NULL DEFAULT NULL COMMENT '加班时长',
  `total` double(20, 2) NULL DEFAULT NULL COMMENT '税前收入',
  `total_aftax` double(20, 2) NULL DEFAULT NULL COMMENT '税后收入',
  `rate` double(20, 5) NULL DEFAULT NULL COMMENT '税率',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '员工薪资表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of emp_sal
-- ----------------------------
INSERT INTO `emp_sal` VALUES (1, 5000.00, '6', 2000.00, 20.00, 0.00, 12000.00, 11400.00, 0.05000);
INSERT INTO `emp_sal` VALUES (22, 2222.00, '5', 111.00, 20.00, NULL, NULL, NULL, NULL);
INSERT INTO `emp_sal` VALUES (23, 2222.00, '11', 111.00, 20.00, NULL, NULL, NULL, NULL);
INSERT INTO `emp_sal` VALUES (24, 5000.00, '7', 111.00, 20.00, NULL, NULL, NULL, NULL);
INSERT INTO `emp_sal` VALUES (25, 8888.00, '8', 111.00, 20.00, NULL, NULL, NULL, NULL);
INSERT INTO `emp_sal` VALUES (26, 3000.00, '9', 111.00, 20.00, NULL, NULL, NULL, NULL);
INSERT INTO `emp_sal` VALUES (27, 3000.00, '10', 111.00, 20.00, 0.00, 6111.00, 5927.67, 0.03000);

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件名',
  `upload_No` bigint NULL DEFAULT NULL COMMENT '上传人id',
  `file_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件路径',
  `upload_time` datetime NULL DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file
-- ----------------------------
INSERT INTO `file` VALUES (2, 'new 119:17:00.304.txt', 6, '119:17:00.304.txt', '2022-04-10 19:17:01');
INSERT INTO `file` VALUES (4, '1259502.docx', 6, '1259502.docx', '2022-04-14 16:07:22');

-- ----------------------------
-- Table structure for leave_audit
-- ----------------------------
DROP TABLE IF EXISTS `leave_audit`;
CREATE TABLE `leave_audit`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '申请理由',
  `auditor` bigint NULL DEFAULT NULL COMMENT '审核人id，对应员工上级编号',
  `emp_no` bigint NULL DEFAULT NULL COMMENT '提交人员工id',
  `type` int NULL DEFAULT 0 COMMENT '申请类型 0为请假 1为离职 2为加班 默认为0',
  `status` int NULL DEFAULT 0 COMMENT '审核状态 1为通过 0为审核中 -1为不通过',
  `audit_duration` double(10, 2) NULL DEFAULT NULL COMMENT '申请时长',
  `audit_option` bigint NULL DEFAULT 0 COMMENT '时长选项 0为小时 1为天',
  `stattime` datetime NULL DEFAULT NULL COMMENT '开始时间',
  `endtime` datetime NULL DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '员工请假审核表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of leave_audit
-- ----------------------------
INSERT INTO `leave_audit` VALUES (1, '想放假，不想干了', 8, 6, 0, 0, 2.00, 1, '2022-04-12 23:18:02', '2022-04-13 23:18:07');
INSERT INTO `leave_audit` VALUES (2, '就请假', 8, 6, 0, 0, 2.00, 1, '2022-04-14 23:19:06', '2022-04-16 23:19:09');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `senderId` bigint NULL DEFAULT NULL COMMENT '发送人员工id',
  `text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '发送内容',
  `sendtime` datetime NULL DEFAULT NULL COMMENT '发送时间',
  `receiverId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '接收员工id，多个以逗号分隔',
  `statu` int NULL DEFAULT NULL COMMENT '状态 0未读 1已读',
  `type` int NULL DEFAULT 0 COMMENT '消息类型 0消息 1公告',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (1, 9, 'asfasf', '2022-04-14 16:05:57', '6', 0, 0);
INSERT INTO `message` VALUES (2, 9, 'asfasf312', '2022-04-14 16:06:03', '6', 0, 0);
INSERT INTO `message` VALUES (3, NULL, 'asdsad11', '2022-04-14 16:06:08', '5', 0, 1);
INSERT INTO `message` VALUES (4, NULL, 'asdsad11', '2022-04-14 16:06:09', '6', 0, 1);
INSERT INTO `message` VALUES (5, NULL, 'asdsad11', '2022-04-14 16:06:09', '8', 0, 1);
INSERT INTO `message` VALUES (6, NULL, 'asdsad11', '2022-04-14 16:06:09', '9', 0, 1);
INSERT INTO `message` VALUES (7, NULL, 'asdsad11', '2022-04-14 16:06:09', '10', 0, 1);

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'id',
  `pid` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '所属上级',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '名称',
  `type` tinyint NOT NULL DEFAULT 0 COMMENT '类型(1:菜单,2:按钮)',
  `permission_value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限值',
  `path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '访问路径',
  `component` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件路径',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图标',
  `delflag` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_pid`(`pid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '权限' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', '', '员工管理', 0, 'emp', '', 'Emp', NULL, 0, '2022-02-09 16:36:55', '2022-02-09 16:36:58');
INSERT INTO `permission` VALUES ('10001', '1', '员工列表', 0, 'emp.list', 'emp/list', 'EmpBasic', NULL, 0, '2022-04-01 11:18:10', '2022-04-02 11:18:13');
INSERT INTO `permission` VALUES ('10002', '1', '新增员工', 0, 'emp.add', 'emp/add', 'EmpAdd', NULL, 0, '2022-03-01 11:18:17', '2022-03-03 11:18:21');
INSERT INTO `permission` VALUES ('2', '', '用户管理', 1, 'user', '', 'User', NULL, 0, '2022-02-11 09:11:38', '2022-04-07 13:53:12');
INSERT INTO `permission` VALUES ('20001', '2', '用户列表', 1, 'user.list', 'user/list', 'UserList', NULL, 0, '2022-02-11 17:15:45', '2022-02-11 21:57:16');
INSERT INTO `permission` VALUES ('20002', '2', '新增用户', 1, 'user.add', 'user/add', 'UserAdd', NULL, 1, '2022-03-01 17:16:22', '2022-04-07 17:16:22');
INSERT INTO `permission` VALUES ('3', '', '薪资管理', 0, 'sal', '', 'Sal', NULL, 0, '2022-03-01 11:17:57', '2022-03-02 11:18:03');
INSERT INTO `permission` VALUES ('30001', '3', '查看薪资', 0, 'sal.list', 'sal/emp', 'SalEmp', NULL, 0, '2022-03-04 11:18:27', '2022-04-01 11:18:32');
INSERT INTO `permission` VALUES ('4', '', '系统管理', 0, 'sys', ' ', 'Sys', NULL, 0, '2022-02-28 15:50:01', '2022-03-30 15:50:06');
INSERT INTO `permission` VALUES ('40001', '4', '管理员列表', 0, 'sys.admin', 'sys/admin', 'SysAdmin', NULL, 0, '2022-03-29 15:50:10', '2022-04-14 15:50:14');
INSERT INTO `permission` VALUES ('40002', '4', '角色授权', 0, 'sys.auth', 'sys/auth', 'SysAuth', NULL, 0, '2022-03-01 15:50:17', '2022-04-20 15:50:21');
INSERT INTO `permission` VALUES ('40003', '4', '菜单管理', 0, 'sys.menu', 'sys/menu', 'SysMenu', NULL, 0, '2022-03-31 15:50:25', '2022-04-14 15:50:29');
INSERT INTO `permission` VALUES ('5', '', '部门管理', 0, 'dept', '', 'Dept', NULL, 0, '2022-03-28 15:50:31', '2022-04-14 15:50:34');
INSERT INTO `permission` VALUES ('50001', '5', '部门列表', 0, 'dept.list', 'dept/list', 'DeptList', NULL, 0, '2022-03-28 15:50:38', '2022-04-14 15:50:41');
INSERT INTO `permission` VALUES ('50002', '5', '新建部门', 0, 'dept.add', 'dept/add', 'DeptAdd', NULL, 0, '2022-03-29 15:50:43', '2022-04-14 15:50:46');
INSERT INTO `permission` VALUES ('6', '', '文件管理', 0, 'file', '', 'File', NULL, 0, '2022-03-28 15:50:48', '2022-04-14 15:50:51');
INSERT INTO `permission` VALUES ('60001', '6', '文件列表', 0, 'file.list', 'file/list', 'FileBasic', NULL, 0, '2022-04-11 15:50:54', '2022-04-14 15:50:58');
INSERT INTO `permission` VALUES ('7', '', '信息管理', 0, 'msg', '', 'Msg', NULL, 0, '2022-03-28 15:51:00', '2022-04-14 15:51:03');
INSERT INTO `permission` VALUES ('70001', '7', '信息列表', 0, 'msg.list', 'msg/list', 'MsgList', NULL, 0, '2022-03-28 15:51:05', '2022-03-29 15:51:07');
INSERT INTO `permission` VALUES ('70002', '7', '发布公告', 0, 'msg.annou', 'msg/annou', 'MsgAnnou', NULL, 0, '2022-03-28 15:51:11', '2022-03-31 15:51:14');
INSERT INTO `permission` VALUES ('8', '', '考勤管理', 0, 'att', '', 'Att', NULL, 0, '2022-03-29 15:51:17', '2022-03-30 15:51:19');
INSERT INTO `permission` VALUES ('80001', '8', '考勤查看', 0, 'att.list', 'att/list', 'AttList', NULL, 0, '2022-03-28 15:51:23', '2022-03-29 15:51:26');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` bigint UNSIGNED NOT NULL COMMENT '角色id，对应User_role中id',
  `permission_id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '菜单id，对应Permission表中id',
  `delflag` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_id`(`role_id`) USING BTREE,
  INDEX `idx_permission_id`(`permission_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1422353473 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色权限' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (1, 0, '2', 1);
INSERT INTO `role_permission` VALUES (2, 1, '2', 0);
INSERT INTO `role_permission` VALUES (3, 2, '2', 0);
INSERT INTO `role_permission` VALUES (4, 1, '4', 0);
INSERT INTO `role_permission` VALUES (111, 1, '3', 0);
INSERT INTO `role_permission` VALUES (1422353467, 1, '1', 0);
INSERT INTO `role_permission` VALUES (1422353468, 1, '5', 0);
INSERT INTO `role_permission` VALUES (1422353469, 1, '6', 0);
INSERT INTO `role_permission` VALUES (1422353470, 1, '7', 0);
INSERT INTO `role_permission` VALUES (1422353471, 0, '7', 1);
INSERT INTO `role_permission` VALUES (1422353472, 1, '8', 0);
INSERT INTO `role_permission` VALUES (1422353473, 0, '3', 0);
INSERT INTO `role_permission` VALUES (1422353474, 0, '8', 0);
INSERT INTO `role_permission` VALUES (1422353475, 2, '2', 0);
INSERT INTO `role_permission` VALUES (1422353476, 2, '7', 0);
INSERT INTO `role_permission` VALUES (1422353477, 2, '3', 0);
INSERT INTO `role_permission` VALUES (1422353478, 2, '5', 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '密码',
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户签名',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `delflag` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `emp_no` bigint NOT NULL COMMENT '用户绑定员工编号',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像存储地址',
  `role_id` bigint NULL DEFAULT 1 COMMENT '权限id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '888888', '充Q币1', '充Q币122', 'as@qq.com', 0, '2022-02-01 10:39:47', '2022-03-05 10:39:47', 6, 'pms-userinfo.oss-cn-guangzhou.aliyuncs.com/userfase/b7323b8f-656c-4d0a-b116-60130468f68b.jpg', 1);
INSERT INTO `user` VALUES (2, 'test', '123456', '测试1', 'test1', NULL, 1, '2022-02-05 14:25:41', '2022-02-05 14:25:46', 5, NULL, 0);
INSERT INTO `user` VALUES (3, 'emp007', '123456', '测试212', 'test2', 'test@163.com', 1, '2022-02-05 00:00:00', '2022-04-13 17:18:46', 7, 'null', 0);
INSERT INTO `user` VALUES (4, 'emp008', '123456', '测试3', 'test3', 'test2@qq.com', 1, '2022-02-05 14:25:41', '2022-02-05 14:25:46', 8, NULL, 1);
INSERT INTO `user` VALUES (5, 'emp009', '123456', '测试4', 'test4', 'test44@qq.com', 0, '2022-02-05 14:25:41', '2022-02-05 14:25:46', 9, NULL, 2);
INSERT INTO `user` VALUES (6, 'emp010', '123456', '测试5', 'test5', 'test567@163.com', 0, '2022-02-05 14:25:41', '2022-02-05 14:25:46', 10, NULL, 0);
INSERT INTO `user` VALUES (7, '01029781X', '123456', '测试', NULL, NULL, 0, '2022-04-01 00:00:00', '2022-04-14 17:11:14', 21, NULL, 1);
INSERT INTO `user` VALUES (8, '01028783X', '123456', '测试经理', NULL, NULL, 0, '2022-04-20 00:00:00', '2022-04-14 17:15:03', 22, NULL, 1);
INSERT INTO `user` VALUES (9, '110287831', '123456', '测试1', NULL, NULL, 0, '2022-04-01 00:00:00', '2022-04-14 17:11:14', 14, NULL, 1);
INSERT INTO `user` VALUES (10, '130287831', '123456', '测试2', NULL, NULL, 0, '2022-04-20 00:00:00', '2022-04-14 17:15:03', 18, NULL, 1);
INSERT INTO `user` VALUES (11, '13028783X', '123456', '测试23', NULL, NULL, 0, '2022-04-01 00:00:00', '2022-04-14 17:11:14', 19, NULL, 1);
INSERT INTO `user` VALUES (12, '12328783X', '123456', '测试24', NULL, NULL, 0, '2022-04-20 00:00:00', '2022-04-14 17:15:03', 20, NULL, 1);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` bigint NOT NULL COMMENT '角色id',
  `role_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '角色名称',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `delflag` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '账号角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (0, '普通用户', '员工', 0);
INSERT INTO `user_role` VALUES (1, '超级管理员', '网站管理者', 0);
INSERT INTO `user_role` VALUES (2, '普通管理员', '运维', 0);

SET FOREIGN_KEY_CHECKS = 1;
