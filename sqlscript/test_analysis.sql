/*
 Navicat Premium Data Transfer

 Source Server         : test_210
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : 172.16.10.210
 Source Database       : test_analysis

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : utf-8

 Date: 06/22/2018 00:27:33 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `admin_user`
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(40) NOT NULL COMMENT '用户名',
  `password` varchar(60) NOT NULL COMMENT '密码',
  `cellphone` varchar(15) DEFAULT NULL COMMENT '手机号',
  `status_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态 0 正常 1 删除',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
--  Records of `admin_user`
-- ----------------------------
BEGIN;
INSERT INTO `admin_user` VALUES ('1', 'baidu', '$2a$10$A/THneODMm/07Q5/EwexNe3nOYNtrEpuGjLfh9ScTUyoRCt3oAiBi', null, '0', '2018-06-19 21:52:55');
COMMIT;

-- ----------------------------
--  Table structure for `data_log`
-- ----------------------------
DROP TABLE IF EXISTS `data_log`;
CREATE TABLE `data_log` (
  `id` bigint(20) NOT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `referer` varchar(100) DEFAULT NULL,
  `target_url` varchar(150) DEFAULT NULL,
  `date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
