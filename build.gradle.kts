plugins {
    kotlin("jvm") version "1.5.0"
    id("application")
}

group = "pl.gieted.flappy_bird"
version = "1.0-SNAPSHOT"

application { 
    mainClass.set("pl.gieted.flappy_bird.MainKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(fileTree("libs") { include("*.jar") })
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0-RC")
}
