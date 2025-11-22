FROM maven:3.8.5-openjdk-17 as build

WORKDIR /app
COPY pom.xml ./

WORKDIR /app/ninjaMicroservice/

COPY ninjaMicroservice/pom.xml ./
COPY ninjaMicroservice/src/ ./src/

RUN mvn dependency:go-offline
RUN mvn clean package -DskipTests


RUN ls -la /app/ninjaMicroservice/target


FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=build /app/ninjaMicroservice/target/ninjaMicroservice-0.0.1-SNAPSHOT.jar /app/ninjaMicroservice.jar

EXPOSE 8060

ENTRYPOINT ["java","-jar","/app/ninjaMicroservice.jar"]