FROM openjdk:17-oracle
ARG JAR_FILE=build/libs/easel-social-service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} easel-social-service.jar
ENTRYPOINT ["java", "-jar", "/easel-social-service.jar"]