CREATE TABLE `review`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `company_id`  int(11)      NOT NULL,
    `created_at`  DATETIME     NOT NULL,
    `title`       varchar(100) NOT NULL,
    `description` TEXT         NULL,
    `rating`      FLOAT        NOT NULL,
    PRIMARY KEY (`id`),
    KEY           `review_company_idx_01` (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;