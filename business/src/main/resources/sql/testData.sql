SET NAMES utf8mb4;
SET
    FOREIGN_KEY_CHECKS = 0;

CREATE
    DATABASE IF NOT EXISTS COLLECT;
use
    COLLECT;

-- ———————————————————————————————————————————————— INSERT ————————————————————————————————————————————————


-- ----------------------------
-- Records of user_info
-- 这里的用户密码均是「123456」
-- 只插入了 admin 和 employee
-- ----------------------------

INSERT INTO `user_info` VALUES (1, 'root', '123456@qq.com', '$2a$10$pcYq0hFxxpKBzPIDlxy1Yesov7EcT4aQZsXTsrkAcUyJRm1hvFJuC', 0, '2020-12-12 11:11:11');
INSERT INTO `user_info` VALUES (2, 'boss', '888@qq.com', '$2a$10$pcYq0hFxxpKBzPIDlxy1Yesov7EcT4aQZsXTsrkAcUyJRm1hvFJuC', 2, '2020-12-12 11:11:11');


-- ----------------------------
-- Records of task
-- ----------------------------

INSERT INTO `task` VALUES (1, 2, 'task1', 'test the app, FUNCTIONAL_TEST', '2022-01-26 00:00:00', '2022-11-11 00:00:00', 0, 0, 4, 7, 0, 0, 'https://cdn.jsdelivr.net/npm/rikka-os@1.0.3/img/cover/rikka_1.webp', 'https://cdn.jsdelivr.net/npm/rikka-os@1.0.3/img/cover/rikka_1.webp');
INSERT INTO `task` VALUES (2, 2, 'task2', 'test the login function, PERFORMANCE_TEST', '2022-01-27 00:00:00', '2022-09-20 00:00:00', 1, 1, 3, 17, 0, 0, 'https://cdn.jsdelivr.net/npm/rikka-os@1.0.3/img/cover/rikka_1.webp', 'https://cdn.jsdelivr.net/npm/rikka-os@1.0.3/img/cover/rikka_1.webp');
INSERT INTO `task` VALUES (3, 2, 'task3', 'test the register function', '2022-01-28 00:00:00', '2022-10-20 00:00:00', 2, 2, 2, 70, 0, 0, 'https://cdn.jsdelivr.net/npm/rikka-os@1.0.3/img/cover/rikka_1.webp', 'https://cdn.jsdelivr.net/npm/rikka-os@1.0.3/img/cover/rikka_1.webp');
INSERT INTO `task` VALUES (4, 2, 'task4', 'test the main menu function', '2022-01-29 00:00:00', '2022-11-20 00:00:00', 3, 0, 1, 99, 0, 0, 'https://cdn.jsdelivr.net/npm/rikka-os@1.0.3/img/cover/rikka_1.webp', 'https://cdn.jsdelivr.net/npm/rikka-os@1.0.3/img/cover/rikka_1.webp');
INSERT INTO `task` VALUES (5, 2, 'task5', 'test the performance', '2022-01-11 00:00:00', '2022-12-20 00:00:00', 4, 1, 5, 27, 0, 0, 'https://cdn.jsdelivr.net/npm/rikka-os@1.0.3/img/cover/rikka_1.webp', 'https://cdn.jsdelivr.net/npm/rikka-os@1.0.3/img/cover/rikka_1.webp');
INSERT INTO `task` VALUES (6, 2, 'task6', 'test the performance', '2022-01-11 00:00:00', '2022-12-20 00:00:00', 5, 2, 7, 77, 0, 0, 'https://cdn.jsdelivr.net/npm/rikka-os@1.0.3/img/cover/rikka_1.webp', 'https://cdn.jsdelivr.net/npm/rikka-os@1.0.3/img/cover/rikka_1.webp');








