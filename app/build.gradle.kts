plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.fetchrewardscodingexercisesoftwareengineeringmobile"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.fetchrewardscodingexercisesoftwareengineeringmobile"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Compose dependencies
    implementation (libs.ui)
    implementation (libs.androidx.lifecycle.viewmodel.compose)
    implementation (libs.androidx.navigation.compose)

    // Retrofit and GSON
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    // Hilt for Dependency Injection
    implementation(libs.hilt.android.v2511)
    implementation(libs.androidx.junit.ktx)
    testImplementation(libs.androidx.ui.test.junit4.android)
    kapt(libs.hilt.android.compiler.v2511)
    implementation (libs.androidx.hilt.navigation.compose)

    // Coroutines
    implementation (libs.kotlinx.coroutines.core)
    implementation (libs.kotlinx.coroutines.android)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Unit Testing
    testImplementation (libs.mockito.core)
    testImplementation (libs.mockito.inline)
    testImplementation (libs.kotlinx.coroutines.test)
    testImplementation(libs.androidx.core.testing)


    // Compose UI testing
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.5.0")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:1.5.0")

    // Espresso (for UI testing)
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("androidx.test:runner:1.5.2")

    // JUnit extensions
    androidTestImplementation ("androidx.test.ext:junit:1.1.5'")

    // Mockito for mocking
    testImplementation ("org.mockito:mockito-core:4.8.1")
    testImplementation ("org.mockito.kotlin:mockito-kotlin:4.1.0")

    // Coroutines testing
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")


    // Android Instrumentation Tests
    androidTestImplementation (libs.ui.test.junit4)
    debugImplementation (libs.ui.test.manifest)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}