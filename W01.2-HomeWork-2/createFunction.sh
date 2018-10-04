#!/bin/sh
# Build:
#       mvn clean compile dependency:copy-dependencies
export LANG=ru_RU.UTF-8
CP="./target/classes:./target/dependency/*:./target/*"
java -Dfile.encoding=UTF-8 -cp ${CP} ru.otus.CreateFunction