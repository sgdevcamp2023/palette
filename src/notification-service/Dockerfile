FROM openjdk:17-oracle
ARG JAR_FILE=build/libs/easel-notification-service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} easel-notification-service.jar
ENTRYPOINT ["java", "-jar", "/easel-notification-service.jar"]
