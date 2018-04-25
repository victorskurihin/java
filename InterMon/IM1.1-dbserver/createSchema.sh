#!/bin/sh
DUMPDIR=./dumps
CP="./target/classes:./target/test-classes:./target/libs:./target/*"
DUMP="-XX:+HeapDumpOnOutOfMemoryError"
DUMP="${DUMP} -XX:HeapDumpPath=${DUMPDIR}"
mkdir -p $DUMPDIR
java -cp ${CP} ${DUMP} \
    -XX:OnOutOfMemoryError="kill -3 %p" com.github.intermon.CreateSchemaMain
