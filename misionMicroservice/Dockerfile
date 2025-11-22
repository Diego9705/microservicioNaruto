FROM maven:3.8.5-openjdk-17 as build

WORKDIR /app
COPY pom.xml ./

WORKDIR /app/misionMicroservice/

COPY misionMicroservice/pom.xml ./
COPY misionMicroservice/src/ ./src/

RUN mvn dependency:go-offline
RUN mvn clean package -DskipTests


RUN ls -la /app/misionMicroservice/target


FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=build /app/misionMicroservice/target/misionMicroservice-0.0.1-SNAPSHOT.jar /app/misionMicroservice.jar

EXPOSE 8050

ENTRYPOINT ["java","-jar","/app/misionMicroservice.jar"]