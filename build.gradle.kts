plugins {
    java
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
}

group = "AroundTheEurope"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2023.0.1"
extra["jacksonVersion"] = "2.15.2"

dependencies {
    implementation("org.springframework.boot:spring-boot")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-gateway-mvc")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    // Dotenv
    implementation("io.github.cdimascio:dotenv-java:3.0.0")
    // Jackson
    implementation("com.fasterxml.jackson.core:jackson-databind:${property("jacksonVersion")}")
    implementation("com.fasterxml.jackson.core:jackson-core:${property("jacksonVersion")}")
    implementation("com.fasterxml.jackson.core:jackson-annotations:${property("jacksonVersion")}")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:${property("jacksonVersion")}")
    // Redis
    implementation("org.springframework.data:spring-data-redis:3.3.0")
    implementation("io.lettuce:lettuce-core:6.3.2.RELEASE")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        mavenBom("com.fasterxml.jackson:jackson-bom:${property("jacksonVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
