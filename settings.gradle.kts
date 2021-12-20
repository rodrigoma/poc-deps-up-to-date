rootProject.name = "poc-deps-up-to-date"
enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            alias("kotlin").to("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.40")
            alias("kotlin-reflect").to("org.jetbrains.kotlin:kotlin-reflect:1.3.40")
            alias("kotlin-plugin").to("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.41")
            alias("spring-boot-starter").to("org.springframework.boot:spring-boot-starter:2.6.1")
            alias("spring-boot-starter-test").to("org.springframework.boot:spring-boot-starter-test:2.6.1")
        }
    }
}
