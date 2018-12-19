#!/bin/sh
for POM in HomeWork/pom.xml HomeWorkPersistent/pom.xml HomeWorkREST/pom.xml pom.xml
do
  sed -i 's/11.1-7/11.1-8/g' $POM
done
# mvn -f HomeWork/pom.xml glassfish:redeploy
