import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform

plugins {
    kotlin("jvm") version "1.5.0"
    id("application")
    id("edu.sc.seis.launch4j") version "2.5.0"
}

group = "pl.gieted.flappy_bird"
version = "1.9-SNAPSHOT"

application {
    mainClass.set("pl.gieted.flappy_bird.MainKt")
}

launch4j {
    mainClassName = "pl.gieted.flappy_bird.MainKt"
    icon = "${projectDir}/favicon.ico"
    bundledJrePath = "./jre"
    bundledJre64Bit = true
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation(fileTree("libs") {
        include("*.jar")
        if (DefaultNativePlatform.getCurrentOperatingSystem().isWindows) {
            include("windows/*.jar")
        } else {
            include("linux/*.jar")
        }
    })

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0-RC")
}
