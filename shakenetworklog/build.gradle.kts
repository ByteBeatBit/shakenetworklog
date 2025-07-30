import ktsSupport.*

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "it.icemangp.shakenetworklog"
    compileSdk = AppConfig.compileSdkVersion

    defaultConfig {
        AppConfig.setupLibraryDefaultConfig(this)
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_21.toString()
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    // Test
    testImplementation("junit:junit:${LibraryVers.junitVersion}")

    // Android test
    androidTestImplementation("androidx.test.ext:junit:${LibraryVers.androidXTestExtJunitVersion}")

    // Core
    implementation("androidx.core:core-ktx:${LibraryVers.ktxVersion}")
    implementation("androidx.appcompat:appcompat:${LibraryVers.appCompatVersion}")

    // Layout
    implementation("com.google.android.material:material:${LibraryVers.materialVersion}")
    implementation("androidx.constraintlayout:constraintlayout:${LibraryVers.constraintVersion}")

    compileOnly("com.squareup.okhttp3:logging-interceptor:${LibraryVers.httpLoggingInterceptorVersion}")
}