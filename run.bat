@ECHO OFF
set LOGDIR=logs
set ERRLOG=%LOGDIR%\std_err.log
set OUTLOG=%LOGDIR%\std_out.log
set GCLOG=%LOGDIR%\gc_pid_%p.log
set DUMPDIR=dumps
set CP=target\classes;target\test-classes;target\libs;target\gson-2.8.2.jar;target\javax.json-1.0.4.jar;target
set REMOTE_DEBUG0=-agentlib:jdwp=transport=dt_socket,address=14025,server=y,suspend=n
set REMOTE_DEBUG=
set MEMORY=-Xms512m -Xmx512m -XX:MaxMetaspaceSize=256m
set GC_LOG=-verbose:gc
set GC_LOG=%GC_LOG% -Xloggc:01_Single_Default\logs\gc_pid_%p.log
set GC_LOG=%GC_LOG% -XX:+PrintGCDateStamps
set GC_LOG=%GC_LOG% -XX:+PrintGCDetails
set GC_LOG=%GC_LOG% -XX:+UseGCLogFileRotation
set GC_LOG=%GC_LOG% -XX:NumberOfGCLogFiles=10
set GC_LOG=%GC_LOG% -XX:GCLogFileSize=100M
set JMX=-Dcom.sun.management.jmxremote.port=15025
set JMX=%JMX% -Dcom.sun.management.jmxremote.authenticate=false
set JMX=%JMX% -Dcom.sun.management.jmxremote.ssl=false
set DUMP=-XX:+HeapDumpOnOutOfMemoryError
set DUMP=%DUMP% -XX:HeapDumpPath=%DUMPDIR%
mkdir %LOGDIR% > nul 2> nul
mkdir %DUMPDIR% > nul 2> nul
java -cp %CP% %REMOTE_DEBUG% %MEMORY% %GC_LOG% %JMX% %DUMP% ru.otus.l081.Main 2> %ERRLOG%
