@file:Suppress("UNUSED_VARIABLE")

import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform

plugins {
    kotlin("multiplatform") version "1.5.31"
    id("edu.sc.seis.launch4j") version "2.5.0"
}

group = "pl.gieted.flappy_bird"
version = "1.12-SNAPSHOT"

kotlin {
    jvm {
        compilations {
            val main by getting

            val development by creating
        }
    }
    js {
        binaries.executable()
        browser {
            commonWebpackConfig {
                devServer?.open = false
            }
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
        val jsMain by getting {
            dependencies {
                implementation(npm("p5", "^1.4.0"))
                compileOnly(npm("@types/p5", "^1.3.1"))
            }
        }
        val jvmDevelopment by getting {
            dependsOn(jvmMain)
            
            dependencies {
                implementation(kotlin("reflect"))
            }
        }
    }
}

launch4j {
    mainClassName = "pl.gieted.flappy_bird.MainKt"
    icon = "${projectDir}/favicon.ico"
    bundledJrePath = "./jre"
    bundledJre64Bit = true
    jarTask = tasks["jvmJar"]
}

repositories {
    mavenCentral()
}
