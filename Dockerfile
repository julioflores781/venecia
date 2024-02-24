FROM openjdk:22-ea-21-jdk

WORKDIR /app

COPY target/venecia-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "venecia-0.0.1-SNAPSHOT.jar"]
