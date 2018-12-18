#!/bin/sh
for POM in HomeWork/pom.xml HomeWorkPersistent/pom.xml HomeWorkREST/pom.xml pom.xml
do
  sed -i 's/10HW.1-6/11HW.1-6/g' $POM
done
# mvn -f HomeWork/pom.xml glassfish:redeploy
