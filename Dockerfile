FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

RUN chmod +x gradlew
RUN ./gradlew bootJar

COPY src src

EXPOSE 8080

CMD ["java", "-jar", "build/libs/MovieManagementWeek05-0.0.1-SNAPSHOT.jar"]