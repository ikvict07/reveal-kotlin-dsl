import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.10"
    application
}

group = "com.jikvict.slides"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.9.1")
    implementation("com.google.code.gson:gson:2.10.1")
    
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

application {
    mainClass.set("com.jikvict.slides.v2.DemoPresentationKt")
}

// Task to generate presentation HTML from Kotlin code
tasks.register("generatePresentation") {
    group = "presentation"
    description = "Generate reveal.js presentation from Kotlin DSL"
    
    dependsOn("compileKotlin")
    
    doLast {
        javaexec {
            classpath = sourceSets["main"].runtimeClasspath
            mainClass.set("com.jikvict.slides.v2.DemoPresentationKt")
        }
    }
}

// Task to serve the presentation locally
tasks.register("serve") {
    group = "presentation"
    description = "Serve the generated presentation locally"
    
    dependsOn("generatePresentation")
    
    doLast {
        exec {
            workingDir = file("reveal.js")
            commandLine("npm", "start")
        }
    }
}

// Task for continuous development using Gradle's continuous build
// Usage: ./gradlew liveUpdate (will recompile and regenerate on changes)
tasks.register("liveUpdate") {
    group = "presentation"
    description = "Continuously watch for code changes and regenerate presentation (uses --continuous)"

    doLast {
        println("🚀 Starting live update (continuous) — press Ctrl+C to stop")
        // Use Gradle wrapper to run generatePresentation with --continuous so compileKotlin re-runs
        val gradleWrapper = if (System.getProperty("os.name").lowercase().contains("win")) "gradlew.bat" else "./gradlew"
        exec {
            commandLine(gradleWrapper, "generatePresentation", "--continuous")
        }
    }
}

// Legacy dev task (kept for compatibility) — forwards to liveUpdate
tasks.register("dev") {
    group = "presentation"
    description = "Alias for liveUpdate"
    dependsOn("liveUpdate")
}