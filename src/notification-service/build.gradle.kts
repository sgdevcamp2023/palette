plugins {
    java
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "org.palette"
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
    // Common Module
    implementation(project(":common-module"))

    // Socket
    implementation("org.springframework.boot:spring-boot-starter-websocket")

    // gRPC
    implementation("net.devh:grpc-spring-boot-starter:2.15.0.RELEASE")

    // Tomcat
    implementation("org.springframework.boot:spring-boot-starter-web")

    // JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // MySQL Driver
    runtimeOnly("com.mysql:mysql-connector-j")

    // RabbitMQ
    implementation("org.springframework.boot:spring-boot-starter-amqp")

    // Kafka
    implementation("org.springframework.kafka:spring-kafka")

    // FCM
    implementation("com.google.firebase:firebase-admin:9.2.0")

    // Eureka
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    // AOP
    implementation("org.springframework.boot:spring-boot-starter-aop")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
