FROM openjdk:17

COPY build/libs/notification-service.war /app/app.war

CMD ["java", "-jar", "-Dspring.profiles.active=dev", "/app/app.war"]
