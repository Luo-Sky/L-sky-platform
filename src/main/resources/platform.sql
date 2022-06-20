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

 Date: 20/06/2022 17:14:40
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
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户给定的该操作名称',
  `start_time` datetime(0) NOT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `source_img1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '上传的原始图片时相1url地址',
  `source_img2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '上传的原始图片时相2url地址',
  `result_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '结果图片的url地址',
  `result` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '这里暂时代表一下返回的分析的一些数据',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '变化检测的历史记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cdhistory
-- ----------------------------
INSERT INTO `cdhistory` VALUES (1, 1, '', '2022-05-07 00:40:54', NULL, 'http://localhost:9090/files/352e8c06b24844f4b85706f113c93b2f', 'http://localhost:9090/files/575b449259e64bf0bbc4eae2770a9609', NULL, NULL);
INSERT INTO `cdhistory` VALUES (3, 1, '', '2022-06-19 23:57:54', NULL, 'http://localhost:9090/files/33fc3d70ecae44fdb58b02fc9293bfc6', 'http://localhost:9090/files/91d9900ba7f644bda7043e8b4f920fd7', NULL, NULL);
INSERT INTO `cdhistory` VALUES (4, 1, '', '2022-06-20 00:25:14', NULL, 'http://localhost:9090/files/02ede606a03d42a080a97df8ae384965', 'http://localhost:9090/files/00adde4caa2a400bb66345a49963eb42', NULL, NULL);
INSERT INTO `cdhistory` VALUES (5, 1, '', '2022-06-20 00:39:06', NULL, 'http://localhost:9090/files/3109e41282ad49fa8714e82b01eed78f', 'http://localhost:9090/files/2d6739e385814669baf3e72fe72d255c', NULL, NULL);
INSERT INTO `cdhistory` VALUES (6, 1, '', '2022-06-20 00:40:28', NULL, 'http://localhost:9090/files/6118c8e3338943678e0b7d7006007046', 'http://localhost:9090/files/e7b0579fddfc4b9a82bf1ee3a7535211', NULL, NULL);
INSERT INTO `cdhistory` VALUES (7, 1, '', '2022-06-20 10:52:12', NULL, 'http://localhost:9090/files/7933a2ec6076422291c0bc36838bd0c8', 'http://localhost:9090/files/ccf7261dcee34fbb94412c386ee41799', NULL, NULL);
INSERT INTO `cdhistory` VALUES (8, 1, '', '2022-06-20 10:53:56', NULL, 'http://localhost:9090/files/c104a57f476a4485b1d05f9abccf7f64', 'http://localhost:9090/files/2e02ff3de4a94293928ec25735b7522a', NULL, NULL);
INSERT INTO `cdhistory` VALUES (9, 1, '', '2022-06-20 10:54:47', NULL, 'http://localhost:9090/files/f1ea7aff6ec4416c8088bbf2a9711ce9', 'http://localhost:9090/files/024664b9a275488aaaf5dc80268231ed', NULL, NULL);
INSERT INTO `cdhistory` VALUES (10, 1, '', '2022-06-20 11:08:55', NULL, 'http://localhost:9090/files/aa7656c2f9284df9a8508ef0855b65f7', 'http://localhost:9090/files/7bcd9442170644e0b2575e573d0917a3', NULL, NULL);
INSERT INTO `cdhistory` VALUES (11, 1, '', '2022-06-20 11:09:33', NULL, 'http://localhost:9090/files/d8ca62dfc35849a5aef922691c848dfc', 'http://localhost:9090/files/83f68904ab2d467c8e171134d6000a0f', NULL, NULL);
INSERT INTO `cdhistory` VALUES (12, 111, '', '2022-06-20 14:45:16', NULL, 'http://192.168.43.98:9090/files/3ed135362c0648efa64709c815c9c867', 'http://192.168.43.98:9090/files/0a0ee51e7a2c42c18c6f12adf3e76523', NULL, NULL);
INSERT INTO `cdhistory` VALUES (13, 111, '', '2022-06-20 14:45:53', NULL, 'http://192.168.43.98:9090/files/b670855fcabd4dae92ca7f3356b5511b', 'http://192.168.43.98:9090/files/783832ec031d40138a40121b2d6ef559', NULL, NULL);
INSERT INTO `cdhistory` VALUES (14, 111, '', '2022-06-20 14:47:01', NULL, 'http://192.168.43.98:9090/files/0293adec00bd49bd9aa7072d641b1cc5', 'http://192.168.43.98:9090/files/8b4e04e9bfb642b18a4c26f2a208ec4b', NULL, NULL);
INSERT INTO `cdhistory` VALUES (15, 111, '', '2022-06-20 14:49:01', NULL, 'http://192.168.43.98:9090/files/6623aca0857242c9b912f22d29feafec', 'http://192.168.43.98:9090/files/a61e7a97af87407b85127b8a38e96d8f', NULL, NULL);
INSERT INTO `cdhistory` VALUES (16, 111, '', '2022-06-20 14:52:13', NULL, 'http://192.168.43.98:9090/files/ae8ab9d9e7d04037b728e35f09b3cdc6', 'http://192.168.43.98:9090/files/26e1114796584fd69897cd3fcdbce0d0', NULL, NULL);
INSERT INTO `cdhistory` VALUES (17, 111, '', '2022-06-20 15:05:36', NULL, 'http://192.168.43.98:9090/files/b44dbe265beb446b8daa54cad29592fb', 'http://192.168.43.98:9090/files/bd4bdea36eef4b5c9b4a190708f88e1d', NULL, NULL);
INSERT INTO `cdhistory` VALUES (18, 1, '名称', '2022-06-20 17:10:07', NULL, 'http://localhost:9090/files/42c13933a9404b2095f5f4fd7016c3eb', 'http://localhost:9090/files/f1e4b9e8af5248cabdb843c859efbe33', NULL, NULL);

-- ----------------------------
-- Table structure for ochistory
-- ----------------------------
DROP TABLE IF EXISTS `ochistory`;
CREATE TABLE `ochistory`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '地物分类记录唯一标识ID',
  `project_id` bigint NOT NULL COMMENT '项目的唯一标识ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户给定的该操作名称',
  `start_time` datetime(0) NOT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `source_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '上传的原始图片url地址',
  `result_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '结果图片的url地址',
  `result` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '这里暂时代表一下返回的分析的一些数据',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '地物分类的历史记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ochistory
-- ----------------------------
INSERT INTO `ochistory` VALUES (3, 1, NULL, '2022-05-07 00:34:46', NULL, 'http://localhost:9090/files/989e0b34045a46c3960bf17272a846aa', NULL, NULL);
INSERT INTO `ochistory` VALUES (4, 1, NULL, '2022-05-07 00:34:53', NULL, 'http://localhost:9090/files/7b58d4b36f984728a90ee98ea43784c3', NULL, NULL);
INSERT INTO `ochistory` VALUES (5, 111, NULL, '2022-06-20 16:05:42', NULL, 'http://192.168.43.98:9090/files/616abf0aba6a4345b95dc27cc0cd4457', NULL, NULL);
INSERT INTO `ochistory` VALUES (6, 1, '名称', '2022-06-20 17:09:36', NULL, 'http://localhost:9090/files/d1b3e6087c4d436a910e604e4f4e829c', NULL, NULL);

-- ----------------------------
-- Table structure for odhistory
-- ----------------------------
DROP TABLE IF EXISTS `odhistory`;
CREATE TABLE `odhistory`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '目标检测记录唯一标识ID',
  `project_id` bigint NOT NULL COMMENT '项目的唯一标识ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户给定的该操作名称',
  `start_time` datetime(0) NOT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `source_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '上传的原始图片url地址',
  `result_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '结果图片的url地址',
  `result` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '这里暂时代表一下返回的分析的一些数据',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '目标检测的历史记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of odhistory
-- ----------------------------
INSERT INTO `odhistory` VALUES (1, 1, NULL, '2022-05-07 00:17:30', NULL, 'http://localhost:9090/files/607567db2c9b4b8fa8717b39d3477bfa', NULL, NULL);
INSERT INTO `odhistory` VALUES (3, 1, NULL, '2022-05-26 22:57:03', NULL, 'http://localhost:9090/files/ac8de50f4936498c8fa102bb49ceafef', NULL, NULL);
INSERT INTO `odhistory` VALUES (4, 1, NULL, '2022-05-26 22:58:29', NULL, 'http://localhost:9090/files/88786bf80a40400d97e92847559a1f53', NULL, NULL);
INSERT INTO `odhistory` VALUES (5, 1, NULL, '2022-05-26 22:59:07', NULL, 'http://localhost:9090/files/148c645e06c245b2a53a6f2ae780f674', NULL, NULL);
INSERT INTO `odhistory` VALUES (6, 111, NULL, '2022-06-20 16:05:24', NULL, 'http://192.168.43.98:9090/files/a41cd3be98784a5ab54aa2b41c08fc69', NULL, NULL);
INSERT INTO `odhistory` VALUES (7, 1, NULL, '2022-06-20 16:34:18', '2022-06-20 16:36:30', 'http://localhost:9090/files/d922df3b40e34d9f897b6aac89760344', 'http://localhost:9090/files/d922df3b40e34d9f897b6aac89760344_predict', '{\"data\":[],\"label\":\"http://localhost:9090/files/d922df3b40e34d9f897b6aac89760344_predict\"}');
INSERT INTO `odhistory` VALUES (8, 1, '名称', '2022-06-20 17:08:10', NULL, 'http://localhost:9090/files/b107c548e61c4c01b9e51204d377a293', NULL, NULL);

-- ----------------------------
-- Table structure for oehistory
-- ----------------------------
DROP TABLE IF EXISTS `oehistory`;
CREATE TABLE `oehistory`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '目标提取记录唯一标识ID',
  `project_id` bigint NOT NULL COMMENT '项目的唯一标识ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户给定的该操作名称',
  `start_time` datetime(0) NOT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `source_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '上传的原始图片url地址',
  `result_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '结果图片的url地址',
  `result` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '这里暂时代表一下返回的分析的一些数据',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '目标提取的历史记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oehistory
-- ----------------------------
INSERT INTO `oehistory` VALUES (1, 1, NULL, '2022-05-07 00:29:53', '2022-06-20 16:21:02', 'http://localhost:9090/files/2ea0b61dec564fb4aedc5e7654a5fd6a', 'http://localhost:9090/files/f87a2d7ba1e941ffab87827c3ac83749_predict', '{\"data\":\"\",\"label\":\"http://localhost:9090/files/f87a2d7ba1e941ffab87827c3ac83749_predict\"}');
INSERT INTO `oehistory` VALUES (3, 111, NULL, '2022-05-22 17:36:28', NULL, 'http://localhost:9090/files/0fe6c9032870450bb0dfce9f79f53133', NULL, NULL);
INSERT INTO `oehistory` VALUES (4, 111, NULL, '2022-05-22 17:40:25', NULL, 'http://localhost:9090/files/b31ab5ab2d7f466bbcb3ad7cb20bb174', NULL, NULL);
INSERT INTO `oehistory` VALUES (5, 111, NULL, '2022-05-22 17:40:40', NULL, 'http://localhost:9090/files/4e06727dc39247e6864605a0a599bead', NULL, NULL);
INSERT INTO `oehistory` VALUES (6, 111, NULL, '2022-05-22 17:42:30', NULL, 'http://localhost:9090/files/142ce8c6ad1a48e19d653e2e48528b48', NULL, NULL);
INSERT INTO `oehistory` VALUES (7, 111, NULL, '2022-05-22 17:42:57', NULL, 'http://localhost:9090/files/a784207ac6574a068507ebabbcbe9b7f', NULL, NULL);
INSERT INTO `oehistory` VALUES (8, 111, NULL, '2022-05-22 17:43:25', NULL, 'http://localhost:9090/files/4ad9f67367684523981d7010263c070c', NULL, NULL);
INSERT INTO `oehistory` VALUES (9, 111, NULL, '2022-05-22 17:45:17', NULL, 'http://localhost:9090/files/32ee59a0769140e8a69bfef9c0a66fc2', NULL, NULL);
INSERT INTO `oehistory` VALUES (25, 1, NULL, '2022-06-20 15:58:41', NULL, 'http://localhost:9090/files/f87a2d7ba1e941ffab87827c3ac83749', NULL, NULL);
INSERT INTO `oehistory` VALUES (26, 111, NULL, '2022-06-20 16:42:32', '2022-06-20 16:42:52', 'http://192.168.43.98:9090/files/40b7c2133fdf4ff79e4efc7da9581d46', 'http://192.168.43.98:9090/files/e8d6decb434c44a88940f5b626476c8a_predict', '{\"data\":\"\",\"label\":\"http://192.168.43.98:9090/files/e8d6decb434c44a88940f5b626476c8a_predict\"}');
INSERT INTO `oehistory` VALUES (27, 111, NULL, '2022-06-20 16:42:49', NULL, 'http://192.168.43.98:9090/files/3bb4ad5ed1a74a55927d5997d5e240ae', NULL, NULL);
INSERT INTO `oehistory` VALUES (28, 111, NULL, '2022-06-20 16:42:51', '2022-06-20 16:43:42', 'http://192.168.43.98:9090/files/e8d6decb434c44a88940f5b626476c8a', 'http://192.168.43.98:9090/files/6d90ab3e84e944cd8ad31d14c38cc80a_predict', '{\"data\":\"\",\"label\":\"http://192.168.43.98:9090/files/6d90ab3e84e944cd8ad31d14c38cc80a_predict\"}');
INSERT INTO `oehistory` VALUES (29, 111, NULL, '2022-06-20 16:43:41', '2022-06-20 16:44:06', 'http://192.168.43.98:9090/files/6d90ab3e84e944cd8ad31d14c38cc80a', 'http://192.168.43.98:9090/files/36b29472c6db42e8a168aa31d9fcae74_predict', '{\"data\":\"\",\"label\":\"http://192.168.43.98:9090/files/36b29472c6db42e8a168aa31d9fcae74_predict\"}');
INSERT INTO `oehistory` VALUES (30, 111, NULL, '2022-06-20 16:44:05', NULL, 'http://192.168.43.98:9090/files/36b29472c6db42e8a168aa31d9fcae74', NULL, NULL);
INSERT INTO `oehistory` VALUES (31, 1, '名称', '2022-06-20 17:08:59', NULL, 'http://localhost:9090/files/3b434e7b38024db3915f47970c3f4f43', NULL, NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

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
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户手机号',
  `mail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'hongbo', '123456', '13413128480', '11111@qq.com');
INSERT INTO `user` VALUES (2, '王刚', 'esse occaecat aliquip Ut', '18653752256', 'n.stvi@qq.com');
INSERT INTO `user` VALUES (6, '吴弘博', '123456', '11111111111', '954123893@qq.com');

SET FOREIGN_KEY_CHECKS = 1;
