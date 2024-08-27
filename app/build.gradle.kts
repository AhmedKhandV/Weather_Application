plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")


}

android {
    namespace = "com.example.weather"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.weather"
        minSdk = 24
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding =true // Enable View Binding
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation(libs.play.services.location)

    val room_version= "2.6.0"

    // Room components
    implementation ("androidx.room:room-runtime:$room_version")
     // For Java projects
    kapt ("androidx.room:room-compiler:$room_version") // For Kotlin projects

    // Optional - Kotlin Extensions and Coroutines support for Room
    implementation ("androidx.room:room-ktx:$room_version")

    // Hilt dependencies
    implementation ("com.google.dagger:hilt-android:2.48")
    kapt ("com.google.dagger:hilt-android-compiler:2.48")
    // Retrofit for network requests
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // Coroutines for asynchronous operations
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Glide for image loading
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    implementation(libs.androidx.room.ktx)
    kapt ("com.github.bumptech.glide:compiler:4.15.1")

    implementation("androidx.concurrent:concurrent-futures:1.2.0")
    // Hilt for dependency injection
    implementation("com.google.dagger.hilt.android:com.google.dagger.hilt.android.gradle.plugin:2.41")
    kapt ("com.google.dagger:hilt-android-compiler:2.41")
    implementation ("com.google.android.material:material:1.10.0")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.0")

    // Google Play services (for FusedLocationProviderClient)
    implementation ("com.google.android.gms:play-services-location:21.0.1")


    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.swiperefreshlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
