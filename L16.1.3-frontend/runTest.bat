@ECHO OFF
set LOGDIR=logs
mkdir %LOGDIR%
set ERRLOG=%LOGDIR%\std_err.log
set OUTLOG=%LOGDIR%\std_out.log
set GCLOG=%LOGDIR%"\gc_pid_%p.log"
set DUMPDIR=dumps
mkdir %DUMPDIR%
set CP="target\classes;target\test-classes;target\libs;target\classes\public_html;target\*"
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
set JMX="-Dcom.sun.management.jmxremote.port=15027"
set JMX=%JMX%" -Dcom.sun.management.jmxremote.authenticate=false"
set JMX=%JMX%" -Dcom.sun.management.jmxremote.ssl=false"
set DUMP="-XX:+HeapDumpOnOutOfMemoryError"
set DUMP=%DUMP%" -XX:HeapDumpPath="%DUMPDIR%
java -cp %CP% %MEMORY% %GC_LOG% %JMX% %DUMP% FrontendMain
