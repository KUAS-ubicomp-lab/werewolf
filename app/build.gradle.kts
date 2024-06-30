plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.werewolf"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.werewolf"
        minSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        vectorDrawables {
            useSupportLibrary = true
        }

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.core:core-ktx:1.13.1")
    implementation ("androidx.fragment:fragment-ktx:1.8.0")
    implementation ("androidx.work:work-runtime:2.9.0")
    implementation("dev.jamesyox:kastro:0.2.0")
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation ("io.coil-kt:coil-gif:2.1.0")
    implementation ("androidx.health.connect:connect-client:1.1.0-alpha07")
    implementation ("androidx.health:health-services-client:1.0.0-rc02")
    implementation ("com.google.guava:guava:33.2.1-android")
    implementation ("androidx.concurrent:concurrent-futures-ktx:1.2.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.8.2")
    implementation ("androidx.lifecycle:lifecycle-service:2.8.2")
    implementation ("androidx.wear:wear:1.3.0")
//    implementation ("com.google.dagger:hilt-android:$hilt_version")
//    kapt ("com.google.dagger:hilt-android-compiler:$hilt_version")
    implementation ("androidx.hilt:hilt-work:1.2.0")
//    kapt 'androidx.hilt:hilt-compiler:1.2.0'
    implementation(libs.play.services.wearable)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.tooling.preview)
    implementation(libs.compose.material)
    implementation(libs.compose.foundation)
    implementation(libs.activity.compose)
    implementation(libs.core.splashscreen)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}