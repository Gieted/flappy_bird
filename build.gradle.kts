plugins {
    kotlin("jvm") version "1.5.0"
    id("application")
    id ("edu.sc.seis.launch4j") version "2.5.0"
}

group = "pl.gieted.flappy_bird"
version = "1.6"

application { 
    mainClass.set("pl.gieted.flappy_bird.MainKt")
}

launch4j {
    mainClassName = "pl.gieted.flappy_bird.MainKt"
    icon = "${projectDir}/favicon.ico"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(fileTree("libs") { include("*.jar") })
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0-RC")
}
