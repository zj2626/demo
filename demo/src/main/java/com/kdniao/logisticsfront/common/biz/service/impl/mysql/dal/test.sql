/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.1.233-kdniao
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : 192.168.1.233:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 09/06/2019 17:16:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for testa
-- ----------------------------
DROP TABLE IF EXISTS `testa`;
CREATE TABLE `testa`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `AGE` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `A_AGE`(`AGE`) USING BTREE,
  INDEX `A_A`(`ID`, `AGE`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44000008 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for testb
-- ----------------------------
DROP TABLE IF EXISTS `testb`;
CREATE TABLE `testb`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `AGE` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `B_AGE`(`AGE`) USING BTREE,
  INDEX `B_B`(`ID`, `AGE`) USING BTREE,
  INDEX `B_NAME`(`NAME`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8830005 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for testc
-- ----------------------------
DROP TABLE IF EXISTS `testc`;
CREATE TABLE `testc`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `AGE` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `C_AGE`(`AGE`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for testd
-- ----------------------------
DROP TABLE IF EXISTS `testd`;
CREATE TABLE `testd`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `AGE` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `A_NAME`(`NAME`) USING BTREE,
  INDEX `D_AGE`(`AGE`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for uc_area
-- ----------------------------
DROP TABLE IF EXISTS `uc_area`;
CREATE TABLE `uc_area`  (
  `id` char(44) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `area_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `area_name` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `level` int(10) NULL DEFAULT NULL COMMENT '区域级别  1大陆,2省,3市/自治州,4区/县,5乡/镇/街道',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `Indexes01`(`area_name`, `level`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- View structure for v1
-- ----------------------------
DROP VIEW IF EXISTS `v1`;
CREATE ALGORITHM = UNDEFINED DEFINER = `root`@`%` SQL SECURITY DEFINER VIEW `v1` AS select `testa`.`ID` AS `ID`,`testa`.`AGE` AS `AGE` from `testa` where (`testa`.`AGE` = 10);

SET FOREIGN_KEY_CHECKS = 1;
