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
	// Kafka
	implementation("org.springframework.kafka:spring-kafka")

	// Eureka
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

	// common
	implementation (project(":common-module"))

	// gRPC
	implementation ("net.devh:grpc-spring-boot-starter:2.15.0.RELEASE")

	// elasticsearch
	implementation ("org.springframework.boot:spring-boot-starter-data-elasticsearch")

	// tomcat
	implementation("org.springframework.boot:spring-boot-starter-web")

	// lombok
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
