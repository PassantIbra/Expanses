FROM maven:3.8.3-openjdk-17-slim
COPY target/expanses-0.0.1-SNAPSHOT.jar expanses.jar
ENTRYPOINT ["java","-jar","/expanses.jar"]