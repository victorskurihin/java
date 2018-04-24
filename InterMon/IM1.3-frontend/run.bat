@ECHO OFF
set LOGDIR=logs
set ERRLOG=%LOGDIR%\std_err.log
set OUTLOG=%LOGDIR%\std_out.log
set GCLOG=%LOGDIR%\gc_pid_%p.log
set DUMPDIR=dumps
set CP="target\classes;target\test-classes;target\libs;target\*"
set MEMORY=-Xms512m -Xmx512m -XX:MaxMetaspaceSize=256m
set GC_LOG=-verbose:gc
set GC_LOG=%GC_LOG% -Xloggc:%LOGDIR%\gc_pid_%p.log
set GC_LOG=%GC_LOG% -XX:+PrintGCDateStamps
set GC_LOG=%GC_LOG% -XX:+PrintGCDetails
set GC_LOG=%GC_LOG% -XX:+UseGCLogFileRotation
set GC_LOG=%GC_LOG% -XX:NumberOfGCLogFiles=10
set GC_LOG=%GC_LOG% -XX:GCLogFileSize=100M
set JMX=-Dcom.sun.management.jmxremote.port=15026
set JMX=%JMX% -Dcom.sun.management.jmxremote.authenticate=false
set JMX=%JMX% -Dcom.sun.management.jmxremote.ssl=false
set DUMP=-XX:+HeapDumpOnOutOfMemoryError
set DUMP=%DUMP% -XX:HeapDumpPath=%DUMPDIR%
mkdir %LOGDIR% > nul 2> nul
mkdir %DUMPDIR% > nul 2> nul
java -cp %CP% %MEMORY% %GC_LOG% %JMX% %DUMP% -jar target\frontend.jar
