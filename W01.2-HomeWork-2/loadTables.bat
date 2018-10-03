@ECHO OFF
REM Build:
REM       mvn clean compile dependency:copy-dependencies
SET CP="target\classes;target\dependency\*;target\*"
REM JAVA_OPTS="-Dfile.encoding=UTF-8 -cp "%CP%
CHCP 65001
java -Dfile.encoding=UTF-8 -cp %CP% ru.otus.LoadTables dep_directory < dep_directory.csv
java -Dfile.encoding=UTF-8 -cp %CP% ru.otus.LoadTables emp_registry  < emp_registry.csv
