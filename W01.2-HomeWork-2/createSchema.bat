@ECHO OFF
REM Build:
REM       mvn clean compile dependency:copy-dependencies
SET CP="target\classes;target\dependency\*;target\*"
CHCP 65001
java -Dfile.encoding=UTF-8 -cp %CP% ru.otus.CreateTables
