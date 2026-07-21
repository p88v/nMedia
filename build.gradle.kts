plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    kotlin("jvm") version "2.4.0"
    id("com.google.devtools.ksp") version "2.3.9" apply false
    id("com.google.gms.google-services") version "4.5.0" apply false
    alias(libs.plugins.kotlin.compose) apply false
}