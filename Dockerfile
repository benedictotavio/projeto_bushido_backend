FROM ubuntu:22.04 AS build

RUN apt-get update && apt-get install -y openjdk-17-jdk && apt-get clean
COPY . .

RUN apt-get install -y maven && apt-get clean
RUN mvn clean install

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /target/institutobushido-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar"]