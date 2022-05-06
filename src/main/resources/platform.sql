/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : platform

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 07/05/2022 00:48:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cdhistory
-- ----------------------------
DROP TABLE IF EXISTS `cdhistory`;
CREATE TABLE `cdhistory`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '变化检测记录唯一标识ID',
  `project_id` bigint NOT NULL COMMENT '项目的唯一标识ID',
  `start_time` datetime(0) NOT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `source_img1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '上传的原始图片时相1url地址',
  `source_img2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '上传的原始图片时相2url地址',
  `result_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '结果图片的url地址',
  `result` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '这里暂时代表一下返回的分析的一些数据',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '变化检测的历史记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cdhistory
-- ----------------------------
INSERT INTO `cdhistory` VALUES (1, 1, '2022-05-07 00:40:54', NULL, 'http://localhost:9090/files/352e8c06b24844f4b85706f113c93b2f', 'http://localhost:9090/files/575b449259e64bf0bbc4eae2770a9609', NULL, NULL);

-- ----------------------------
-- Table structure for ochistory
-- ----------------------------
DROP TABLE IF EXISTS `ochistory`;
CREATE TABLE `ochistory`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '地物分类记录唯一标识ID',
  `project_id` bigint NOT NULL COMMENT '项目的唯一标识ID',
  `start_time` datetime(0) NOT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `source_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '上传的原始图片url地址',
  `result_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '结果图片的url地址',
  `result` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '这里暂时代表一下返回的分析的一些数据',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '地物分类的历史记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ochistory
-- ----------------------------
INSERT INTO `ochistory` VALUES (3, 1, '2022-05-07 00:34:46', NULL, 'http://localhost:9090/files/989e0b34045a46c3960bf17272a846aa', NULL, NULL);
INSERT INTO `ochistory` VALUES (4, 1, '2022-05-07 00:34:53', NULL, 'http://localhost:9090/files/7b58d4b36f984728a90ee98ea43784c3', NULL, NULL);

-- ----------------------------
-- Table structure for odhistory
-- ----------------------------
DROP TABLE IF EXISTS `odhistory`;
CREATE TABLE `odhistory`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '目标检测记录唯一标识ID',
  `project_id` bigint NOT NULL COMMENT '项目的唯一标识ID',
  `start_time` datetime(0) NOT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `source_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '上传的原始图片url地址',
  `result_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '结果图片的url地址',
  `result` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '这里暂时代表一下返回的分析的一些数据',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '目标检测的历史记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of odhistory
-- ----------------------------
INSERT INTO `odhistory` VALUES (1, 1, '2022-05-07 00:17:30', NULL, 'http://localhost:9090/files/607567db2c9b4b8fa8717b39d3477bfa', NULL, NULL);

-- ----------------------------
-- Table structure for oehistory
-- ----------------------------
DROP TABLE IF EXISTS `oehistory`;
CREATE TABLE `oehistory`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '目标提取记录唯一标识ID',
  `project_id` bigint NOT NULL COMMENT '项目的唯一标识ID',
  `start_time` datetime(0) NOT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `source_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '上传的原始图片url地址',
  `result_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '结果图片的url地址',
  `result` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '这里暂时代表一下返回的分析的一些数据',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '目标提取的历史记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oehistory
-- ----------------------------
INSERT INTO `oehistory` VALUES (1, 1, '2022-05-07 00:29:53', NULL, 'http://localhost:9090/files/2ea0b61dec564fb4aedc5e7654a5fd6a', NULL, NULL);

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '项目的唯一ID',
  `user_id` bigint NOT NULL COMMENT '用户的唯一标识ID',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目的描述',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '项目名称',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES (1, 1, '修改后的描述', '项目1', '2016-03-12 11:29:37');
INSERT INTO `project` VALUES (3, 1, '项目2的描述', '项目2', '2009-08-20 17:57:30');
INSERT INTO `project` VALUES (4, 1, NULL, '空项目', '2008-05-22 14:08:56');
INSERT INTO `project` VALUES (5, 1, '项目3的描述', '项目3', '1972-02-21 14:02:37');
INSERT INTO `project` VALUES (6, 1, '项目4的描述', '项目4', '1985-09-19 08:01:52');
INSERT INTO `project` VALUES (7, 1, '项目5的描述', '项目5', '1985-09-19 08:01:52');
INSERT INTO `project` VALUES (8, 1, '项目6的描述', '项目6', '1985-09-19 08:01:52');
INSERT INTO `project` VALUES (9, 1, '项目7的描述', '项目7', '1985-09-19 08:01:52');
INSERT INTO `project` VALUES (10, 1, '项目8的描述', '项目8', '1985-09-19 08:01:52');
INSERT INTO `project` VALUES (11, 1, '项目9的描述', '项目9', '1985-09-19 08:01:52');
INSERT INTO `project` VALUES (12, 1, '项目10的描述', '项目10', '1985-09-19 08:01:52');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户唯一标识ID',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户手机号',
  `mail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'hongbo', '123456', '13413128480', '11111@qq.com');
INSERT INTO `user` VALUES (2, '王刚', 'esse occaecat aliquip Ut', '18653752256', 'n.stvi@qq.com');

SET FOREIGN_KEY_CHECKS = 1;
