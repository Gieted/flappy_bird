@file:Suppress("UNUSED_VARIABLE")

import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform

plugins {
    kotlin("multiplatform") version "1.8.10"
}

group = "pl.gieted.flappy_bird"
version = "1.14-SNAPSHOT"

repositories {
    mavenCentral()
    google()
}

kotlin {
    jvm {
        compilations {
            create("development")
        }
    }
    js(IR) {
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
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(fileTree("libs") {
                    include("*.jar", "desktop/*.jar")
                    if (DefaultNativePlatform.getCurrentOperatingSystem().isWindows) {
                        include("desktop/windows/*.jar")
                    } else {
                        include("desktop/linux/*.jar")
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
