#!/bin/sh
# Build:
#       mvn clean compile dependency:copy-dependencies
export LANG=ru_RU.UTF-8
CP="./target/classes:./target/dependency/*:./target/*"
JAVA_OPTS="-Dfile.encoding=UTF-8 -cp ${CP}"
java ${JAVA_OPTS} ru.otus.LoadTables dep_directory < dep_directory.csv
java ${JAVA_OPTS} ru.otus.LoadTables emp_registry  < emp_registry.csv
