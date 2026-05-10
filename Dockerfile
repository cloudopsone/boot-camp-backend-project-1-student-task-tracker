# syntax=docker/dockerfile:1

FROM eclipse-temurin:25-jdk AS build
WORKDIR /app

COPY gradlew gradlew.bat build.gradle settings.gradle ./
COPY gradle ./gradle
COPY src ./src

RUN chmod +x ./gradlew && ./gradlew clean bootJar -x test --no-daemon

FROM eclipse-temurin:25-jdk
WORKDIR /app

COPY --from=build /app/build/libs/task-tracker-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
CMD ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar /app/app.jar"]
