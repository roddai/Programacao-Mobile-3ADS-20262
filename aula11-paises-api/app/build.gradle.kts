import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
}

// Lê a chave da API do local.properties (arquivo que não vai para o GitHub)
val localProperties = Properties()
val arquivoLocal = rootProject.file("local.properties")
if (arquivoLocal.exists()) {
    arquivoLocal.inputStream().use { localProperties.load(it) }
}

android {
    namespace = "com.example.aula11_paises_api_pt2"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.aula11_paises_api_pt2"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "REST_COUNTRIES_KEY",
            "\"" + localProperties.getProperty("REST_COUNTRIES_KEY", "") + "\""
        )
    }

    buildFeatures {
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
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}