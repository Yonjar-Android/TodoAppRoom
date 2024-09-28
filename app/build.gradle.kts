plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.androidx.room)
    alias(libs.plugins.android.ksp)
    id("kotlin-kapt")
    alias(libs.plugins.androidx.hilt)
}

android {
    namespace = "com.example.todoapproom"
    compileSdk = 35

    room {
        schemaDirectory("$projectDir/schemas")
    }

    defaultConfig {
        applicationId = "com.example.todoapproom"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.todoapproom.AndroidCustomRunner"
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

    packaging {
        resources {
            excludes += listOf(
                "/META-INF/LICENSE.md",
                "/META-INF/LICENSE",
                "/META-INF/ASL2.0",
                "/META-INF/DEPENDENCIES",
                "/META-INF/NOTICE",
                "/META-INF/NOTICE.txt",
                "/META-INF/ASL2.0",
                "META-INF/LICENSE-notice.md"
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
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    testImplementation(libs.kotlinx.coroutines.test)

    //Testing
    //Mockk

    testImplementation(libs.mockk)
    testImplementation (libs.mockk.android)
    testImplementation (libs.mockk.agent)

    //Turbine

    testImplementation(libs.turbine)

    //Navigation Component
    implementation(libs.androidx.navigation.compose)

    //Splash Screen
    implementation(libs.androidx.core.splashscreen.v120alpha02)

    //Room

    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    implementation(libs.androidx.room.paging)

    // To use Kotlin Symbol Processing (KSP)
    ksp(libs.androidx.room.compiler)

    //Dagger Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // For instrumented tests.
    androidTestImplementation(libs.hilt.android.testing)
    // ...with Kotlin.
    kaptAndroidTest(libs.hilt.android.compiler)

    // Paging 3
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)
    testImplementation(libs.androidx.paging.common)
}

kapt {
    correctErrorTypes = true
}