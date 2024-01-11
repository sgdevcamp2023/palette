FROM openjdk:17-oracle
ARG JAR_FILE=build/libs/easel-user-service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} easel-user-service.jar
ENTRYPOINT ["java", "-jar", "/easel-user-service.jar"]