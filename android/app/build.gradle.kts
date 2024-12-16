plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}



android {
    namespace = "com.example.inzynierka1"
    compileSdk = 34

    androidResources {
        noCompress += "tflite"
    }

    defaultConfig {
        applicationId = "com.example.inzynierka1"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }


}

buildscript {
    repositories {
        // Add repositories if needed
        google()
        mavenCentral()
    }
    dependencies {
        // Add Mockito core library to the classpath
        classpath(libs.mockito.core)
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.ui.text.google.fonts)
    implementation(libs.androidx.datastore.preferences.core.jvm)
//    implementation(libs.litert)
    implementation(libs.firebase.firestore.ktx)

    implementation(libs.okhttp)
    implementation(libs.core)
    implementation(libs.core.ktx)


    testImplementation(libs.junit)
    testImplementation (libs.mockito.kotlin)
    testImplementation(libs.junit.v412)
    testImplementation(libs.mockk)
    testImplementation(libs.junit.jupiter)
    testImplementation (libs.mockito.mockito.inline)
    testImplementation (libs.kotlinx.coroutines.test)
    testImplementation (libs.robolectric)




    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation("org.mockito:mockito-core:3.12.4")

    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.0.5")
    androidTestImplementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.51.1")
    kaptAndroidTest("com.google.dagger:hilt-compiler:2.51.1")

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("androidx.compose.material3:material3:1.2.1")

    implementation("androidx.datastore:datastore-preferences:1.0.0")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    // hilt
    implementation ("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-compiler:2.51.1")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0-alpha03")

    implementation ("androidx.navigation:navigation-compose:2.5.3")

    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")

    implementation (libs.tensorflow.lite)
    implementation (libs.tensorflow.lite.support)
    implementation(libs.tensorflow.lite.gpu)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
