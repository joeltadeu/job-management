CREATE TABLE `job`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `company_id`  int(11)      NOT NULL,
    `title`       varchar(100) NOT NULL,
    `description` TEXT         NULL,
    `min_salary`  int(11)      NOT NULL,
    `max_salary`  int(11)      NOT NULL,
    `location`    varchar(100) NOT NULL,
    PRIMARY KEY (`id`),
    KEY           `job_company_idx_01` (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;