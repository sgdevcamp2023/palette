FROM openjdk:17-oracle
ARG JAR_FILE=build/libs/easel-timeline-service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} easel-timeline-service.jar
ENTRYPOINT ["java", "-jar", "/easel-timeline-service.jar"]
