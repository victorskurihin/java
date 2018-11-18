@ECHO OFF
REM Build:
REM       mvn clean compile dependency:copy-dependencies
SET CS=encoding=UTF-8
SET CP=-cp "target\classes;target\dependency\*;target\*"
SET JAVA_OPTS=%CP% -Dfile.%CS% -Dsun.stdout.%CS% -Dsun.err.%CS%
CHCP 65001
java %JAVA_OPTS% ru.otus.GenerateXML src\main\resources\StatisticEntities.xml  StatisticEntity  < csv/statistic.csv
java %JAVA_OPTS% ru.otus.GenerateXML src\main\resources\DeptEntities.xml       DeptEntity       < csv\dep_directory.csv
java %JAVA_OPTS% ru.otus.GenerateXML src\main\resources\UserEntities.xml       UserEntity       < csv\users.csv
java %JAVA_OPTS% ru.otus.GenerateXML src\main\resources\EmpEntities.xml        EmpEntity        < csv\emp_registry.csv
java %JAVA_OPTS% ru.otus.GenerateXML src\main\resources\UsersgroupEntities.xml UsersgroupEntity < csv\user_groups.csv
