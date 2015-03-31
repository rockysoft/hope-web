hope-web
========

	A Maven project using SpringMVC 3.2.x, MyBatis, Extjs 4 and Log4j/Logback configuration.

Features
========

  * SpringMVC 3.2.11
  * Mybatis 3.2.3
  * Extjs 4
  * Log4j/Logback
  
Quick Start
========
####1. Create the database for the hope-web application using the mysql database connector.
```java
# mysql -u root -p
Enter password: *
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 1
Server version: 5.1.58-community MySQL Community Server (GPL)

Copyright (c) 2000, 2010, Oracle and/or its affiliates. All rights reserved.
This software comes with ABSOLUTELY NO WARRANTY. This is free software,
and you are welcome to modify and redistribute it under the GPL v2 license

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> CREATE DATABASE `hope` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
Query OK, 1 row affected (0.03 sec)

mysql> show databases;
+---------------------+
| Database            |
+---------------------+
| information_schema  |
| hope                |
| mysql               |
| test                |
+---------------------+
4 rows in set (0.52 sec)

mysql> use hope;
Database changed
mysql> source D:\hope-web\db\hope.sql;
Query OK, 0 rows affected (0.00 sec)
...
```
  
####2. Run it
```java
	cd hope-web
 	mvn jetty:run
```
Go to http://localhost:8080/hope-web/ in a browser.
