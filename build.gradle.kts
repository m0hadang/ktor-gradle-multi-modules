import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// root project

plugins {
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.23" // 현재 build.gradle.kts 에서 java, import 같은 키워드 사용하기 위해 필요
    kotlin("plugin.spring") version "1.9.23"
}

// kotlin과 호환될 java 버전 설정(현재 root project 에만 적용)
java {
    sourceCompatibility = JavaVersion.VERSION_17
}

// 모든 project에 적용
allprojects {
    group = "com.example"
    version = "0.0.1-SNAPSHOT"
    repositories {
        mavenCentral()
    }
}

// subproject 들에만 적용
subprojects {
    // implementation 같은 키워드 사용 가능
    apply(plugin = "org.jetbrains.kotlin.jvm")
    // implementation 을 통해 추가 되는 라이브러리(ex: web) 의존성 관리
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    // Bean 객체 처리
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")

    dependencies {
        // 모든 submodule이 web 라이브러리가 않기에 제거
        // implementation("org.springframework.boot:spring-boot-starter-web")

        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }
    tasks.withType<Test> {
        useJUnitPlatform()
    }

    // kotlin과 호환될 java 버전 설정(모든 subproject 들에 적용)
    java {
        sourceCompatibility = JavaVersion.VERSION_17
    }
}