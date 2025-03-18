plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.svalero.tournaments"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.svalero.tournaments"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
    implementation("com.mapbox.maps:android:10.9.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.4.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation ("androidx.room:room-runtime:2.4.3")
    annotationProcessor ("androidx.room:room-compiler:2.4.3")
}