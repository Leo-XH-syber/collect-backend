SET NAMES utf8mb4;
SET
    FOREIGN_KEY_CHECKS = 0;

CREATE
    DATABASE IF NOT EXISTS COLLECT;
use
    COLLECT;


-- ————————————————
-- COMMENT：备注
-- ENGINE = InnoDB：使用InnoBD数据库引擎
-- AUTO_INCREMENT = x: 表示插入的记录将从x开始而不是默认值1
-- CHARACTER SET = utf8mb4：permission 表使用的编码是utf8mb4（UTF-8）
-- COLLATE = utf8mb4_general_ci：指定排序的编码规则utf8mb4_general_ci，utf8mb4编码的默认值为utf8mb4_general_ci
-- ROW_FORMAT = Dynamic：指定当前表使用的行记录结构类型
-- ————————————————


-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
create TABLE `user_info`
(
    `id`          int(11)                                                       NOT NULL AUTO_INCREMENT,
    `uname`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `email`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `password`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `user_role`   int(11)                                                       NOT NULL,
    `create_time` datetime                                                      NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) COMMENT = '0=ADMIN, 1=EMPLOYER, 2=WORKER'
    ENGINE = InnoDB
    AUTO_INCREMENT = 1
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci
    ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for worker_preference
-- ----------------------------
DROP TABLE IF EXISTS `worker_property`;
create TABLE `worker_property`
(
    `worker_id`             int(11) NOT NULL,
    `ability`               double                                  DEFAULT 0,
    `activity`              int                                     DEFAULT 0,
    `default_os`            int                                     DEFAULT NULL,
    `preference`            varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `cooperation_ability`   double                                  DEFAULT 0,
    `criticism_ability`     double                                  DEFAULT 0,
    `report_avg_criticism`  double                                  DEFAULT 0,
    `avg_repeatability`     double                                  DEFAULT 0,
    `task_count`            int                                     DEFAULT 0,
    `history_bug_count`     int                                     DEFAULT 0,
    `bug_report_proportion` double                                  DEFAULT 0,
    `duplicate_index`       double                                  DEFAULT 0,

    PRIMARY KEY (`worker_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
create TABLE `task`
(
    `id`              int(11)                                                       NOT NULL AUTO_INCREMENT,
    `employer_id`     int(11)                                                       NOT NULL,
    `task_name`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `introduction`    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NOT NULL,
    `start_time`      datetime                                                      NOT NULL,
    `end_time`        datetime                                                      NOT NULL,
    `task_kind`       int(11)                                                       NOT NULL,
    `task_difficulty` int(11)                                                       NOT NULL,
    `task_os`         int(11)                                                       NOT NULL,
    `need_workers`    int(11)                                                       NOT NULL,
    `has_workers`     int(11)                                                       NOT NULL,
    `task_state`      int(11)                                                       NOT NULL,
    `test_app`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `test_doc`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,

    PRIMARY KEY (`id`) USING BTREE
) COMMENT =
    'task_state: 0=IN_PROCESS, 1=PERSON_FULL, 2=TIME_OVER
    task_kind: 0=FUNCTIONAL_TEST, 1=PERFORMANCE_TEST,'
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci
    ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`
(
    `id`          int      NOT NULL AUTO_INCREMENT,
    `worker_id`   int      NOT NULL,
    `task_id`     int      NOT NULL,
    `select_time` datetime NOT NULL,
    `order_state` int      NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `workerIdAndTaskId` (`worker_id`, `task_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='order_state: 0=IN_PROCESS, 1=FINISHED, 2=EXPIRED';


-- ----------------------------
-- Table structure for report
-- ----------------------------
DROP TABLE IF EXISTS `report`;
create TABLE `report`
(
    `id`            int(11)                                                       NOT NULL AUTO_INCREMENT,
    `worker_id`     int(11)                                                       NOT NULL,
    `task_id`       int(11)                                                       NOT NULL,
    `create_time`   datetime                                                      NOT NULL,
    `description`   text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NOT NULL,
    `recovery_step` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NOT NULL,
    `screenshot`    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NOT NULL,
    `device_os`     int(11)                                                       NOT NULL,
    `device_type`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `bug_report`    int(11)                                                       NOT NULL DEFAULT 0,
    `BUG_ID`        int(11)                                                       NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;



-- ----------------------------
-- Table structure for report_criticism
-- ----------------------------
DROP TABLE IF EXISTS `report_criticism`;
create TABLE `report_criticism`
(
    `id`          int(11)                                               NOT NULL AUTO_INCREMENT,
    `report_id`   int(11)                                               NOT NULL,
    `worker_id`   int(11)                                               NOT NULL,
    `create_time` datetime                                              NOT NULL,
    `score`       int(11)                                               NOT NULL,
    `comments`    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,

    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for cooperation_report
-- ----------------------------
DROP TABLE IF EXISTS `cooperation_report`;
create TABLE `cooperation_report`
(
    `id`                int(11)                                                       NOT NULL AUTO_INCREMENT,
    `co_worker_id`      int(11)                                                       NOT NULL,
    `origin_report_id`  int(11)                                                       NOT NULL,
    `task_id`           int(11)                                                       NOT NULL,
    `create_time`       datetime                                                      NOT NULL,
    `description`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NOT NULL,
    `recovery_step`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NOT NULL,
    `screenshot`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NOT NULL,
    `device_os`         int(11)                                                       NOT NULL,
    `score_from_author` int(11)                                                       NOT NULL DEFAULT 0,
    `device_type`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for bug_table
-- ----------------------------
DROP TABLE IF EXISTS `bug_table`;
create TABLE `bug_table`
(
    `id`       int(11)                                                       NOT NULL AUTO_INCREMENT,
    `task_id`  int(11)                                                       NOT NULL,
    `bug_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;


-- --------------------------END--------------------------


DROP PROCEDURE IF EXISTS `update_task_state`;
delimiter ;;
CREATE PROCEDURE `update_task_state`()
BEGIN

    SET @time = date_sub(now(), INTERVAL '1' DAY);
    DROP TEMPORARY TABLE
        IF
            EXISTS tmp_table;
    CREATE TEMPORARY TABLE tmp_table (SELECT id FROM task WHERE now() >= end_time and task_state = 0);
    UPDATE task
    SET task_state = 2
    WHERE id IN (SELECT * FROM tmp_table);
    UPDATE `order`
    SET order_state = 2
    WHERE task_id IN (SELECT * FROM tmp_table);
END
;;
delimiter ;

-- ----------------------------
-- Event structure for update_task_state
-- ----------------------------
DROP EVENT IF EXISTS `update_task_state`;
delimiter ;;
CREATE EVENT `update_task_state`
    ON SCHEDULE
        EVERY '1' DAY STARTS '2022-03-15 00:00:00'
    COMMENT '每天执行一次更新 task state 和 order state'
    DO CALL update_task_state()
;;
delimiter ;

