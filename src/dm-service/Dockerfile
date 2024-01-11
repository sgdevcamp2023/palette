FROM openjdk:17-oracle
ARG JAR_FILE=build/libs/easel-dm-service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} easel-dm-service.jar
ENTRYPOINT ["java", "-jar", "/easel-dm-service.jar"]
