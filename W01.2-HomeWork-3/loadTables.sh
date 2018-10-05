#!/bin/sh
# Build:
#       mvn clean compile dependency:copy-dependencies
export LANG=ru_RU.UTF-8
CS="encoding=UTF-8"  
CP="-cp ./target/classes:./target/dependency/*:./target/*"
JAVA_OPTS="$CP -Dfile.$CS -Dsun.stdout.$CS -Dsun.$CS"
java ${JAVA_OPTS} ru.otus.LoadTables dep_directory < dep_directory.csv
java ${JAVA_OPTS} ru.otus.LoadTables emp_registry  < emp_registry.csv
