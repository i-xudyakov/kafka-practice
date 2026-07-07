plugins {
    kotlin("jvm") version "2.1.21"
}

group = "demo"
version = "1.0"

repositories {
    mavenLocal()

    mavenCentral()

    maven {
        url = uri("https://packages.confluent.io/maven/")
    }
}

dependencies {
    implementation("org.apache.kafka:kafka-clients:3.7.1")
    implementation("org.apache.avro:avro:1.11.4")
    implementation("io.confluent:kafka-avro-serializer:7.6.1")
}