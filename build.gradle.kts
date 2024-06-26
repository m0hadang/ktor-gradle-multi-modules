import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// root project

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    // 현재 build.gradle.kts 에서 java, import 같은 키워드 사용하기 위해 필요
    kotlin("jvm")
    kotlin("plugin.spring")
}

// kotlin과 호환될 java 버전 설정(현재 root project 에만 적용)
java {
    sourceCompatibility = JavaVersion.VERSION_17
}

val projectGroup: String by project
val applicationVersion: String by project

// 모든 project에 적용
allprojects {
    group = projectGroup
    version = applicationVersion
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

    tasks.getByName("bootJar") {
        enabled = false // 모든 subproject 들이 실행 가능한 jar 파일 생성할 필요 없기에 false
    }

    tasks.getByName("jar") {
        enabled = true // 기본적으로 subproject 들은 일반 jar 파일 생성
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