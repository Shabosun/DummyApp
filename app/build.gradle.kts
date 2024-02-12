plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
    id("kotlin-android")
    //id("com.google.devtools.ksp")

}

android {
    namespace = "com.example.dummyapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.dummyapp"
        minSdk = 24
        targetSdk = 34
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
    buildFeatures{
        viewBinding = true
        //dataBinding = true
    }
}

dependencies {

    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    val nav_version = "2.7.4"
    val room_version = "2.6.1"

    //RxJava
    implementation("io.reactivex.rxjava3:rxjava:3.1.8")
    implementation("io.reactivex.rxjava3:rxandroid:3.0.2")



    //Other
    implementation("androidx.preference:preference-ktx:1.2.1")
    implementation("com.google.firebase:firebase-messaging-ktx:23.3.1")
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.0")


    //CircleImage
    implementation ("de.hdodenhof:circleimageview:3.1.0")


    //Room
    implementation ("androidx.room:room-runtime:$room_version")
    implementation ("androidx.room:room-ktx:$room_version")
    implementation ("androidx.room:room-testing:$room_version")
    implementation("androidx.room:room-rxjava3:$room_version")
    //ksp("androidx.room:room-compiler:2.5.0")
    //annotationProcessor("androidx.room:room-compiler:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation ("androidx.compose.runtime:runtime-livedata:1.5.4")


    //Retrofit+OkHttp
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.7.2")
    implementation ("com.squareup.okhttp3:okhttp:4.7.2")
    implementation("com.squareup.picasso:picasso:2.8")

    //Dagger
    implementation("com.google.dagger:hilt-android:2.46.1")
   // kapt("com.google.dagger:hilt-compiler:2:46.1")


    //Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")


    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}