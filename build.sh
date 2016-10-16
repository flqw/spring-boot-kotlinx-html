#!/bin/sh

WORKDIR=$(dirname $0)

cd ${WORKDIR}

mvn clean package

JAR_FILE=$(find . -name *.jar -exec basename {} \;)
mv target/${JAR_FILE} /spring-boot-kotlinx-html.jar