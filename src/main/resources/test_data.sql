# CREATE SCHEMA `mydbtest` DEFAULT CHARACTER SET utf8 ;

DROP TABLE if exists `mydbtest`.`employee` ;
DROP TABLE if exists `mydbtest`.`department` ;

COMMIT;

CREATE TABLE `mydbtest`.`department` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Department` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

COMMIT;

INSERT INTO `mydbtest`.`department` (`ID`, `Department`) VALUES ('1', 'Отдел1');
INSERT INTO `mydbtest`.`department` (`ID`, `Department`) VALUES ('2', 'Отдел2');
INSERT INTO `mydbtest`.`department` (`ID`, `Department`) VALUES ('3', 'Отдел3');

COMMIT;

DROP TABLE if exists `mydbtest`.`employee` ;

COMMIT;

CREATE TABLE `mydbtest`.`employee` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `ID_Department` INT NOT NULL,
  `FullName` VARCHAR(45) NOT NULL,
  `BirthDate` DATE NOT NULL,
  `Salary` INT NULL,
  PRIMARY KEY (`ID`),
  INDEX `ID_Department_idx` (`ID_Department` ASC),
  CONSTRAINT `ID_Department`
  FOREIGN KEY (`ID_Department`)
  REFERENCES `mydbtest`.`department` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

COMMIT;

INSERT INTO `mydbtest`.`employee` (`ID`, `ID_Department`, `FullName`, `BirthDate`, `Salary`) VALUES ('1', '1', 'петров', '1999-08-11', '1992');
INSERT INTO `mydbtest`.`employee` (`ID`, `ID_Department`, `FullName`, `BirthDate`, `Salary`) VALUES ('2', '1', 'Иванов', '1981-06-01', '2000');
INSERT INTO `mydbtest`.`employee` (`ID`, `ID_Department`, `FullName`, `BirthDate`, `Salary`) VALUES ('3', '2', 'Сергеевиц', '1989-12-28', '34858');
INSERT INTO `mydbtest`.`employee` (`ID`, `ID_Department`, `FullName`, `BirthDate`, `Salary`) VALUES ('4', '3', 'Олегович', '1995-05-10', '1234');
INSERT INTO `mydbtest`.`employee` (`ID`, `ID_Department`, `FullName`, `BirthDate`, `Salary`) VALUES ('5', '2', 'Викторович', '1985-08-21', '9000');


COMMIT;