@file:Suppress("UNUSED_VARIABLE")

import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform

plugins {
    kotlin("multiplatform") version "1.5.31"
    id("edu.sc.seis.launch4j") version "2.5.0"
    id ("com.android.application") version "4.2.2"
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
    android()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(fileTree("libs") {
                    include("*.jar", "android/*.jar")
                })

                implementation("androidx.appcompat:appcompat:1.3.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
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

launch4j {
    mainClassName = "pl.gieted.flappy_bird.MainKt"
    icon = "${projectDir}/favicon.ico"
    bundledJrePath = "./jre"
    bundledJre64Bit = true
    jarTask = tasks["jvmJar"]
}

android {
    compileSdkVersion(31)
    defaultConfig {
        applicationId = group.toString()
        minSdkVersion(24)
        targetSdkVersion(31)
        versionName = version.toString()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    sourceSets {
        val main by getting {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            assets.srcDirs("src/androidMain/resources", "src/javaMain/resources", "src/commonMain/resources")
        }
    }
}

repositories {
    mavenCentral()
    google()
}
