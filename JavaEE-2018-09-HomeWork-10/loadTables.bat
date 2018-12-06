@ECHO OFF
REM Build:
REM       mvn clean compile dependency:copy-dependencies
SET CS=encoding=UTF-8
SET CP=-cp "target\classes;target\dependency\*;target\*"
SET JAVA_OPTS=%CP% -Dfile.%CS% -Dsun.stdout.%CS% -Dsun.err.%CS%
CHCP 65001
java %JAVA_OPTS% ru.otus.LoadTables statistic      < csv\statistic.csv
java %JAVA_OPTS% ru.otus.LoadTables dept_directory < csv\dept_directory.csv
java %JAVA_OPTS% ru.otus.LoadTables users          < csv\users.csv
java %JAVA_OPTS% ru.otus.LoadTables emp_registry   < csv\emp_registry.csv
java %JAVA_OPTS% ru.otus.LoadTables user_groups    < csv\user_groups.csv
