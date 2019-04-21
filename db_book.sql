/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50720
Source Host           : :3306
Source Database       : db_book

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-11-29 15:52:08
*/
SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;
drop database db_book;
create Database db_book CHARACTER SET utf8 COLLATE utf8_general_ci;
use db_book;

-- ----------------------------
-- Table structure for `book`
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `bid` int(20) NOT NULL AUTO_INCREMENT,
  `bisbn` int(20) NOT NULL,
  `bname` varchar(40) NOT NULL,
  `bwriter` varchar(20) NOT NULL,
  `bnumber` int(10) NOT NULL,
  `boutnumber` int(10) default null ,
  `bsort` varchar(20) NOT NULL,
  `bpub` varchar(20) NOT NULL,
  `bprice` decimal(10,0) NOT NULL,
  `bintro` varchar(100) DEFAULT NULL,
  `bpic` varchar(50) default null ,
  PRIMARY KEY (`bid`),
  UNIQUE KEY `bisbn` (`bisbn`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book
-- ----------------------------

-- ----------------------------
-- Table structure for `manager`
-- ----------------------------
DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager` (
  `mid` int(20) NOT NULL AUTO_INCREMENT,
  `mname` varchar(255) NOT NULL,
  `mpwd` varchar(20) NOT NULL,
  PRIMARY KEY (`mid`),
  UNIQUE KEY `mname` (`mname`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of manager
-- ----------------------------

-- ----------------------------
-- Table structure for `reader`
-- ----------------------------
DROP TABLE IF EXISTS `reader`;
CREATE TABLE `reader` (
  `rid` int(30) NOT NULL AUTO_INCREMENT,
  `rname` varchar(20) NOT NULL,
  `rpwd` varchar(20) NOT NULL,
  `rage` int(4) DEFAULT NULL,
  `rsex` char(6) DEFAULT NULL,
  PRIMARY KEY (`rid`),
  UNIQUE KEY `rname` (`rname`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reader
-- ----------------------------
INSERT INTO `reader` VALUES ('1', '1', '1', '1', 'male');
INSERT INTO `reader` VALUES ('3', '?', '3', '1', 'male');

-- ----------------------------
-- Table structure for `record`
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `bisbn` int(20) NOT NULL,
  `rname` varchar(30) NOT NULL,
  `outdate` date NOT NULL,
  `indate` date DEFAULT NULL,
  `penalty` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of record
-- ----------------------------
