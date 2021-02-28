plugins {
    id("java")
    kotlin("jvm")
    id("application")
    id("distribution")
}

val ktorVersion = project.property("ktor.version") as String
val logbackVersion = project.property("logback.version") as String

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-html-builder:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("org.litote.kmongo:kmongo:4.2.3")
}

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

tasks.withType<Copy>().named("processResources") {
    from(project(":client").tasks.named("browserDistribution"))
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
}
