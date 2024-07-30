CREATE
DATABASE `companies-db`;

USE `companies-db`;

CREATE TABLE `company`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `name`        varchar(100) NOT NULL,
    `description` TEXT         NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE
DATABASE `jobs-db`;

USE `jobs-db`;

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

CREATE
DATABASE `reviews-db`;

USE `reviews-db`;

CREATE TABLE `review`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `company_id` int(11)      NOT NULL,
    `created_at`  DATETIME     NOT NULL,
    `title`       varchar(100) NOT NULL,
    `description` TEXT         NULL,
    `rating`      FLOAT        NOT NULL,
    PRIMARY KEY (`id`),
    KEY           `review_company_idx_01` (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;