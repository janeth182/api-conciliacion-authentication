FROM openjdk:17-alpine
VOLUME /tmp
EXPOSE 8130
ARG JAR_FILE=build/libs/api-conciliacion-authentication-0.0.1.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]


