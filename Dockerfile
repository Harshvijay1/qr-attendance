FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
RUN ./gradlew clean build
COPY build/libs/qr-attendance-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080