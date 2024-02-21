FROM openjdk:17-oracle
ARG JAR_FILE=build/libs/easel-gateway-service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} easel-gateway-service.jar
ENTRYPOINT ["java", "-jar", "/easel-gateway-service.jar"]