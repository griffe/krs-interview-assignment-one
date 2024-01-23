FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=build/libs/assignment-one-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} assignment.jar
ENTRYPOINT ["java","-jar","/assignment.jar"]