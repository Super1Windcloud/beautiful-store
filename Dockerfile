FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app
COPY . .

RUN mvn -pl backend -am clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app

#从 build 阶段复制 JAR 包，而不是宿主机
COPY --from=build /app/backend/target/backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 33333
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
