import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    idea
    jacoco
    id("org.springframework.boot") version "2.1.6.RELEASE"
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
    id("org.jetbrains.kotlin.jvm") version "1.3.41"
    id("org.jetbrains.kotlin.plugin.spring") version "1.3.41"
    id("org.jetbrains.kotlin.kapt") version "1.3.41"
    id("com.github.ben-manes.versions") version "0.21.0"
    id("io.gitlab.arturbosch.detekt") version "1.0.0-RC16"
    id("nu.studer.jooq") version "3.0.3"
}

defaultTasks("bootRun")

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:Greenwich.RELEASE")
    }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs += listOf("-Xjsr305=strict", "-XXLanguage:+InlineClasses")
}

val integrationTestImplementation by configurations.creating {
    extendsFrom(configurations["testImplementation"])
}

// source: https://stackoverflow.com/a/52906232
tasks.register<Test>("integrationTest") {
    group = "verification"
    description = "Runs integration tests."
    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
    mustRunAfter(tasks.test)
}

sourceSets {
    val main by getting
    main.java.srcDirs("src/main/generated")

    create("integrationTest") {
        withConvention(KotlinSourceSet::class) {
            kotlin.srcDir("src/it/kotlin")
            resources.srcDir("src/it/resources")
            compileClasspath += sourceSets["main"].output + sourceSets["test"].output + configurations["testRuntimeClasspath"]
            runtimeClasspath += output + compileClasspath + sourceSets["test"].runtimeClasspath
        }
    }
}

val stage by tasks.registering {
    dependsOn(tasks.assemble)
}

val check by tasks.existing {
    dependsOn(tasks["integrationTest"])
}

jacoco {
    toolVersion = "0.8.2"
}

tasks.jacocoTestReport {
    reports {
        csv.isEnabled = false
        html.isEnabled = true
        xml.isEnabled = true
    }
    dependsOn(tasks.test)
}

detekt {
    config = files("detekt.yml")
    input = files(
        "src/main/kotlin",
        "src/test/kotlin",
        "src/it/kotlin"
    )
}

idea {
    module {
        sourceDirs.add(file("build/generated/source/kapt/main"))
        generatedSourceDirs.add(file("build/generated/source/kapt/main"))

        sourceDirs.add(file("src/main/generated/"))
        generatedSourceDirs.add(file("src/main/generated/"))
    }
}

/**
 * "Jooq" plugin does not support kotlin:
 * https://github.com/etiennestuder/gradle-jooq-plugin/issues/62#issuecomment-513394067
 * Consider alternative plugin with kotlin support:
 * https://github.com/rohanprabhu/kotlin-dsl-gradle-jooq-plugin
 */
apply(from = "useJooq.gradle")

repositories {
    mavenCentral()
    jcenter()
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
    maven { url = uri("http://dl.bintray.com/kotlin/kotlinx.coroutines") }
    maven { url = uri("http://dl.bintray.com/kotlin/kotlin-eap") }
    maven { url = uri("https://jitpack.io") }
}

dependencies {
//    ext.kotlinxcoroutines = "0.30.1"
    val querydsl = "4.2.1"

    compile("org.springframework.boot:spring-boot-starter-web")
    compile("org.springframework.boot:spring-boot-starter-data-jpa")

    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compile("org.jetbrains.kotlin:kotlin-reflect")
    compile("org.jetbrains.kotlin:kotlin-stdlib-common:1.3.41")
    compile("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.0-M2")

    implementation("org.postgresql:postgresql:42.2.6")
    compile("org.hibernate:hibernate-core")
    compile("javax.validation:validation-api:2.0.1.Final")
    compile("org.flywaydb:flyway-core")
    compile("com.querydsl:querydsl-jpa:${querydsl}")
    kapt("com.querydsl:querydsl-apt:${querydsl}:jpa")
    jooqRuntime("org.postgresql:postgresql:42.2.6")
    compile("org.jooq:jooq")
    compile("org.jooq:jooq-meta")
    compile("org.jooq:jooq-codegen")

    compile("com.fasterxml.jackson.module:jackson-module-kotlin")
    compile("org.jsoup:jsoup:1.12.1")
    compile("org.apache.commons:commons-text:1.5")

    testCompile("org.springframework.boot:spring-boot-starter-test")
    testCompile("io.projectreactor:reactor-test")
    testCompile("com.nhaarman:mockito-kotlin:1.6.0")
    testCompile("org.powermock:powermock-api-mockito2:2.0.2")
    testCompile("org.assertj:assertj-core:3.12.2")

    integrationTestImplementation("com.jayway.jsonpath:json-path:2.4.0")

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.0.0-RC16")

    testImplementation("org.springframework.cloud:spring-cloud-contract-wiremock")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.wrapper {
    gradleVersion = "5.6.1"
}
