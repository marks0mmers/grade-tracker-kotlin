import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	id("org.springframework.boot") version "2.4.2"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.4.30"
	kotlin("plugin.spring") version "1.4.30-RC"
}

group = "com.marks0mmers"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot", "spring-boot-starter-data-mongodb-reactive")
	implementation("org.springframework.boot", "spring-boot-starter-security")
	implementation("org.springframework.boot", "spring-boot-starter-validation")
	implementation("org.springframework.boot", "spring-boot-starter-webflux")
	implementation("io.jsonwebtoken", "jjwt", "0.9.1")
	implementation("com.fasterxml.jackson.module", "jackson-module-kotlin")
	implementation("io.projectreactor.kotlin", "reactor-kotlin-extensions")
	implementation("jakarta.xml.bind", "jakarta.xml.bind-api")
	implementation("org.jetbrains.kotlin", "kotlin-reflect")
	implementation("org.jetbrains.kotlin", "kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core")
	implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-reactor")
	testImplementation("org.springframework.boot", "spring-boot-starter-test")
	testImplementation("io.projectreactor", "reactor-test")
	testImplementation("org.springframework.security", "spring-security-test")
}

tasks.withType<BootJar> {
	archiveFileName.set("${archiveBaseName.get()}.${archiveExtension.get()}")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
