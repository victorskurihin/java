#!/bin/sh
# Build:
#       mvn clean compile dependency:copy-dependencies
export LANG=ru_RU.UTF-8
CS="encoding=UTF-8"  
CP="-cp ./target/classes:./target/dependency/*:./target/*"
JAVA_OPTS="$CP -Dfile.$CS -Dsun.stdout.$CS -Dsun.$CS"
java ${JAVA_OPTS} ru.otus.GenerateXML src/main/resources/StatisticEntities.xml  StatisticEntity  < csv/statistic.csv
java ${JAVA_OPTS} ru.otus.GenerateXML src/main/resources/DeptEntities.xml       DeptEntity       < csv/dep_directory.csv
java ${JAVA_OPTS} ru.otus.GenerateXML src/main/resources/UserEntities.xml       UserEntity       < csv/users.csv
java ${JAVA_OPTS} ru.otus.GenerateXML src/main/resources/EmpEntities.xml        EmpEntity        < csv/emp_registry.csv
java ${JAVA_OPTS} ru.otus.GenerateXML src/main/resources/UsersgroupEntities.xml UsersgroupEntity < csv/user_groups.csv
