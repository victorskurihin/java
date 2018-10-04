@ECHO OFF
REM Build:
REM       mvn clean compile dependency:copy-dependencies
SET CS=UTF-8
SET CP=-cp "target\classes;target\dependency\*;target\*"
SET JAVA_OPTS=%CP% -Dfile.encoding=%CS% -Dsun.stdout.encoding=%CS% -Dsun.err.encoding=%CS%
CHCP 65001
java %JAVA_OPTS% ru.otus.CreateFunction
