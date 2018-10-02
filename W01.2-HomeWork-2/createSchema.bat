@ECHO OFF
REM Build:
REM mvn clean compile dependency:copy-dependencies
SET CP="target\classes;target\dependency\*;target\*"
java -cp %CP% ru.otus.CreateTables
