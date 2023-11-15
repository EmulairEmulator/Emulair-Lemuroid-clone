plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(":emulair-app-common"))
    implementation(deps.libs.retrofit)
    implementation(deps.libs.kotlinxCoroutinesAndroid)
}
