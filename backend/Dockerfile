FROM amazoncorretto:21.0.3-alpine3.19
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
WORKDIR /app

COPY . .
RUN ./gradlew build
CMD ["java", "-jar", "/app/build/libs/backend-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=test"]
EXPOSE 8080