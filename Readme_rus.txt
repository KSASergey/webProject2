Проект myProject

собирается в файл webProject.war из комадной строки:
собрать проект: mvn package
собрать без тестов: mvn -DskipTests=true package

готовый файл находится в папке target

Полученный war-файл залить в tomcat по пути:
...\apache-tomcat\webapps\

После запуска tomcat стартовая страница доступна по url:
http://localhost:8080/myProject/table_Department

Используемые версии:
apache-maven-3.3.9
apache-tomcat-9.0.0.M1

==================================
для работы приложения требуется установленный MySql5
для работы приложения требуется создать schema - "mydbtest"
(еcли schema не создалась автоматически)

для доступа к базе данных используется следующие настройки:
Host: localhost
Database: mydbtest
User: root
Password: root

для тестов используется DB h2 в In-memory database mode

При старте приложения база данных заполняется автоматически
данные берутся из:

для проекта: src\main\resources\test_data.sql

для тестов: src\main\resources\db-schema.sql
	    src\main\resources\db-test-data.sql

==================================

Web - приложение позволяет:

1. посмотреть список отделов и среднюю зарплату (рассчитывается автоматически) по этим отделам (первая списочная форма);

2. список сотрудников в отделах с указанием зарплаты для каждого сотрудника  и полем поиска для поиска сотрудников родившихся в определенную дату или в период между датами (вторая списочная форма);

3. изменять (добавлять/редактировать/удалять) вышеуказанные данные.