import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.6.1"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.0"
	kotlin("plugin.spring") version "1.6.0"
}

group = "com.rodrigoma"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

buildscript {
	repositories {
		mavenCentral()
	}
	dependencies {
		classpath(libs.kotlin.plugin)
		classpath(libs.releasesHubGradlePlugin)
	}
}
apply(plugin = "com.dipien.releaseshub.gradle.plugin")

dependencies {
	implementation(libs.spring.boot.starter)
	implementation(libs.kotlin.reflect)
	implementation(libs.kotlin)
	testImplementation(libs.spring.boot.starter.test)
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
