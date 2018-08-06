/*
 Navicat Premium Data Transfer

 Source Server         : test_210
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : 172.16.10.210
 Source Database       : cdn_log

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : utf-8

 Date: 07/06/2018 20:58:01 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `data_log`
-- ----------------------------
DROP TABLE IF EXISTS `data_log`;
CREATE TABLE `data_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ip` varchar(255) DEFAULT NULL,
  `referer` varchar(100) DEFAULT NULL,
  `target_url` varchar(150) DEFAULT NULL,
  `date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `log_download_record`
-- ----------------------------
DROP TABLE IF EXISTS `log_download_record`;
CREATE TABLE `log_download_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `name` varchar(120) NOT NULL DEFAULT '' COMMENT '文件名',
  `size` int(11) DEFAULT NULL COMMENT '文件大小',
  `key` varchar(32) NOT NULL COMMENT 'name md5后的值',
  `log_path` varchar(250) DEFAULT '' COMMENT '下载路径',
  `status_flag` tinyint(2) DEFAULT '0' COMMENT '状态 0 正常 -1 下载失败',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_key` (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志文件下载记录'

-- ----------------------------
--  Table structure for `log_import_record`
-- ----------------------------
DROP TABLE IF EXISTS `log_import_record`;
CREATE TABLE `log_import_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `name` varchar(120) NOT NULL DEFAULT '' COMMENT '文件名',
  `size` int(11) DEFAULT NULL COMMENT '文件大小',
  `key` varchar(32) NOT NULL COMMENT 'name md5后的值',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_key` (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志文件分析记录'

-- ----------------------------
--  Table structure for `record`
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `url` varchar(600) DEFAULT NULL COMMENT 'url地址',
  `type` tinyint(2) DEFAULT NULL COMMENT '类型 1 资源地址 2 来源平台地址',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_prefix_url` (`url`(50)) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='推广纪录表'

-- ----------------------------
--  Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `name` varchar(20) DEFAULT NULL,
  `sort` smallint(6) DEFAULT NULL COMMENT '排序',
  `desc` varchar(100) DEFAULT NULL COMMENT '描述',
  `status_flag` tinyint(2) DEFAULT '0' COMMENT '状态 0 正常 1 删除',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
--  Records of `role`
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES ('1', 'ROLE_ADMIN', '1', '管理员', '0', '2018-06-26 16:02:28', '2018-06-26 16:02:28'), ('2', 'ROLE_USER', '2', '普通用户', '0', '2018-06-26 16:02:28', '2018-06-26 16:02:28');
COMMIT;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(40) NOT NULL COMMENT '用户名',
  `password` varchar(60) NOT NULL COMMENT '密码',
  `cellphone` varchar(15) DEFAULT NULL COMMENT '手机号',
  `status_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态 0 正常 1 删除',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('1', 'baidu', '$2a$10$eM2bx5atIGnDQ7FLI8xoM.kE45KP0WjCEP8mu2eHhboDSJXR3I91O', null, '0', '2018-06-26 15:29:01', '2018-06-26 15:29:04'), ('2', 'test001', '$2a$10$OXPIkaujC73/HmJKNFHAxe6HgIGENKdHDDO1mSYn8WfbQAIDFrYIy', '', '0', '2018-06-26 15:29:30', '2018-06-26 16:29:27'), ('3', 'test002', '$2a$10$Edup9NtHJ8QcGDgMVqqXiOX7wDWtTIAnTtrY7/n1Yf8LbIYHS3ILK', '', '0', '2018-06-26 15:30:05', '2018-06-26 16:30:01');
COMMIT;

-- ----------------------------
--  Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户Id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色Id',
  `status_flag` tinyint(2) DEFAULT '0' COMMENT '状态 0 正常 1 删除',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户权限表';

-- ----------------------------
--  Records of `user_role`
-- ----------------------------
BEGIN;
INSERT INTO `user_role` VALUES ('1', '1', '1', '0', '2018-06-26 15:28:13', '2018-06-26 15:28:18'), ('2', '2', '2', '0', '2018-06-26 16:29:27', '2018-06-26 16:29:27'), ('3', '3', '1', '0', '2018-06-26 16:30:01', '2018-06-26 16:30:01');
COMMIT;

-- ----------------------------
--  Table structure for `error_record`
-- ----------------------------
DROP TABLE IF EXISTS `error_record`;
CREATE TABLE `error_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) DEFAULT NULL,
  `time` bigint(20) DEFAULT NULL,
  `status` tinyint(2) DEFAULT '0' COMMENT '0 未处理；1 已处理',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='导入时按天统计失败的记录';

-- ----------------------------
--  Table structure for `cdn_setting`
-- ----------------------------
DROP TABLE IF EXISTS `cdn_setting`;
CREATE TABLE `cdn_setting` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `domain` varchar(40) DEFAULT NULL COMMENT '域名',
  `key` varchar(40) DEFAULT NULL COMMENT '域名对应的AccessKeyId',
  `secret` varchar(60) DEFAULT NULL COMMENT '域名对应的AccessKeySecret',
  `status_flag` tinyint(2) DEFAULT '0' COMMENT '状态 0 正常 1 删除',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='CDN域名配置记录';

-- ----------------------------
--  Records of `cdn_setting`
-- ----------------------------
BEGIN;
INSERT INTO `cdn_setting` VALUES ('1', 'down.fcbtq.com', 'LTAIbzOJzjxBqAuj', 'Aw5faogIc2G400wrGxyylECn1gddmh', '0', '2018-08-03 20:53:04'), ('2', 'feedback.seekxhb.com', 'LTAIbzOJzjxBqAuj', 'Aw5faogIc2G400wrGxyylECn1gddmh', '0', '2018-08-05 11:34:12'), ('3', 'www.yunczl.com', 'LTAIbzOJzjxBqAuj', 'Aw5faogIc2G400wrGxyylECn1gddmh', '0', '2018-08-05 11:35:16'), ('4', 'www.tn830.com', 'LTAIbzOJzjxBqAuj', 'Aw5faogIc2G400wrGxyylECn1gddmh', '0', '2018-08-05 11:35:38'), ('5', 'down.tn830.com', 'LTAIbzOJzjxBqAuj', 'Aw5faogIc2G400wrGxyylECn1gddmh', '0', '2018-08-05 11:35:52'), ('6', 'down.aixi360.com', 'LTAIbzOJzjxBqAuj', 'Aw5faogIc2G400wrGxyylECn1gddmh', '0', '2018-08-05 11:36:02'), ('7', 'www.hoowine.com', 'LTAIbzOJzjxBqAuj', 'Aw5faogIc2G400wrGxyylECn1gddmh', '0', '2018-08-05 11:36:12'), ('8', 'download.30755.net', 'LTAIbzOJzjxBqAuj', 'Aw5faogIc2G400wrGxyylECn1gddmh', '0', '2018-08-05 11:36:31');
COMMIT;


SET FOREIGN_KEY_CHECKS = 1;
