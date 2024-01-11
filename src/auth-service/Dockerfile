FROM openjdk:17-oracle
ARG JAR_FILE=build/libs/easel-auth-service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} easel-auth-service.jar
ENTRYPOINT ["java", "-jar", "/easel-auth-service.jar"]
