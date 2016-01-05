INSERT INTO `mydbtest`.`department` (`ID`, `Department`) VALUES ('1', 'Отдел1');
INSERT INTO `mydbtest`.`department` (`ID`, `Department`) VALUES ('2', 'Отдел2');
INSERT INTO `mydbtest`.`department` (`ID`, `Department`) VALUES ('3', 'Отдел3');

# COMMIT;

INSERT INTO `mydbtest`.`employee` (`ID`, `ID_Department`, `FIO`, `BirthDate`, `Salary`) VALUES ('1', '1', 'петров', '1999-08-11', '1992');
INSERT INTO `mydbtest`.`employee` (`ID`, `ID_Department`, `FIO`, `BirthDate`, `Salary`) VALUES ('2', '1', 'Иванов', '1981-06-01', '2000');
INSERT INTO `mydbtest`.`employee` (`ID`, `ID_Department`, `FIO`, `BirthDate`, `Salary`) VALUES ('3', '2', 'Сергеевиц', '1989-12-28', '34858');
INSERT INTO `mydbtest`.`employee` (`ID`, `ID_Department`, `FIO`, `BirthDate`, `Salary`) VALUES ('4', '3', 'Олегович', '1995-05-10', '1234');
INSERT INTO `mydbtest`.`employee` (`ID`, `ID_Department`, `FIO`, `BirthDate`, `Salary`) VALUES ('5', '2', 'Викторович', '1985-08-21', '9000');

# COMMIT;