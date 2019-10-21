DROP DATABASE IF EXISTS exerciseBelatrix;
CREATE DATABASE IF NOT EXISTS exerciseBelatrix;
USE exerciseBelatrix;

CREATE TABLE IF NOT EXISTS `logsApp` (
	`id` INT(4) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'The log Id',
	`type` INT(4) NOT NULL COMMENT 'The log type',
	`message` VARCHAR(255) NOT NULL COMMENT 'The log message'	
) ENGINE = InnoDB AUTO_INCREMENT = 1;
