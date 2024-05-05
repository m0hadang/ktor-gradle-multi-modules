tasks.getByName("bootJar") {
    enabled = true // 실행 가능한 jar 파일 생성
}

tasks.getByName("jar") {
    enabled = false
}

dependencies {
    implementation(project(":modules:common"))
    implementation("org.springframework.boot:spring-boot-starter-web")
}