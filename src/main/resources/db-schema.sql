-- DROP SCHEMA if exists `dbtest`;
-- CREATE SCHEMA `dbtest`;

DROP TABLE if exists `employee` ;
DROP TABLE if exists `department` ;

CREATE TABLE `department` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Department` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`));
-- ENGINE 'InnoDB';
-- DEFAULT CHARACTER SET = utf8;

CREATE TABLE `employee` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `ID_Department` INT NOT NULL,
  `FullName` VARCHAR(45) NOT NULL,
  `BirthDate` DATE NOT NULL,
  `Salary` INT NULL,
  PRIMARY KEY (`ID`),
  --   INDEX `ID_Department_idx` (`ID_Department` ASC),
  CONSTRAINT `ID_Department`
  FOREIGN KEY (`ID_Department`)
  REFERENCES `department` (`ID`)
  ON DELETE CASCADE
  ON UPDATE CASCADE);
-- ENGINE 'InnoDB';
-- DEFAULT CHARACTER SET = utf8;