/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : businessfiles

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2016-09-29 19:49:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_certificate
-- ----------------------------
DROP TABLE IF EXISTS `t_certificate`;
CREATE TABLE `t_certificate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `attachment` varchar(255) DEFAULT NULL,
  `teacherId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_certificate
-- ----------------------------
INSERT INTO `t_certificate` VALUES ('1', '九江学院本科学位证', '1.jpg', 'admin');

-- ----------------------------
-- Table structure for t_educationalresearch
-- ----------------------------
DROP TABLE IF EXISTS `t_educationalresearch`;
CREATE TABLE `t_educationalresearch` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topic` varchar(255) DEFAULT NULL,
  `topicNum` varchar(255) DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `approver` varchar(255) DEFAULT NULL,
  `level` varchar(255) DEFAULT NULL,
  `funds` double(255,2) DEFAULT NULL,
  `rank` int(11) DEFAULT NULL,
  `totalNum` int(11) DEFAULT NULL,
  `score` double(255,2) DEFAULT NULL,
  `attachment` varchar(255) DEFAULT NULL,
  `teacherId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_educationalresearch
-- ----------------------------
INSERT INTO `t_educationalresearch` VALUES ('1', '抛锚式教学”在《多媒体教育工程设计》教学改革中的研究与实践', 'EL20034', '2016-09-12', '2016-09-20', '江西省高等学校教学改革研究课题组', '重点', '10000.00', '1', '5', '20.00', '1.jpg', 'admin');
INSERT INTO `t_educationalresearch` VALUES ('2', '抛锚式教学”在《多媒体教育工程设计》教学改革中的研究与实践', 'EL20034', '2016-09-12', '2016-09-20', '江西省高等学校教学改革研究课题组', '重点', '10000.00', '1', '5', '20.00', '1.jpg', 'admin');

-- ----------------------------
-- Table structure for t_guidestudent
-- ----------------------------
DROP TABLE IF EXISTS `t_guidestudent`;
CREATE TABLE `t_guidestudent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prize` varchar(255) DEFAULT NULL,
  `level` varchar(255) DEFAULT NULL,
  `order` varchar(255) DEFAULT NULL,
  `time` date DEFAULT NULL,
  `score` double(10,2) DEFAULT NULL,
  `attachment` varchar(255) DEFAULT NULL,
  `teacherId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_guidestudent
-- ----------------------------
INSERT INTO `t_guidestudent` VALUES ('1', '指导学生参加江西大学生手机软件·手机应用编程赛', 'level', '二等奖', '2016-09-12', '100.00', '1.jpg', 'admin');
INSERT INTO `t_guidestudent` VALUES ('2', '指导学生参加江西大学生手机软件·手机应用编程赛', 'level', '一等奖', '2016-09-21', '100.00', '1.jpg', 'admin');

-- ----------------------------
-- Table structure for t_learningexperience
-- ----------------------------
DROP TABLE IF EXISTS `t_learningexperience`;
CREATE TABLE `t_learningexperience` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `level` varchar(255) DEFAULT NULL,
  `school` varchar(255) DEFAULT NULL,
  `major` varchar(255) DEFAULT NULL,
  `graduatDate` date DEFAULT NULL,
  `teacherId` varchar(255) DEFAULT NULL COMMENT '所属教师的工号id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_learningexperience
-- ----------------------------
INSERT INTO `t_learningexperience` VALUES ('1', '1', '华东师范大学', '教育技术学', '2016-09-20', '14181');
INSERT INTO `t_learningexperience` VALUES ('2', '1', '华东师范大学', '教育技术学', '2016-09-12', 'admin');

-- ----------------------------
-- Table structure for t_learninggroup
-- ----------------------------
DROP TABLE IF EXISTS `t_learninggroup`;
CREATE TABLE `t_learninggroup` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `level` varchar(255) DEFAULT NULL,
  `major` varchar(255) DEFAULT NULL,
  `job` varchar(255) DEFAULT NULL,
  `teacherId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_learninggroup
-- ----------------------------
INSERT INTO `t_learninggroup` VALUES ('1', '中国计算机学会', '国家一级学会', '计算机', '研究员', 'admin');
INSERT INTO `t_learninggroup` VALUES ('2', '中国计算机学会', '国家二级学会', '计算机', '研究员', 'admin');

-- ----------------------------
-- Table structure for t_learningmeeting
-- ----------------------------
DROP TABLE IF EXISTS `t_learningmeeting`;
CREATE TABLE `t_learningmeeting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `major` varchar(255) DEFAULT NULL,
  `organization` varchar(255) DEFAULT NULL,
  `time` date DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `teacherId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_learningmeeting
-- ----------------------------
INSERT INTO `t_learningmeeting` VALUES ('1', '第七届全国高等学校计算机实践教学论坛', '计算机', '计算机实践教学论坛会务组', '2016-09-19', '哈尔滨工业大学', 'admin');
INSERT INTO `t_learningmeeting` VALUES ('2', '第七届全国高等学校计算机实践教学论坛', '计算机', '计算机实践教学论坛会务组', '2016-09-19', '九江学院', 'admin');

-- ----------------------------
-- Table structure for t_monographtextbook
-- ----------------------------
DROP TABLE IF EXISTS `t_monographtextbook`;
CREATE TABLE `t_monographtextbook` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bookName` varchar(255) DEFAULT NULL,
  `publishDate` date DEFAULT NULL,
  `publication` varchar(255) DEFAULT NULL,
  `rank` int(255) DEFAULT NULL,
  `totalNum` int(255) DEFAULT NULL,
  `score` double(255,2) DEFAULT NULL,
  `attachment` varchar(255) DEFAULT NULL,
  `teacherId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_monographtextbook
-- ----------------------------
INSERT INTO `t_monographtextbook` VALUES ('1', '《数据结构——基于C语言的实现》', '2016-08-29', '高等教育出版社', '1', '30', '90.00', '1.jpg', 'admin');
INSERT INTO `t_monographtextbook` VALUES ('2', 'C语言', '2016-08-29', '高等教育出版社', '1', '60', '90.00', '2.jpg', 'admin');

-- ----------------------------
-- Table structure for t_otherworks
-- ----------------------------
DROP TABLE IF EXISTS `t_otherworks`;
CREATE TABLE `t_otherworks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `work` varchar(255) DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  `teacherId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_otherworks
-- ----------------------------
INSERT INTO `t_otherworks` VALUES ('1', '班主任', '信B1312班', '2年', 'admin');
INSERT INTO `t_otherworks` VALUES ('2', '教研室副主任', '应用教研室', '5年', 'admin');

-- ----------------------------
-- Table structure for t_paperresearch
-- ----------------------------
DROP TABLE IF EXISTS `t_paperresearch`;
CREATE TABLE `t_paperresearch` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `paperTitle` varchar(255) DEFAULT NULL,
  `publishDate` date DEFAULT NULL,
  `publication` varchar(255) DEFAULT NULL,
  `publishNum` varchar(255) DEFAULT NULL,
  `level` varchar(255) DEFAULT NULL,
  `rank` int(11) DEFAULT NULL,
  `totalNum` int(11) DEFAULT NULL,
  `score` double(255,0) DEFAULT NULL,
  `attachment` varchar(255) DEFAULT NULL,
  `teacherId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_paperresearch
-- ----------------------------
INSERT INTO `t_paperresearch` VALUES ('1', '采用视点校正的视点可分级编码', '2016-09-21', '计算机与现代化', 'ISSN333444', '一般公开期刊', '2', '6', '16', '1.jpg', 'admin');
INSERT INTO `t_paperresearch` VALUES ('2', '采用视点校正的视点可分级编码', '2016-09-21', '计算机与现代化', 'ISSN333444', '一般公开期刊', '2', '6', '16', '1.jpg', 'admin');
INSERT INTO `t_paperresearch` VALUES ('3', '采用视点校正的视点可分级编码', '2016-09-21', '计算机与现代化', 'ISSN333444', '一般公开期刊', '2', '6', '16', '1.jpg', 'admin');

-- ----------------------------
-- Table structure for t_scientificresearch
-- ----------------------------
DROP TABLE IF EXISTS `t_scientificresearch`;
CREATE TABLE `t_scientificresearch` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topic` varchar(255) DEFAULT NULL,
  `topicNum` varchar(255) DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `approver` varchar(255) DEFAULT NULL,
  `level` varchar(255) DEFAULT NULL,
  `funds` double(255,2) DEFAULT NULL,
  `rank` int(11) DEFAULT NULL,
  `totalNum` int(11) DEFAULT NULL,
  `score` double(255,2) DEFAULT NULL,
  `attachment` varchar(255) DEFAULT NULL,
  `teacherId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_scientificresearch
-- ----------------------------
INSERT INTO `t_scientificresearch` VALUES ('1', '《增强现实技术在文化遗产数字化保护中的关键技术研究——以庐山白鹿洞书院为例》', 'GL20034', '2016-09-12', '2016-09-20', '江西省青年科学基金', '重点', '10000.00', '1', '5', '20.00', '1.jpg', 'admin');
INSERT INTO `t_scientificresearch` VALUES ('2', '《增强现实技术在文化遗产数字化保护中的关键技术研究——以庐山白鹿洞书院为例》', 'GL20034', '2016-09-12', '2016-09-20', '江西省青年科学基金', '重点', '10000.00', '1', '5', '20.00', '1.jpg', 'admin');

-- ----------------------------
-- Table structure for t_teacher
-- ----------------------------
DROP TABLE IF EXISTS `t_teacher`;
CREATE TABLE `t_teacher` (
  `id` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `birthDate` varchar(10) DEFAULT NULL,
  `nation` varchar(255) DEFAULT NULL,
  `political` varchar(255) DEFAULT NULL,
  `cardId` varchar(18) DEFAULT NULL,
  `professTitle` varchar(255) DEFAULT NULL,
  `professDate` date DEFAULT NULL,
  `employTitle` varchar(255) DEFAULT NULL,
  `employDate` date DEFAULT NULL,
  `finalDegreeDate` date DEFAULT NULL,
  `finalDegree` varchar(255) DEFAULT NULL,
  `finalEducat` varchar(255) DEFAULT NULL,
  `grade` varchar(255) DEFAULT NULL,
  `dept` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_teacher
-- ----------------------------
INSERT INTO `t_teacher` VALUES ('14181', '123', '周小雄', '2016-09-05', '藏族', '党员', '362421199412204113', '副教授', '2016-09-12', '助教', '2016-09-12', '2016-09-11', '学士', '研究生', '信息科学与技术学院', '教务科');
INSERT INTO `t_teacher` VALUES ('admin', '123', '王文华', '2016-09-23', '维吾尔族', '群众', '362421199412204113', '讲师', '2016-09-12', '讲师', '2016-09-23', '2016-09-11', '学士', '本科', '文学与传媒学院', '教务科');

-- ----------------------------
-- Table structure for t_teachingachievement
-- ----------------------------
DROP TABLE IF EXISTS `t_teachingachievement`;
CREATE TABLE `t_teachingachievement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prize` varchar(255) DEFAULT NULL,
  `level` varchar(255) DEFAULT NULL,
  `order` varchar(255) DEFAULT NULL,
  `time` date DEFAULT NULL,
  `rank` int(10) DEFAULT NULL,
  `totalNum` int(10) DEFAULT NULL,
  `score` double(20,0) DEFAULT NULL,
  `attachment` varchar(255) DEFAULT NULL,
  `teacherId` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_teachingachievement
-- ----------------------------
INSERT INTO `t_teachingachievement` VALUES ('1', '教学成果奖', '省级', '三等奖', '2016-09-19', '1', '50', '90', '1.jpg', 'admin');
INSERT INTO `t_teachingachievement` VALUES ('2', '教学成果奖', '省级', '三等奖', '2016-09-19', '2', '50', '90', '1.jpg', 'admin');

-- ----------------------------
-- Table structure for t_teachingotherprize
-- ----------------------------
DROP TABLE IF EXISTS `t_teachingotherprize`;
CREATE TABLE `t_teachingotherprize` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prize` varchar(255) DEFAULT NULL,
  `level` varchar(255) DEFAULT NULL,
  `time` date DEFAULT NULL,
  `attachment` varchar(255) DEFAULT NULL,
  `teacherId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_teachingotherprize
-- ----------------------------
INSERT INTO `t_teachingotherprize` VALUES ('1', '优秀教师', '校级', '2016-09-19', '1.jpg', 'admin');
INSERT INTO `t_teachingotherprize` VALUES ('2', '优秀教师', '校级', '2016-09-19', '1.jpg', 'admin');

-- ----------------------------
-- Table structure for t_teachingworkload
-- ----------------------------
DROP TABLE IF EXISTS `t_teachingworkload`;
CREATE TABLE `t_teachingworkload` (
  `id` int(11) NOT NULL,
  `courseId` varchar(255) DEFAULT NULL,
  `courseName` varchar(255) DEFAULT NULL,
  `tclass` varchar(255) DEFAULT NULL,
  `tclassNum` int(11) DEFAULT NULL,
  `material` varchar(255) DEFAULT NULL,
  `periodNum` int(11) DEFAULT NULL,
  `teacherId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_teachingworkload
-- ----------------------------
INSERT INTO `t_teachingworkload` VALUES ('1', '10002332', 'Android应用程序设计', '信B1411', '39', '《疯狂Android讲义》-电子工业出版社', '96', 'admin');

-- ----------------------------
-- Table structure for t_trainingstudy
-- ----------------------------
DROP TABLE IF EXISTS `t_trainingstudy`;
CREATE TABLE `t_trainingstudy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) DEFAULT NULL,
  `direction` varchar(255) DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  `totalDays` varchar(10) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `teacherId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_trainingstudy
-- ----------------------------
INSERT INTO `t_trainingstudy` VALUES ('1', '短期培训', 'Android技术培训', '2016-09-19', '50天', '广州千锋互联有限公司', 'admin');
INSERT INTO `t_trainingstudy` VALUES ('2', '短期培训', 'Java技术培训', '2016-09-19', '50天', '广州千锋互联有限公司', 'admin');

-- ----------------------------
-- Table structure for t_workingexperience
-- ----------------------------
DROP TABLE IF EXISTS `t_workingexperience`;
CREATE TABLE `t_workingexperience` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `teacherId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_workingexperience
-- ----------------------------
INSERT INTO `t_workingexperience` VALUES ('1', '嘉兴东城科技', '技术总监', '2016-09-12', '2016-09-05', 'admin');
