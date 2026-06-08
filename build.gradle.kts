// build.gradle (Project-level)

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}

buildscript {
    dependencies {
        classpath ("com.google.gms:google-services:4.4.2")// veya en güncel sürümü
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()

    }
}
