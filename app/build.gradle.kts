plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.gamememoria"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.gamememoria"
        minSdk = 28
        targetSdk = 33
        versionCode = 1
        versionName = "3.1"

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
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation ("com.google.android.gms:play-services-ads-identifier:18.0.1")

    implementation ("com.my.target:mytarget-sdk:5.20.1")

    implementation ("com.my.target:mytarget-sdk:3.2.0")

    implementation ("com.google.android.exoplayer:exoplayer-core:2.19.1")

    implementation ("com.google.android.exoplayer:exoplayer-hls:2.19.1")

    implementation ("androidx.recyclerview:recyclerview:1.3.2")

     /* implementation ("com.google.android.gms:play-services-ads:23.0.0")*/


// ПОСТРОЕНИЕ ДИАГРАММ
// Для просмотра карты
    implementation("androidx.cardview:cardview:1.0.0")

// Библиотека диаграмм и графиков
    implementation("com.github.blackfizz:eazegraph:1.2.5l@aar")
    implementation("com.nineoldandroids:library:2.4.0")


}