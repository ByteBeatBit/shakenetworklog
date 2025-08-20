plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    `kotlin-dsl-precompiled-script-plugins`
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    compileOnly(kotlin("gradle-plugin"))
    implementation("com.android.tools.build:gradle:8.7.3")
}
