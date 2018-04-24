#!/bin/sh
LOGDIR=./logs
ERRLOG=$LOGDIR/std_err.log
OUTLOG=$LOGDIR/std_out.log
GCLOG="$LOGDIR/gc_pid_%p.log"
WCGCLOG="$LOGDIR/gc_pid_*.log*"
DUMPDIR=./dumps
CP="./target/classes:./target/test-classes:./target/libs:./target/*"
MEMORY="-Xms512m -Xmx512m -XX:MaxMetaspaceSize=256m"
GC="-XX:+UseG1GC"
GC="${GC} -XX:MaxGCPauseMillis=500"
GC_LOG="-verbose:gc"
GC_LOG="${GC_LOG} -Xloggc:${GCLOG}"
GC_LOG="${GC_LOG} -XX:+PrintGCDateStamps"
GC_LOG="${GC_LOG} -XX:+PrintGCDetails"
GC_LOG="${GC_LOG} -XX:+UseGCLogFileRotation"
GC_LOG="${GC_LOG} -XX:NumberOfGCLogFiles=10"
GC_LOG="${GC_LOG} -XX:GCLogFileSize=100M"
JMX="-Dcom.sun.management.jmxremote.port=15026"
JMX="${JMX} -Dcom.sun.management.jmxremote.authenticate=false"
JMX="${JMX} -Dcom.sun.management.jmxremote.ssl=false"
DUMP="-XX:+HeapDumpOnOutOfMemoryError"
DUMP="${DUMP} -XX:HeapDumpPath=${DUMPDIR}"
mkdir -p $LOGDIR
mkdir -p $DUMPDIR
java -cp ${CP} ${MEMORY} ${GC} ${GC_LOG} ${JMX} ${DUMP} \
    -XX:OnOutOfMemoryError="kill -3 %p" -jar ./target/dbserver.jar
