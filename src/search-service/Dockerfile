FROM openjdk:17-oracle
ENV PASSPORT_KEY=${PASSPORT_KEY};
ENV PASSPORT_ALGORITHM=${PASSPORT_ALGORITHM};
ARG JAR_FILE=build/libs/easel-search-service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} easel-search-service.jar
ENTRYPOINT ["java", "-jar", "/easel-search-service.jar"]