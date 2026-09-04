import java.util.Properties
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    id("com.google.devtools.ksp") version "2.3.9"

    alias(libs.plugins.kotlin.compose)
}


val localProperties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}


val apiKey = localProperties.getProperty("API_KEY") ?: ""

android {
    namespace = "ru.netology.nmedia"
    compileSdk = 37

    defaultConfig {
        applicationId = "ru.netology.nmedia"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        buildConfigField(
            "String",
            "API_KEY",
            "\"$apiKey\""
        )

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
        compose = true
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
}

dependencies {

    implementation(libs.androidx.swiperefreshlayout)
    val navVersion = "2.8.9"
    val roomVersion = "2.8.4"
    val lifecycleVersion = "2.10.0"
    val coroutinesVersion = "1.11.0"

    // AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)


    //Glide
    implementation("com.github.bumptech.glide:glide:5.0.9")

    // Material
    implementation("com.google.android.material:material:1.12.0")
    implementation(libs.material)

    // Navigation
    implementation(
        "androidx.navigation:navigation-fragment-ktx:$navVersion"
    )
    implementation(
        "androidx.navigation:navigation-ui-ktx:$navVersion"
    )

    // Room
    implementation(
        "androidx.room:room-runtime:$roomVersion"
    )
    implementation(
        "androidx.room:room-ktx:$roomVersion"
    )
    ksp(
        "androidx.room:room-compiler:$roomVersion"
    )

    // Lifecycle / ViewModel
    implementation(
        "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
    )

    // Coroutines
    implementation(
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
    )

    // Retrofit
    implementation(
        "com.squareup.retrofit2:retrofit:2.11.0"
    )
    implementation(
        "com.squareup.retrofit2:converter-gson:2.11.0"
    )

    // OkHttp
    implementation(
        "com.squareup.okhttp3:logging-interceptor:4.12.0"
    )

    // Gson
    implementation(
        "com.google.code.gson:gson:2.11.0"
    )



    // Compose
    implementation(
        platform(libs.androidx.compose.bom)
    )
    implementation(
        libs.androidx.activity.compose
    )
    implementation(
        libs.androidx.compose.material3
    )
    implementation(
        libs.androidx.compose.ui
    )
    implementation(
        libs.androidx.compose.ui.graphics
    )
    implementation(
        libs.androidx.compose.ui.tooling.preview
    )

    // Tests
    testImplementation(
        libs.junit
    )

    androidTestImplementation(
        platform(libs.androidx.compose.bom)
    )
    androidTestImplementation(
        libs.androidx.compose.ui.test.junit4
    )
    androidTestImplementation(
        libs.androidx.junit
    )
    androidTestImplementation(
        libs.androidx.espresso.core
    )

    debugImplementation(
        libs.androidx.compose.ui.test.manifest
    )
    debugImplementation(
        libs.androidx.compose.ui.tooling
    )
}