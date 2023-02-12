import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Links
// https://blog.dipien.com/how-to-automate-your-dependencies-upgrades-with-github-actions-bedf1337ca3f
// https://blog.jdriven.com/2022/11/gradle-goodness-defining-plugin-versions-using-version-catalog/
// https://zone84.tech/programming/manage-your-dependencies-with-gradle-version-catalogs/

// necessario para suprir um warn de erro, corrigido na proxima versão 8.1 do gradle
// https://github.com/gradle/gradle/issues/22797
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
	alias(libs.plugins.springframework.boot)
	alias(libs.plugins.spring.dependency.management)
	alias(libs.plugins.springframework.cloud.contract)
	alias(libs.plugins.plantuml)
	alias(libs.plugins.avro)
	alias(libs.plugins.git.properties)
	alias(libs.plugins.allure)
	alias(libs.plugins.allure.adapter)
	alias(libs.plugins.releaseshub)
	id("application")

	alias(libs.plugins.kotlin.lang)
	alias(libs.plugins.kotlin.spring)
	alias(libs.plugins.kotlin.jpa)
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
		classpath(libs.build.sonarqube.gradle.plugin)
		classpath(libs.build.gradle.plantuml.plugin) { exclude("net.sourceforge.plantuml", "plantuml") }
		classpath(libs.build.plantuml)
	}
}
apply(plugin = "com.dipien.releaseshub.gradle.plugin")

dependencies {
	// Kotlin
	implementation(libs.jackson.module.kotlin)

	// Spring Boot
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-hateoas")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
	implementation("org.springframework.kafka:spring-kafka")

	// AWS
	implementation(libs.spring.cloud.starter.aws.messaging)
	implementation(libs.aws.java.sdk.secretsmanager)
	runtimeOnly(libs.aws.secretsmanager.jdbc)
	runtimeOnly(libs.aws.java.sdk.bom)
	runtimeOnly(libs.aws.java.sdk.core)

	// Database
	implementation(libs.liquibase.core)
	implementation(libs.hibernate.types)

	// Redis
	// versao mais alta que funcionou, demais davam erro de timeout
	implementation(libs.jedis)

	// New Relic
	implementation(libs.newrelic.api)

	// Tests
	testImplementation(libs.test.h2.database)
	testImplementation(libs.test.kotlin.extensions)
	testImplementation(libs.test.mockito.kotlin)
	testImplementation(libs.test.awaitility)
	testImplementation(libs.test.spring.cloud.starter.contract.stub.runner)
	testImplementation(libs.test.converter)
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:2022.0.1")
	}
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
