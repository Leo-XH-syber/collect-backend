create TABLE `user_info`
(
    `id`          int(11)                                                       NOT NULL AUTO_INCREMENT,
    `uname`       varchar(255)  NOT NULL,
    `email`       varchar(255) NOT NULL,
    `password`    varchar(255)  NOT NULL,
    `user_role`   int(11)                                                       NOT NULL,
    `create_time` timestamp                                                    NOT NULL,
    PRIMARY KEY (`id`)
);
create TABLE `worker_property`
(
    `worker_id`             int(11) NOT NULL,
    `ability`               double                                  DEFAULT NULL,
    `activity`              int                                     DEFAULT 0,
    `default_os`            int                                     DEFAULT NULL,
    `preference`            varchar(255) DEFAULT NULL,
    `cooperation_ability`   double                                  DEFAULT NULL,
    `criticism_ability`     double                                  DEFAULT NULL,
    `report_avg_criticism`  double                                  DEFAULT NULL,
    `avg_repeatability`     double                                  DEFAULT NULL,
    `task_count`            int                                     DEFAULT NULL,
    `history_bug_count`     int                                     DEFAULT NULL,
    `bug_report_proportion` double                                  DEFAULT NULL,
    `duplicate_index`       double                                  DEFAULT NULL,

    PRIMARY KEY (`worker_id`)
);
create TABLE `task`
(
    `id`              int(11)                                                       NOT NULL AUTO_INCREMENT,
    `employer_id`     int(11)                                                       NOT NULL,
    `task_name`       varchar(255)  NOT NULL,
    `introduction`    text         NOT NULL,
    `start_time`      datetime                                                      NOT NULL,
    `end_time`        datetime                                                      NOT NULL,
    `task_kind`       int(11)                                                       NOT NULL,
    `task_difficulty` int(11)                                                       NOT NULL,
    `task_os`         int(11)                                                       NOT NULL,
    `need_workers`    int(11)                                                       NOT NULL,
    `has_workers`     int(11)                                                       NOT NULL,
    `task_state`      int(11)                                                       NOT NULL,
    `test_app`        varchar(255)  NULL DEFAULT NULL,
    `test_doc`        varchar(255)  NULL DEFAULT NULL,

    PRIMARY KEY (`id`)
) ;
CREATE TABLE `order`
(
    `id`          int      NOT NULL AUTO_INCREMENT,
    `worker_id`   int      NOT NULL,
    `task_id`     int      NOT NULL,
    `select_time` datetime NOT NULL,
    `order_state` int      NOT NULL,
    PRIMARY KEY (`id`) ,
    UNIQUE KEY `workerIdAndTaskId` (`worker_id`, `task_id`)
);

create TABLE `report`
(
    `id`            int(11)                                                       NOT NULL AUTO_INCREMENT,
    `worker_id`     int(11)                                                       NOT NULL,
    `task_id`       int(11)                                                       NOT NULL,
    `create_time`   datetime                                                      NOT NULL,
    `description`   text        NOT NULL,
    `recovery_step` text       NOT NULL,
    `screenshot`    text        NOT NULL,
    `device_os`     int(11)                                                       NOT NULL,
    `device_type`   varchar(255)  NULL     DEFAULT NULL,
    `bug_report`    int(11)                                                       NOT NULL DEFAULT 0,
    `BUG_ID`        int(11)                                                       NULL     DEFAULT NULL,
    PRIMARY KEY (`id`)
);

create TABLE `report_criticism`
(
    `id`          int(11)                                               NOT NULL AUTO_INCREMENT,
    `report_id`   int(11)                                               NOT NULL,
    `worker_id`   int(11)                                               NOT NULL,
    `create_time` datetime                                              NOT NULL,
    `score`       int(11)                                               NOT NULL,
    `comments`    text  NOT NULL,

    PRIMARY KEY (`id`)
);
create TABLE `cooperation_report`
(
    `id`                int(11)                                                       NOT NULL AUTO_INCREMENT,
    `co_worker_id`      int(11)                                                       NOT NULL,
    `origin_report_id`  int(11)                                                       NOT NULL,
    `task_id`           int(11)                                                       NOT NULL,
    `create_time`       datetime                                                      NOT NULL,
    `description`       text        NOT NULL,
    `recovery_step`     text        NOT NULL,
    `screenshot`        text        NOT NULL,
    `device_os`         int(11)                                                       NOT NULL,
    `score_from_author` int(11)                                                       NOT NULL,
    `device_type`       varchar(255)  NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
);
