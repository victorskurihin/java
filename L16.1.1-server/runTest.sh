#!/bin/sh
LOGDIR=./logs
mkdir -p $LOGDIR
ERRLOG=$LOGDIR/std_err.log
OUTLOG=$LOGDIR/std_out.log
GCLOG="$LOGDIR/gc_pid_%p.log"
WCGCLOG="$LOGDIR/gc_pid_*.log*"
DUMPDIR=./dumps
mkdir -p $DUMPDIR
CP="./target/classes:./target/test-classes:./target/libs:target\classes\public_html:./target*"
REMOTE_DEBUG="-agentlib:jdwp=transport=dt_socket,address=14025,server=y,suspend=n"
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
JMX="-Dcom.sun.management.jmxremote.port=15025"
JMX="${JMX} -Dcom.sun.management.jmxremote.authenticate=false"
JMX="${JMX} -Dcom.sun.management.jmxremote.ssl=false"
DUMP="-XX:+HeapDumpOnOutOfMemoryError"
DUMP="${DUMP} -XX:HeapDumpPath=${DUMPDIR}"
cat <<EOF
tail -f $OUTLOG $ERRLOG $WCGCLOG
EOF
rm -f $LOGDIR/* ${DUMPDIR}/*
java ${CP} ${REMOTE_DEBUG} ${MEMORY} ${GC} ${GC_LOG} ${JMX} ${DUMP} \
    -XX:OnOutOfMemoryError="kill -3 %p" ru.otus.l161.ServerMain
