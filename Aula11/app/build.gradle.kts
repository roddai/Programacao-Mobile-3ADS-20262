plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.aula11_desafio_apismoedaepases"
    // Atualizado para 36 conforme exigido pelas bibliotecas activity:1.13.0 e core:1.18.0
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.aula11_desafio_apismoedaepases"
        minSdk = 24
        targetSdk = 35 // Mantido conforme instrução, a menos que o build exija target 36
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.glide)
    implementation(libs.volley)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
