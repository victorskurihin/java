# 16-e домашнее задание курса "Разработчик Java" в Otus.ru

Группа 2017-11
 * Solution for L16.1
 * PreReq: A DB compatible with Hibernate ORM.

Configure application in:
 *   ../L16.1.2-dbserver/src/main/resources/hibernate.cfg.xml
 *   ../L16.1.2-dbserver/src/main/resources/createSchema.hibernate.cfg.xml
 *
 * To start:
    * For UNIX like OS:
    $ cd ../L16.1.1-msgsrv
    $ mvn clean install
    $ cd  ../L16.1.2-dbserver
    $ mvn clean package
    $ ./createSchema.sh
    $ cd ../L16.1.3-frontend
    $ mvn clean package
    $ cd ../L16.1.1-msgsrv
    $ ./run.sh

 * For OS Windows:
    > cd ..\L16.1.1-msgsrv
    > mvn clean install
    > cd ..\L16.1.2-dbserver
    > mvn clean package
    > createSchema.bat
    > cd ..\L16.1.3-frontend
    > mvn clean package
    > cd ..\L16.1.1-msgsrv
    > run.bat
