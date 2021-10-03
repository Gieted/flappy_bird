import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform

plugins {
    kotlin("multiplatform") version "1.5.31"
    id("application")
    id("edu.sc.seis.launch4j") version "2.5.0"
}

group = "pl.gieted.flappy_bird"
version = "1.11-SNAPSHOT"

application {
    mainClass.set("pl.gieted.flappy_bird.MainKt")
}

launch4j {
    mainClassName = "pl.gieted.flappy_bird.MainKt"
    icon = "${projectDir}/favicon.ico"
    bundledJrePath = "./jre"
    bundledJre64Bit = true
}

kotlin {
    jvm {
        testRuns["test"].executionTask.configure {
            useJUnit()
        }
    }
    js(IR) {
        binaries.executable()
        browser {
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(fileTree("libs") {
                    include("*.jar")
                    if (DefaultNativePlatform.getCurrentOperatingSystem().isWindows) {
                        include("windows/*.jar")
                    } else {
                        include("linux/*.jar")
                    }
                })
            }
        }
        val jvmTest by getting
        val jsMain by getting {
            dependencies {
                implementation(npm("p5", "^1.4.0"))
                implementation(npm("@types/p5", "^1.3.1"))
            }
        }
        val jsTest by getting
    }
}

repositories {
    mavenCentral()
}
