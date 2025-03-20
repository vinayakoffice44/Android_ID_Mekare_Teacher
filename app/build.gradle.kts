plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.id_maker_teacher"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.id_maker_teacher"
        minSdk = 24
        targetSdk = 35
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("com.itextpdf:itext7-core:7.1.15")
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //bottom navigation bar chip
    implementation("com.github.ismaeldivita:chip-navigation-bar:1.4.0");

    // retro fit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0");
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0");
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.3");
    implementation ("com.google.code.gson:gson:2.8.9");
    //lotti animaiton
    implementation ("com.google.android.material:material:1.9.0");
    implementation ("com.airbnb.android:lottie:6.0.0");
    // glide for image loading
    implementation ("com.github.bumptech.glide:glide:4.15.1");
    annotationProcessor ("com.github.bumptech.glide:compiler:4.15.1");
    // circle image
    implementation("de.hdodenhof:circleimageview:3.1.0");
    //pdf maker

    implementation ("com.itextpdf:itextg:5.5.10");

    // cropin image
    implementation ("com.github.yalantis:ucrop:2.2.8");


}