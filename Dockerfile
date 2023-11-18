FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /workspace/app

COPY . .

RUN ./gradlew clean build

FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/build/libs
COPY --from=build ${DEPENDENCY}/qr-attendance-0.0.1-SNAPSHOT.jar /app/qr-attendance-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/app/qr-attendance-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080