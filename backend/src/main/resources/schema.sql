/*
 Navicat Premium Data Transfer

 Source Server         : 台式机
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : 192.168.10.103:12345
 Source Schema         : graduate_manage

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 10/05/2021 17:14:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for announce
-- ----------------------------
DROP TABLE IF EXISTS `announce`;
CREATE TABLE `announce`
(
    `id`          int(0)                                                        NOT NULL AUTO_INCREMENT,
    `content`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `to_ids`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `title`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `create_time` datetime(3)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    `update_time` datetime(3)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `delete_time` datetime(3)                                                   NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`
(
    `id`          int(0)                                                         NOT NULL AUTO_INCREMENT,
    `title`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL,
    `author`      varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL,
    `summary`     varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `image`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL,
    `create_time` datetime(3)                                                    NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `update_time` datetime(3)                                                    NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    `delete_time` datetime(3)                                                    NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for card
-- ----------------------------
DROP TABLE IF EXISTS `card`;
CREATE TABLE `card`
(
    `id`           int(0)                                                        NOT NULL AUTO_INCREMENT,
    `question_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `title`        varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '名称',
    `create_time`  datetime(3)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    `update_time`  datetime(3)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `delete_time`  datetime(3)                                                   NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for class
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class`
(
    `id`          int(0)                                                       NOT NULL AUTO_INCREMENT,
    `name`        varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '专业名称',
    `college_id`  int(0)                                                       NOT NULL COMMENT '所属学院ID',
    `major_id`    int(0)                                                       NOT NULL COMMENT '所属专业ID',
    `create_time` datetime(3)                                                  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    `update_time` datetime(3)                                                  NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `delete_time` datetime(3)                                                  NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 16
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for college
-- ----------------------------
DROP TABLE IF EXISTS `college`;
CREATE TABLE `college`
(
    `id`          int(0)                                                       NOT NULL AUTO_INCREMENT,
    `name`        varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '学院名称',
    `create_time` datetime(3)                                                  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    `update_time` datetime(3)                                                  NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `delete_time` datetime(3)                                                  NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 14
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for graduate_info
-- ----------------------------
DROP TABLE IF EXISTS `graduate_info`;
CREATE TABLE `graduate_info`
(
    `id`          int(0)                                                        NOT NULL AUTO_INCREMENT,
    `title`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `teacher_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `year`        varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL,
    `delete_time` datetime(3)                                                   NULL     DEFAULT NULL,
    `update_time` datetime(3)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `create_time` datetime(3)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for lin_file
-- ----------------------------
DROP TABLE IF EXISTS `lin_file`;
CREATE TABLE `lin_file`
(
    `id`          int(0) UNSIGNED                                               NOT NULL AUTO_INCREMENT,
    `path`        varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `type`        varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT 'LOCAL' COMMENT 'LOCAL 本地，REMOTE 远程',
    `name`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `extension`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL,
    `size`        int(0)                                                        NULL     DEFAULT NULL,
    `md5`         varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT 'md5值，防止上传重复文件',
    `create_time` datetime(3)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `update_time` datetime(3)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    `delete_time` datetime(3)                                                   NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `md5_del` (`md5`, `delete_time`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for lin_group
-- ----------------------------
DROP TABLE IF EXISTS `lin_group`;
CREATE TABLE `lin_group`
(
    `id`          int(0) UNSIGNED                                               NOT NULL AUTO_INCREMENT,
    `name`        varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '分组名称，例如：搬砖者',
    `info`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '分组信息：例如：搬砖的人',
    `level`       tinyint(0)                                                    NOT NULL DEFAULT 3 COMMENT '分组级别 1：root 2：guest 3：user  root（root、guest分组只能存在一个)',
    `create_time` datetime(3)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `update_time` datetime(3)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    `delete_time` datetime(3)                                                   NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `name_del` (`name`, `delete_time`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 8
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for lin_group_permission
-- ----------------------------
DROP TABLE IF EXISTS `lin_group_permission`;
CREATE TABLE `lin_group_permission`
(
    `id`            int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
    `group_id`      int(0) UNSIGNED NOT NULL COMMENT '分组id',
    `permission_id` int(0) UNSIGNED NOT NULL COMMENT '权限id',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `group_id_permission_id` (`group_id`, `permission_id`) USING BTREE COMMENT '联合索引'
) ENGINE = InnoDB
  AUTO_INCREMENT = 50
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for lin_log
-- ----------------------------
DROP TABLE IF EXISTS `lin_log`;
CREATE TABLE `lin_log`
(
    `id`          int(0) UNSIGNED                                               NOT NULL AUTO_INCREMENT,
    `message`     varchar(450) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `user_id`     int(0) UNSIGNED                                               NOT NULL,
    `username`    varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL,
    `status_code` int(0)                                                        NULL     DEFAULT NULL,
    `method`      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL,
    `path`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL,
    `permission`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `create_time` datetime(3)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `update_time` datetime(3)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    `delete_time` datetime(3)                                                   NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for lin_permission
-- ----------------------------
DROP TABLE IF EXISTS `lin_permission`;
CREATE TABLE `lin_permission`
(
    `id`          int(0) UNSIGNED                                              NOT NULL AUTO_INCREMENT,
    `name`        varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限名称，例如：访问首页',
    `module`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限所属模块，例如：人员管理',
    `mount`       tinyint(1)                                                   NOT NULL DEFAULT 1 COMMENT '0：关闭 1：开启',
    `create_time` datetime(3)                                                  NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `update_time` datetime(3)                                                  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    `delete_time` datetime(3)                                                  NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 39
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for lin_user
-- ----------------------------
DROP TABLE IF EXISTS `lin_user`;
CREATE TABLE `lin_user`
(
    `id`          int(0) UNSIGNED                                               NOT NULL AUTO_INCREMENT,
    `username`    varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '用户名，唯一',
    `nickname`    varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '用户昵称',
    `avatar`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '头像url',
    `email`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '邮箱',
    `create_time` datetime(3)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `update_time` datetime(3)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    `delete_time` datetime(3)                                                   NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `username_del` (`username`, `delete_time`) USING BTREE,
    UNIQUE INDEX `email_del` (`email`, `delete_time`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 30
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for lin_user_group
-- ----------------------------
DROP TABLE IF EXISTS `lin_user_group`;
CREATE TABLE `lin_user_group`
(
    `id`       int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
    `user_id`  int(0) UNSIGNED NOT NULL COMMENT '用户id',
    `group_id` int(0) UNSIGNED NOT NULL COMMENT '分组id',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `user_id_group_id` (`user_id`, `group_id`) USING BTREE COMMENT '联合索引'
) ENGINE = InnoDB
  AUTO_INCREMENT = 35
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for lin_user_identity
-- ----------------------------
DROP TABLE IF EXISTS `lin_user_identity`;
CREATE TABLE `lin_user_identity`
(
    `id`            int(0) UNSIGNED                                               NOT NULL AUTO_INCREMENT,
    `user_id`       int(0) UNSIGNED                                               NOT NULL COMMENT '用户id',
    `identity_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `identifier`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `credential`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `create_time`   datetime(3)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `update_time`   datetime(3)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    `delete_time`   datetime(3)                                                   NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 30
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for major
-- ----------------------------
DROP TABLE IF EXISTS `major`;
CREATE TABLE `major`
(
    `id`          int(0)                                                       NOT NULL AUTO_INCREMENT,
    `name`        varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '专业名称',
    `college_id`  int(0)                                                       NOT NULL COMMENT '所属学院ID',
    `create_time` datetime(3)                                                  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    `update_time` datetime(3)                                                  NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `delete_time` datetime(3)                                                  NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 24
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for option
-- ----------------------------
DROP TABLE IF EXISTS `option`;
CREATE TABLE `option`
(
    `id`          int(0)                                                        NOT NULL AUTO_INCREMENT,
    `content`     varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '内容',
    `score`       int(0)                                                        NOT NULL,
    `create_time` datetime(3)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    `update_time` datetime(3)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `delete_time` datetime(3)                                                   NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 73
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`
(
    `id`          int(0)                                                        NOT NULL AUTO_INCREMENT,
    `title`       varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '标题',
    `option_ids`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `create_time` datetime(3)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    `update_time` datetime(3)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `delete_time` datetime(3)                                                   NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 19
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for scorecard
-- ----------------------------
DROP TABLE IF EXISTS `scorecard`;
CREATE TABLE `scorecard`
(
    `id`            int(0)                                                        NOT NULL AUTO_INCREMENT,
    `title`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '评分表标题',
    `comment`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '评分表留言',
    `level`         varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '优秀、良好、中等、及格和不及格',
    `score`         double                                                        NULL     DEFAULT NULL COMMENT '得分',
    `guide_card_id` int(0)                                                        NOT NULL COMMENT '指导老师评分表模板id',
    `judge_advise`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '评委团队建议',
    `judge_score`   double                                                        NULL     DEFAULT NULL COMMENT '评委团队评分',
    `judge_answers` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `guide_answers` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `guide_advise`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '指导老师建议',
    `guide_score`   double                                                        NULL     DEFAULT NULL COMMENT '指导老师评分',
    `judge_card_id` int(0)                                                        NULL     DEFAULT NULL COMMENT '答辩小组评分表模板id',
    `supplement`    longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NULL,
    `topic_id`      int(0)                                                        NULL     DEFAULT NULL,
    `create_time`   datetime(3)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    `update_time`   datetime(3)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `delete_time`   datetime(3)                                                   NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 38
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`
(
    `id`          int(0)                                                       NOT NULL AUTO_INCREMENT,
    `sid`         varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '学号',
    `class_id`    int(0)                                                       NOT NULL COMMENT '班级号',
    `name`        varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
    `sex`         int(0)                                                       NULL     DEFAULT NULL COMMENT '性别 1:男 0:女',
    `email`       varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '邮箱',
    `mobile`      varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '手机号',
    `create_time` datetime(3)                                                  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    `update_time` datetime(3)                                                  NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `delete_time` datetime(3)                                                  NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 25
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`
(
    `id`          int(0)                                                        NOT NULL AUTO_INCREMENT,
    `tid`         varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL COMMENT '老师工号',
    `name`        varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '姓名',
    `sex`         int(0)                                                        NULL     DEFAULT NULL COMMENT '性别 1:男 0:女',
    `college_id`  int(0)                                                        NULL     DEFAULT NULL COMMENT '学院ID',
    `position`    varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '职位',
    `email`       varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '邮箱',
    `mobile`      varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '手机号',
    `summary`     varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '简介',
    `create_time` datetime(3)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    `update_time` datetime(3)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `delete_time` datetime(3)                                                   NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for team
-- ----------------------------
DROP TABLE IF EXISTS `team`;
CREATE TABLE `team`
(
    `id`          int(0)                                                         NOT NULL AUTO_INCREMENT,
    `name`        varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL COMMENT '团队名',
    `teacher_id`  int(0)                                                         NULL     DEFAULT NULL COMMENT '负责人id',
    `judges`      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '评委团队名称',
    `student_ids` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '成员学生id',
    `years`       varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci    NULL     DEFAULT NULL COMMENT '届数',
    `college_id`  int(0)                                                         NULL     DEFAULT NULL COMMENT '所属学院',
    `create_time` datetime(3)                                                    NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    `update_time` datetime(3)                                                    NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `delete_time` datetime(3)                                                    NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 15
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for topic
-- ----------------------------
DROP TABLE IF EXISTS `topic`;
CREATE TABLE `topic`
(
    `id`               int(0)                                                        NOT NULL AUTO_INCREMENT,
    `name`             varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
    `description`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '描述',
    `type`             int(0)                                                        NULL     DEFAULT NULL COMMENT '0:为老师发布 1:学生自主申请',
    `publish_year`     varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '发布年份',
    `student_id`       varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '学生学号',
    `teacher_id`       int(0)                                                        NULL     DEFAULT NULL COMMENT '老师id',
    `college_id`       int(0)                                                        NULL     DEFAULT NULL COMMENT '所属学院',
    `graduate_info_id` int(0)                                                        NULL     DEFAULT NULL,
    `status`           int(0)                                                        NULL     DEFAULT 0 COMMENT '课题状态',
    `create_time`      datetime(3)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    `update_time`      datetime(3)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `delete_time`      datetime(3)                                                   NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 28
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC;

INSERT INTO book(`title`, `author`, `summary`, `image`)
VALUES ('深入理解计算机系统', 'Randal E.Bryant',
        '从程序员的视角，看计算机系统！\n本书适用于那些想要写出更快、更可靠程序的程序员。通过掌握程序是如何映射到系统上，以及程序是如何执行的，读者能够更好的理解程序的行为为什么是这样的，以及效率低下是如何造成的。',
        'https://img3.doubanio.com/lpic/s1470003.jpg');
INSERT INTO book(`title`, `author`, `summary`, `image`)
VALUES ('C程序设计语言', '（美）Brian W. Kernighan',
        '在计算机发展的历史上，没有哪一种程序设计语言像C语言这样应用广泛。本书原著即为C语言的设计者之一Dennis M.Ritchie和著名计算机科学家Brian W.Kernighan合著的一本介绍C语言的权威经典著作。',
        'https://img3.doubanio.com/lpic/s1106934.jpg');

-- ----------------------------
-- 插入超级管理员
-- 插入root分组
-- ----------------------------
BEGIN;

INSERT INTO lin_user(id, username, nickname)
VALUES (1, 'root', 'root');

INSERT INTO lin_user_identity (id, user_id, identity_type, identifier, credential)
VALUES (1, 1, 'USERNAME_PASSWORD', 'root',
        'pbkdf2sha256:64000:18:24:n:yUnDokcNRbwILZllmUOItIyo9MnI00QW:6ZcPf+sfzyoygOU8h/GSoirF');

INSERT INTO lin_group(id, name, info, level)
VALUES (1, 'root', '超级用户组', 1);

INSERT INTO lin_group(id, name, info, level)
VALUES (2, 'guest', '游客组', 2);

INSERT INTO lin_user_group(id, user_id, group_id)
VALUES (1, 1, 1);

COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
