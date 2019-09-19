/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.1.0
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : 192.168.1.0:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 15/06/2019 14:11:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `c_custkey` int(11) NULL DEFAULT NULL,
  `c_name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `c_address` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `c_nationkey` int(11) NULL DEFAULT NULL,
  `c_phone` char(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `c_acctbal` double NULL DEFAULT NULL,
  `c_mktsegment` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `c_comment` varchar(117) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for lineitem
-- ----------------------------
DROP TABLE IF EXISTS `lineitem`;
CREATE TABLE `lineitem`  (
  `l_orderkey` int(11) NULL DEFAULT NULL,
  `l_partkey` int(11) NULL DEFAULT NULL,
  `l_suppkey` int(11) NULL DEFAULT NULL,
  `l_linenumber` int(11) NULL DEFAULT NULL,
  `l_quantity` double NULL DEFAULT NULL,
  `l_extendedprice` double NULL DEFAULT NULL,
  `l_discount` double NULL DEFAULT NULL,
  `l_tax` double NULL DEFAULT NULL,
  `l_returnflag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `l_linestatus` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `l_shipDATE` date NULL DEFAULT NULL,
  `l_commitDATE` date NULL DEFAULT NULL,
  `l_receiptDATE` date NULL DEFAULT NULL,
  `l_shipinstruct` char(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `l_shipmode` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `l_comment` varchar(44) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  INDEX `l_partkey`(`l_partkey`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for nation
-- ----------------------------
DROP TABLE IF EXISTS `nation`;
CREATE TABLE `nation`  (
  `n_nationkey` int(11) NULL DEFAULT NULL,
  `n_name` char(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `n_regionkey` int(11) NULL DEFAULT NULL,
  `n_comment` varchar(152) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  INDEX `n_regionkey`(`n_regionkey`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `o_orderkey` int(11) NULL DEFAULT NULL,
  `o_custkey` int(11) NULL DEFAULT NULL,
  `o_orderstatus` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `o_totalprice` double NULL DEFAULT NULL,
  `o_orderDATE` date NULL DEFAULT NULL,
  `o_orderpriority` char(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `o_clerk` char(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `o_shippriority` int(11) NULL DEFAULT NULL,
  `o_comment` varchar(79) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  INDEX `o_custkey`(`o_totalprice`, `o_custkey`, `o_orderkey`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for orders_copy1
-- ----------------------------
DROP TABLE IF EXISTS `orders_copy1`;
CREATE TABLE `orders_copy1`  (
  `o_orderkey` int(11) NULL DEFAULT NULL,
  `o_custkey` int(11) NULL DEFAULT NULL,
  `o_orderstatus` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `o_totalprice` double NULL DEFAULT NULL,
  `o_orderDATE` date NULL DEFAULT NULL,
  `o_orderpriority` char(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `o_clerk` char(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `o_shippriority` int(11) NULL DEFAULT NULL,
  `o_comment` varchar(79) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `o_custkey`(`o_custkey`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1500001 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for part
-- ----------------------------
DROP TABLE IF EXISTS `part`;
CREATE TABLE `part`  (
  `p_partkey` int(11) NULL DEFAULT NULL,
  `p_name` varchar(55) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `p_mfgr` char(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `p_brand` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `p_type` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `p_size` int(11) NULL DEFAULT NULL,
  `p_container` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `p_retailprice` double NULL DEFAULT NULL,
  `p_comment` varchar(23) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  INDEX `p_size`(`p_size`) USING BTREE,
  INDEX `p_type`(`p_type`) USING BTREE,
  INDEX `p_partkey`(`p_partkey`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for partsupp
-- ----------------------------
DROP TABLE IF EXISTS `partsupp`;
CREATE TABLE `partsupp`  (
  `ps_partkey` int(11) NULL DEFAULT NULL,
  `ps_suppkey` int(11) NULL DEFAULT NULL,
  `ps_availqty` int(11) NULL DEFAULT NULL,
  `ps_supplycost` double NULL DEFAULT NULL,
  `ps_comment` varchar(199) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  INDEX `ps_partkey`(`ps_partkey`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for region
-- ----------------------------
DROP TABLE IF EXISTS `region`;
CREATE TABLE `region`  (
  `r_regionkey` int(11) NULL DEFAULT NULL,
  `r_name` char(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `r_comment` varchar(152) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for supplier
-- ----------------------------
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier`  (
  `s_suppkey` int(11) NULL DEFAULT NULL,
  `s_name` char(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `s_address` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `s_nationkey` int(11) NULL DEFAULT NULL,
  `s_phone` char(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `s_acctbal` double NULL DEFAULT NULL,
  `s_comment` varchar(101) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  INDEX `s_suppkey`(`s_suppkey`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for testc
-- ----------------------------
DROP TABLE IF EXISTS `testc`;
CREATE TABLE `testc`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `AGE` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `AN`(`NAME`, `AGE`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4001 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for testd
-- ----------------------------
DROP TABLE IF EXISTS `testd`;
CREATE TABLE `testd`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `AGE` int(11) NULL DEFAULT NULL,
  `SEX` int(11) NULL DEFAULT NULL,
  `COMMENT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `TOL`(`NAME`, `AGE`, `SEX`, `COMMENT`) USING BTREE,
  INDEX `COMMENT`(`COMMENT`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8001 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
