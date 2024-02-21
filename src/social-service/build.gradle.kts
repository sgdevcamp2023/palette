plugins {
    java
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "org.pallete"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2023.0.0"

dependencies {
    // gRPC
    implementation("net.devh:grpc-spring-boot-starter:2.15.0.RELEASE")

    // Common Module
    implementation(project(":common-module"))

    // Kafka
    implementation("org.springframework.kafka:spring-kafka")

    // Neo4j
    implementation("org.neo4j.driver:neo4j-java-driver:5.15.0")
    implementation("org.springframework.boot:spring-boot-starter-data-neo4j")

    // Tomcat
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Eureka
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
