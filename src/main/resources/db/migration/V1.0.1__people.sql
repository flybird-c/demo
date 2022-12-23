drop table if exists people;
CREATE TABLE `people` (
                          `people_id` int NOT NULL AUTO_INCREMENT,
                          `first_name` varchar(255) DEFAULT NULL,
                          `last_name` varchar(255) DEFAULT NULL,
                          PRIMARY KEY (`people_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
