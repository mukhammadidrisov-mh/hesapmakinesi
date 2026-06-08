plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.era.hesapmakinesi"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.era.hesapmakinesi"
        minSdk = 26
        targetSdk = 34
        versionCode = 10
        versionName = "1.5"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("org.tensorflow:tensorflow-lite:2.15.0")
    implementation("org.tensorflow:tensorflow-lite-support:0.4.4")



    // Matematik hesaplama
    implementation("net.objecthunter:exp4j:0.4.8")

    // AndroidX ve UI
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.cardview)
    implementation(libs.androidx.ui.text.android)
    implementation(libs.pose.detection.common)
    implementation(libs.pose.detection)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")
    // ✅ AdMob (Google Mobile Ads SDK)
    implementation("com.google.android.gms:play-services-ads:24.2.0")
    implementation("net.objecthunter:exp4j:0.4.8")

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}


