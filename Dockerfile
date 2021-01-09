# FROM openjdk:8-jdk-alpine
FROM openjdk:11-jre-slim
# FROM openjdk:11.0.4-jre-slim
VOLUME /tmp
COPY target/really-strange-chess-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]

