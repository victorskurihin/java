@ECHO OFF
set LOGDIR=logs
mkdir %LOGDIR%
set ERRLOG=%LOGDIR%\std_err.log
set OUTLOG=%LOGDIR%\std_out.log
set GCLOG=%LOGDIR%"\gc_pid_%p.log"
set DUMPDIR=dumps
mkdir %DUMPDIR%
set CP="-cp target\classes;target\test-classes;target\libs:target"
set CP=%CP%;target\gson-2.8.2.jar
set CP=%CP%;target\guava-21.0.jar
set CP=%CP%;target\hamcrest-core-1.3.jar
set CP=%CP%;target\javassist-3.21.0-GA.jar
set CP=%CP%;target\javax.json-1.0.4.jar
set CP=%CP%;target\json-simple-1.1.1.jar
set CP=%CP%;target\junit-4.12.jar
set CP=%CP%;target\log4j-api-2.10.0.jar
set CP=%CP%;target\log4j-core-2.10.0.jar
set CP=%CP%;target\postgresql-9.1-901-1.jdbc4.jar
set CP=%CP%;target\reflections-0.9.11.jar
set CP=%CP%;postgresql-9.1-901-1.jdbc4.jar
set REMOTE_DEBUG="-agentlib:jdwp=transport=dt_socket,address=14025,server=y,suspend=n"
set MEMORY="-Xms512m -Xmx512m -XX:MaxMetaspaceSize=256m"
set GC="-XX:+UseG1GC"
set GC=%GC%" -XX:MaxGCPauseMillis=500"
set GC_LOG="-verbose:gc"
set GC_LOG=%GC_LOG%" -Xloggc:logs\gc_pid_%p.log"
set GC_LOG=%GC_LOG%" -XX:+PrintGCDateStamps"
set GC_LOG=%GC_LOG%" -XX:+PrintGCDetails"
set GC_LOG=%GC_LOG%" -XX:+UseGCLogFileRotation"
set GC_LOG=%GC_LOG%" -XX:NumberOfGCLogFiles=10"
set GC_LOG=%GC_LOG%" -XX:GCLogFileSize=100M"
set JMX="-Dcom.sun.management.jmxremote.port=15025"
set JMX=%JMX%" -Dcom.sun.management.jmxremote.authenticate=false"
set JMX=%JMX%" -Dcom.sun.management.jmxremote.ssl=false"
set DUMP="-XX:+HeapDumpOnOutOfMemoryError"
set DUMP=%DUMP%" -XX:HeapDumpPath="%DUMPDIR%
java %CP% %REMOTE_DEBUG% %MEMORY% %GC% %GC_LOG% %JMX% %DUMP% ru.otus.l091.MainTest 2> %ERRLOG% > %OUTLOG%
