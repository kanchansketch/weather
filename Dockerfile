FROM maven:3.9.9-eclipse-temurin-21-jammy AS builder
LABEL authors="kanch"
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
RUN mkdir -p /app/data && chmod -R 777 /app
ENV WEATHER_API_KEY=null
VOLUME /app/data
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]