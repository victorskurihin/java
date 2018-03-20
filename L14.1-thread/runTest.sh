#!/bin/sh
LOGDIR=./logs
mkdir -p $LOGDIR
ERRLOG=$LOGDIR/std_err.log
OUTLOG=$LOGDIR/std_out.log
GCLOG="$LOGDIR/gc_pid_%p.log"
WCGCLOG="$LOGDIR/gc_pid_*.log*"
DUMPDIR=./dumps
mkdir -p $DUMPDIR
CP="-cp ./target/classes:./target/test-classes:./target/libs:./target"
CP=$CP":./target/L09.1.jar"
CP=$CP":./target/gson-2.8.2.jar"
CP=$CP":./target/guava-21.0.jar"
CP=$CP":./target/hamcrest-core-1.3.jar"
CP=$CP":./target/javassist-3.21.0-GA.jar"
CP=$CP":./target/javax.json-1.0.4.jar"
CP=$CP":./target/json-simple-1.1.1.jar"
CP=$CP":./target/junit-4.12.jar"
CP=$CP":./target/log4j-api-2.10.0.jar"
CP=$CP":./target/log4j-core-2.10.0.jar"
CP=$CP":./target/reflections-0.9.11.jar"
CP=$CP":./target/postgresql-9.1-901-1.jdbc4.jar"
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
    -XX:OnOutOfMemoryError="kill -3 %p" ru.otus.l141.MainTest 
