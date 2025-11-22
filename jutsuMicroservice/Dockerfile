FROM maven:3.8.5-openjdk-17 as build

WORKDIR /app
COPY pom.xml ./

WORKDIR /app/jutsuMicroservice/

COPY jutsuMicroservice/pom.xml ./
COPY jutsuMicroservice/src/ ./src/

RUN mvn dependency:go-offline
RUN mvn clean package -DskipTests


RUN ls -la /app/jutsuMicroservice/target


FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=build /app/jutsuMicroservice/target/jutsuMicroservice-0.0.1-SNAPSHOT.jar /app/jutsuMicroservice.jar

EXPOSE 8070

ENTRYPOINT ["java","-jar","/app/jutsuMicroservice.jar"]