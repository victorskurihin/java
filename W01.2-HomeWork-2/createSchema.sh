#!/bin/sh
# Build:
# mvn clean compile dependency:copy-dependencies
CP="./target/classes:./target/dependency/*:./target/*"
java -cp ${CP} ru.otus.CreateTables
