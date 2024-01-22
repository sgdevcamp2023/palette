FROM openjdk:17-oracle
ARG JAR_FILE=build/libs/easel-discovery-service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} easel-discovery-service.jar
ENTRYPOINT ["java", "-jar", "/easel-discovery-service.jar"]

