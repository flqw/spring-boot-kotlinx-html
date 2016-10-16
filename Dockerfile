FROM maven:3-jdk-8-alpine

COPY . /src
RUN chmod +x /src/build.sh && /src/build.sh
RUN rm -rf /src

EXPOSE 8080

CMD ["java", "-jar", "spring-boot-kotlinx-html.jar"]