FROM maven:3.8.5-openjdk-17 as build

WORKDIR /app
COPY pom.xml ./

WORKDIR /app/aldeaMicroservice/

COPY aldeaMicroservice/pom.xml ./
COPY aldeaMicroservice/src/ ./src/

RUN mvn dependency:go-offline
RUN mvn clean package -DskipTests


RUN ls -la /app/aldeaMicroservice/target


FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=build /app/aldeaMicroservice/target/aldeaMicroservice-0.0.1-SNAPSHOT.jar /app/aldeaMicroservice.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/aldeaMicroservice.jar"]