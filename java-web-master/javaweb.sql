/*
 Navicat Premium Data Transfer

 Source Server         : Mysql57
 Source Server Type    : MySQL
 Source Server Version : 50737
 Source Host           : localhost:3306
 Source Schema         : javaweb

 Target Server Type    : MySQL
 Target Server Version : 50737
 File Encoding         : 65001


*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `course_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '景点名',
  `course_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '景点代码',
  `begin` int(2) NULL DEFAULT NULL COMMENT '开始',
  `end` int(2) NULL DEFAULT NULL COMMENT '截至',
  `status` int(1) NULL DEFAULT 1 COMMENT '是否可以购票',
  PRIMARY KEY (`id`, `course_code`) USING BTREE,
  UNIQUE INDEX `course_course_code_uindex`(`course_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (1, '故宫', 'A1234567891', 1, 7, 0);
INSERT INTO `course` VALUES (2, '长城', 'A1234567892', 2, 5, 1);
INSERT INTO `course` VALUES (3, '北海公园', 'A1523665820', 3, 7, 1);
INSERT INTO `course` VALUES (4, '颐和园', 'A1774457696', 4, 6, 1);
INSERT INTO `course` VALUES (5, '香山', 'A1234569872', 1, 7, 1);

-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '地点id',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地点名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of room
-- ----------------------------
INSERT INTO `room` VALUES (1, '南1101');
INSERT INTO `room` VALUES (2, '南1102');
INSERT INTO `room` VALUES (3, '南1103');
INSERT INTO `room` VALUES (4, '南1201');
INSERT INTO `room` VALUES (5, '南1202');
INSERT INTO `room` VALUES (6, '南1203');
INSERT INTO `room` VALUES (7, '中1101');
INSERT INTO `room` VALUES (8, '中1102');
INSERT INTO `room` VALUES (9, '中1103');
INSERT INTO `room` VALUES (10, '北1101');
INSERT INTO `room` VALUES (11, '北1102');
INSERT INTO `room` VALUES (12, '北1103');
INSERT INTO `room` VALUES (13, '北1104');
INSERT INTO `room` VALUES (14, '北1105');
INSERT INTO `room` VALUES (15, '北1106');

-- ----------------------------
-- Table structure for search
-- ----------------------------
DROP TABLE IF EXISTS `search`;
CREATE TABLE `search`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `student_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户号',
  `score` int(3) NULL DEFAULT -1 COMMENT '价格',
  `teach_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `search_teach_id_fk`(`teach_id`) USING BTREE,
  INDEX `search_user_username_fk`(`student_id`) USING BTREE,
  CONSTRAINT `search_teach_id_fk` FOREIGN KEY (`teach_id`) REFERENCES `teach` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `search_user_username_fk` FOREIGN KEY (`student_id`) REFERENCES `user` (`username`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of search
-- ----------------------------
INSERT INTO `search` VALUES (2, '201924100709', -1, 3);
INSERT INTO `search` VALUES (3, '201924100709', 80, 1);
INSERT INTO `search` VALUES (4, '202024100903', 75, 1);
INSERT INTO `search` VALUES (5, '202024100904', 58, 1);


-- ----------------------------
-- Table structure for teach
-- ----------------------------
DROP TABLE IF EXISTS `teach`;
CREATE TABLE `teach`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `teacher_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职工号',
  `course_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '景点代码',
  `day` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '周几',
  `time` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '进入时间',
  `room_id` int(11) NULL DEFAULT NULL COMMENT '地点编号',
  `now` int(11) NULL DEFAULT 0 COMMENT '现购人数',
  `max` int(11) NULL DEFAULT NULL COMMENT '限购人数',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `teach_room_id_fk`(`room_id`) USING BTREE,
  INDEX `teach_user_username_fk`(`teacher_id`) USING BTREE,
  INDEX `teach_course_course_code_fk`(`course_code`) USING BTREE,
  CONSTRAINT `teach_course_course_code_fk` FOREIGN KEY (`course_code`) REFERENCES `course` (`course_code`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `teach_room_id_fk` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `teach_user_username_fk` FOREIGN KEY (`teacher_id`) REFERENCES `user` (`username`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teach
-- ----------------------------
INSERT INTO `teach` VALUES (1, '20200801', 'A1234567891', '周一', '10:10-11:50', 2, 5, 30);
INSERT INTO `teach` VALUES (2, '20200801', 'A1234567892', '周四', '10:10-11:50', 3, 1, 45);
INSERT INTO `teach` VALUES (3, '20200802', 'A1234567890', '周五', '2:00-3:40', 7, 1, 50);
INSERT INTO `teach` VALUES (4, '20200804', 'B1306658653', '周二', '4:00-5:40', 5, 1, 20);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `sex` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
  `person_number` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `role` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '用户' COMMENT '角色',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`, `username`) USING BTREE,
  UNIQUE INDEX `user_username_uindex`(`username`) USING BTREE,
  UNIQUE INDEX `user_person_number_uindex`(`person_number`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'root', '161be98664d73f70db4932b08b32a872', '超级管理员', '男', '350303200502130019', '13000000000', '2430305938@qq.com', 'http://localhost:9090/files/703ccbe2a3c7426aa577227433e11bb0', '管理员', '2025-04-23 13:21:51', '2025-05-13 11:31:51');
INSERT INTO `user` VALUES (2, '202321034501', '161be98664d73f70db4932b08b32a872', '张三', ' 男', '350303200502130020', '13000000000', '2430305938@qq.com', NULL, '用户', '2025-04-23 15:25:51', '2025-05-23 21:25:30');
INSERT INTO `user` VALUES (17, '202321034502', '161be98664d73f70db4932b08b32a872', '李四', '男', '350303200502130021', '13000000000', '2430305938@qq.com', NULL, '商家', '2025-05-15 16:12:59', NULL);


SET FOREIGN_KEY_CHECKS = 1;