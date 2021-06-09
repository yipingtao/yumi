/*
Navicat MySQL Data Transfer

Source Server         : 阿里云数据库-101.133.235.204
Source Server Version : 50729
Source Host           : 101.133.235.204:3306
Source Database       : mydata

Target Server Type    : MYSQL
Target Server Version : 50729
File Encoding         : 65001

Date: 2020-02-19 12:07:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for appointment
-- ----------------------------
DROP TABLE IF EXISTS `appointment`;
CREATE TABLE `appointment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT '0' COMMENT '用户id',
  `nailArtistId` int(11) DEFAULT '0' COMMENT '美甲师id',
  `startTime` bigint(20) DEFAULT '0' COMMENT '开始时间',
  `endTime` bigint(20) DEFAULT '0' COMMENT '结束时间',
  `createTime` bigint(20) DEFAULT '0',
  `state` int(4) DEFAULT '1' COMMENT '1预约 2取消预约',
  `isActive` int(4) DEFAULT '1' COMMENT '1有效 2 无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of appointment
-- ----------------------------
INSERT INTO `appointment` VALUES ('13', '4', '1', '1577808000000', '1577894340000', '1581989622833', '2', '1');
INSERT INTO `appointment` VALUES ('14', '4', '1', '1577894400000', '1577980740000', '1581989634848', '2', '1');
INSERT INTO `appointment` VALUES ('15', '4', '1', '1577980800000', '1578067140000', '1581989801904', '2', '1');
INSERT INTO `appointment` VALUES ('16', '4', '3', '1582041600000', '1582127940000', '1582014636416', '2', '1');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `storeId` int(11) DEFAULT NULL COMMENT '店铺id',
  `userId` int(11) DEFAULT NULL COMMENT '用户id',
  `content` text COMMENT '内容',
  `createTime` bigint(20) DEFAULT '0',
  `orderId` int(11) DEFAULT '0' COMMENT '主订单id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('21', '1', '4', '阿萨德阿萨德', '1582001457667', '11');
INSERT INTO `comment` VALUES ('22', '1', '4', '收到阿萨德', '1582001495281', '10');
INSERT INTO `comment` VALUES ('23', '7', '4', '非常好', '1582014786185', '15');

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT '' COMMENT '名称',
  `money` varchar(50) DEFAULT '' COMMENT '价位',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `photo` varchar(255) DEFAULT '' COMMENT '照片',
  `isActive` int(11) DEFAULT '1' COMMENT '1 有效 2 无效',
  `storeId` int(11) DEFAULT '0' COMMENT '店铺id',
  `remark` varchar(255) DEFAULT '' COMMENT '简介',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('100', '美甲商品1', '20', '1581848146135', 'http://localhost:8088/b14e83a658bd41b390684a4094497a9f.jpg', '1', '1', '美甲商品1');
INSERT INTO `goods` VALUES ('114', '美甲商品2', '88', '1581849722855', 'http://localhost:8088/42b34afa9e4d46e1a7fe69cf0a385a7b.jpg', '1', '2', '美甲二号店商品2');
INSERT INTO `goods` VALUES ('115', '美甲商品3', '66', '1581849769376', 'http://localhost:8088/66dac04f74e3478084530624c468ed26.jpg', '1', '1', '美甲商品一号店');
INSERT INTO `goods` VALUES ('116', '美甲商品4', '66', '1582014435239', 'http://localhost:8088/00efc619c7e849f3beb1b792c48b93eb.jpg', '1', '7', '系列美甲套餐');
INSERT INTO `goods` VALUES ('117', '美甲商品6', '777', '1582014471525', 'http://localhost:8088/de5a53dec94745c9bd4c551e82c1c3e7.jpg', '1', '7', '美捷美');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `storeId` int(11) DEFAULT NULL COMMENT '招聘id',
  `userId` int(11) DEFAULT NULL COMMENT '用户id',
  `content` text COMMENT '内容',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `isActive` int(50) DEFAULT '1' COMMENT '1有效 2无效',
  `recontent` text COMMENT '回复内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('97', '97', '2', '我要去', '1552399703399', '1', null);
INSERT INTO `message` VALUES ('98', '99', '2', '你好', '1552399792641', '1', null);
INSERT INTO `message` VALUES ('99', '99', '2', '我要留言', '1552400347875', '1', null);
INSERT INTO `message` VALUES ('100', '99', '2', '娃哈哈', '1552402914996', '1', null);

-- ----------------------------
-- Table structure for nailArtist
-- ----------------------------
DROP TABLE IF EXISTS `nailArtist`;
CREATE TABLE `nailArtist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT '' COMMENT '名称',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `photo` varchar(255) DEFAULT '' COMMENT '照片',
  `isActive` int(11) DEFAULT '1' COMMENT '1 有效 2 无效',
  `storeId` int(11) DEFAULT '0' COMMENT '店铺id',
  `remark` varchar(255) DEFAULT '' COMMENT '简介',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of nailArtist
-- ----------------------------
INSERT INTO `nailArtist` VALUES ('1', '美甲师1', '1581907414703', 'http://localhost:8088/503ffae3980045e39108da74175c6e35.jpg', '1', '1', '专业美甲师');
INSERT INTO `nailArtist` VALUES ('2', '美甲师2', '1581907638713', 'http://localhost:8088/3547ec860bfd4d08b6392a388048c871.jpg', '1', '2', '专业美甲师');
INSERT INTO `nailArtist` VALUES ('3', '美甲师3', '1582014495051', 'http://localhost:8088/2cbfdaaa1997418295dfe861c3153192.jpg', '1', '7', '专业美甲');
INSERT INTO `nailArtist` VALUES ('4', '美甲师4', '1582014514638', 'http://localhost:8088/4216584390744e989173cb69007c1ccf.jpg', '1', '7', '专业美美');

-- ----------------------------
-- Table structure for ordermain
-- ----------------------------
DROP TABLE IF EXISTS `ordermain`;
CREATE TABLE `ordermain` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) DEFAULT '' COMMENT '订单编号',
  `money` varchar(50) DEFAULT '' COMMENT '价位',
  `storeId` int(11) DEFAULT NULL COMMENT '店铺Id',
  `userId` int(11) DEFAULT NULL COMMENT '用户ID',
  `storeName` varchar(50) DEFAULT '' COMMENT '店铺名称',
  `userName` varchar(50) DEFAULT '' COMMENT '用户名称',
  `address` varchar(255) DEFAULT '' COMMENT '送货地址',
  `content` text COMMENT '备注',
  `isActive` int(11) DEFAULT '1' COMMENT '1 有效 2 无效 ',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `state` tinyint(4) DEFAULT '1' COMMENT '1未支付 2已支付（待发货） 3已发货（待收货）4已收货（待评价）5已评价 6已退款',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ordermain
-- ----------------------------
INSERT INTO `ordermain` VALUES ('1', '1', '11', '1', '1', '111', '111', '11', '111', '1', '0', '4');
INSERT INTO `ordermain` VALUES ('2', '2', '172', '1', '4', '美甲一号店', '用户3', '上海市奉贤区百盛大厦一楼', null, '1', '1581923479412', '3');
INSERT INTO `ordermain` VALUES ('3', 'DJ1581923479412', '172', '1', '4', '美甲一号店', '用户3', '是哪个还', null, '1', '1581923993981', '3');
INSERT INTO `ordermain` VALUES ('4', 'DJ1581923479412', '172', '1', '4', '美甲一号店', '用户3', '上海市第十', null, '1', '1581926942469', '3');
INSERT INTO `ordermain` VALUES ('5', 'DJ1581923479412', '152', '1', '4', '美甲一号店', '用户3', '阿达说的', null, '1', '1581927031558', '3');
INSERT INTO `ordermain` VALUES ('6', 'DJ1581923479412', '172', '1', '4', '美甲一号店', '用户3', '奥术大师多', null, '1', '1581927130235', '3');
INSERT INTO `ordermain` VALUES ('7', 'DJ1581923479412', '86', '1', '4', '美甲一号店', '用户3', '啊啊二无群二Asdasd', null, '1', '1581927240567', '5');
INSERT INTO `ordermain` VALUES ('8', 'DJ1581923479412', '172', '1', '4', '美甲一号店', '用户3', '阿达说的阿达', null, '1', '1581927455152', '5');
INSERT INTO `ordermain` VALUES ('9', 'DJ1581923479412', '390', '1', '4', '美甲一号店', '用户3', '上海市', null, '1', '1581942232249', '6');
INSERT INTO `ordermain` VALUES ('10', 'DJ1581923479412', '416', '1', '4', '美甲一号店', '用户3', '上海市', null, '1', '1581942429343', '6');
INSERT INTO `ordermain` VALUES ('11', 'DJ1581923479412', '172', '1', '4', '美甲一号店', '用户3', '北京市五里屯一号楼', null, '1', '1581984817816', '6');
INSERT INTO `ordermain` VALUES ('12', 'DJ1581923479412', '238', '1', '4', '美甲一号店', '用户3', '北京市五里屯XX楼', null, '1', '1581994855558', '6');
INSERT INTO `ordermain` VALUES ('13', 'DJ1582002408384', '238', '1', '4', '美甲一号店', '用户3', '大声道撒阿萨德', null, '1', '1582002408312', '6');
INSERT INTO `ordermain` VALUES ('14', 'DJ1582014592124', '152', '1', '4', '美甲一号店', '用户3', '上海市徐家汇大厦一楼', null, '1', '1582014592058', '6');
INSERT INTO `ordermain` VALUES ('15', 'DJ1582014744415', '2397', '7', '4', '美甲四号店', '用户3', '上海市徐家汇一楼', null, '1', '1582014744414', '5');

-- ----------------------------
-- Table structure for ordersub
-- ----------------------------
DROP TABLE IF EXISTS `ordersub`;
CREATE TABLE `ordersub` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goodsId` int(11) DEFAULT NULL COMMENT '商品id',
  `mainId` int(11) DEFAULT NULL COMMENT '主订单id',
  `amount` varchar(20) DEFAULT NULL COMMENT '数量',
  `createTime` bigint(20) DEFAULT '0',
  `money` varchar(255) DEFAULT NULL COMMENT '商品单价',
  `goodName` varchar(255) DEFAULT '' COMMENT '商品名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of ordersub
-- ----------------------------
INSERT INTO `ordersub` VALUES ('1', '100', '2', '2', '1581923479412', '20', '美甲商品1');
INSERT INTO `ordersub` VALUES ('2', '115', '2', '2', '1581923479412', '66', '美甲商品3');
INSERT INTO `ordersub` VALUES ('3', '100', '3', '2', '1581923993981', '20', '美甲商品1');
INSERT INTO `ordersub` VALUES ('4', '115', '3', '2', '1581923993981', '66', '美甲商品3');
INSERT INTO `ordersub` VALUES ('5', '100', '4', '2', '1581926942469', '20', '美甲商品1');
INSERT INTO `ordersub` VALUES ('6', '115', '4', '2', '1581926942469', '66', '美甲商品3');
INSERT INTO `ordersub` VALUES ('7', '100', '5', '1', '1581927031558', '20', '美甲商品1');
INSERT INTO `ordersub` VALUES ('8', '115', '5', '2', '1581927031558', '66', '美甲商品3');
INSERT INTO `ordersub` VALUES ('9', '100', '6', '2', '1581927130235', '20', '美甲商品1');
INSERT INTO `ordersub` VALUES ('10', '115', '6', '2', '1581927130235', '66', '美甲商品3');
INSERT INTO `ordersub` VALUES ('11', '100', '7', '1', '1581927240567', '20', '美甲商品1');
INSERT INTO `ordersub` VALUES ('12', '115', '7', '1', '1581927240567', '66', '美甲商品3');
INSERT INTO `ordersub` VALUES ('13', '100', '8', '2', '1581927455152', '20', '美甲商品1');
INSERT INTO `ordersub` VALUES ('14', '115', '8', '2', '1581927455152', '66', '美甲商品3');
INSERT INTO `ordersub` VALUES ('15', '100', '9', '3', '1581942232249', '20', '美甲商品1');
INSERT INTO `ordersub` VALUES ('16', '115', '9', '5', '1581942232249', '66', '美甲商品3');
INSERT INTO `ordersub` VALUES ('17', '100', '10', '1', '1581942429343', '20', '美甲商品1');
INSERT INTO `ordersub` VALUES ('18', '115', '10', '6', '1581942429343', '66', '美甲商品3');
INSERT INTO `ordersub` VALUES ('19', '100', '11', '2', '1581984817816', '20', '美甲商品1');
INSERT INTO `ordersub` VALUES ('20', '115', '11', '2', '1581984817816', '66', '美甲商品3');
INSERT INTO `ordersub` VALUES ('21', '100', '12', '2', '1581994855558', '20', '美甲商品1');
INSERT INTO `ordersub` VALUES ('22', '115', '12', '3', '1581994855558', '66', '美甲商品3');
INSERT INTO `ordersub` VALUES ('23', '100', '13', '2', '1582002408312', '20', '美甲商品1');
INSERT INTO `ordersub` VALUES ('24', '115', '13', '3', '1582002408312', '66', '美甲商品3');
INSERT INTO `ordersub` VALUES ('25', '100', '14', '1', '1582014592058', '20', '美甲商品1');
INSERT INTO `ordersub` VALUES ('26', '115', '14', '2', '1582014592058', '66', '美甲商品3');
INSERT INTO `ordersub` VALUES ('27', '116', '15', '1', '1582014744414', '66', '美甲商品4');
INSERT INTO `ordersub` VALUES ('28', '117', '15', '3', '1582014744414', '777', '美甲商品6');

-- ----------------------------
-- Table structure for store
-- ----------------------------
DROP TABLE IF EXISTS `store`;
CREATE TABLE `store` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT '' COMMENT '店铺名称',
  `remark` varchar(200) DEFAULT '' COMMENT '描述',
  `createTime` bigint(20) DEFAULT '0' COMMENT '创建时间',
  `photo` varchar(255) DEFAULT '' COMMENT '图片',
  `isActive` int(11) DEFAULT '1' COMMENT '1 有效 2 无效',
  `address` varchar(255) DEFAULT '' COMMENT '地址',
  `latitude` varchar(255) DEFAULT '' COMMENT '纬度',
  `longitude` varchar(255) DEFAULT '' COMMENT '经度',
  `phone` varchar(255) DEFAULT '' COMMENT '手机号码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of store
-- ----------------------------
INSERT INTO `store` VALUES ('1', '美甲一号店', '美甲，美眉', '1581843780866', 'http://localhost:8088/b858e6e16e7349ac9ed852fad298aa14.jpg', '1', '上海市黄浦区泰康路200-1号', '31.207791', '121.469446', '021-9999999');
INSERT INTO `store` VALUES ('2', '美甲二号店', '修眉，代购', '1581907650999', 'http://localhost:8088/8ec41e24d6824558baf0256abb73caf8.jpg', '1', '上海市黄浦区复兴中路597号', '31.213640', '121.461770', '021-888888');
INSERT INTO `store` VALUES ('3', '美甲三号店', '专业美甲师', '1581844016381', 'http://localhost:8088/aa31578ab9064f089219733bb3a8c795.jpg', '1', '上海市徐汇区梅陇路130号', '31.143297', '121.424203', '021-7777777');
INSERT INTO `store` VALUES ('7', '美甲四号店', '美肤美甲', '1582014365258', 'http://localhost:8088/dd91d5b8319d401fba9e01bf53f0f85e.jpg', '1', '上海市浦东新区房地产交易中心', '31.190430', '121.702870', '021-66666666');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `loginName` varchar(20) DEFAULT NULL,
  `code` varchar(50) DEFAULT '' COMMENT 'openId',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `age` varchar(50) DEFAULT '',
  `sex` varchar(20) DEFAULT NULL,
  `photo` varchar(255) DEFAULT '' COMMENT '头像',
  `isActive` int(11) DEFAULT '1' COMMENT '1 有效 2 无效',
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '用户5', '1', '3', '1', '1552981269698', '20', '女', '677fc173434741568a127fc2a27c535c.png', '1', '北京王府井天安门小区');
INSERT INTO `user` VALUES ('2', '用户名', '1', 'admin', '1', '1581833561344', '20', '男', '6fa03224dae74a328daff6a73d5bb965.png', '1', '北京王府井天安门小区');
INSERT INTO `user` VALUES ('3', '用户2', '1', '2', '1', '1552405341525', '20', '男', '677fc173434741568a127fc2a27c535c.png', '1', '北京王府井天安门小区');
INSERT INTO `user` VALUES ('4', '用户3', '1', '1', '1', '1582081934778', '20', '男', 'http://localhost:8088/470b522cb84544ada3eca8e37c1b0598.jpg', '1', '北京王府井天安门小区');
INSERT INTO `user` VALUES ('91', '用户8', '1', '4', '1', '1552195986675', '20', '女', '677fc173434741568a127fc2a27c535c.png', '1', '北京王府井天安门小区');
INSERT INTO `user` VALUES ('93', '用户3', '1', '5', '1', '1552131460655', '20', '女', '677fc173434741568a127fc2a27c535c.png', '1', '北京王府井天安门小区');
INSERT INTO `user` VALUES ('97', '用户4', '1', '6', '1', '1552195530762', '0', '女', '677fc173434741568a127fc2a27c535c.png', '1', '北京王府井天安门小区');
INSERT INTO `user` VALUES ('114', '文', '111111', '文', '', null, '', null, '', '1', null);
