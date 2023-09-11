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

    // Google
    implementation(deps.libs.review)
    implementation(deps.libs.reviewKtx)
    implementation(deps.libs.featureDelivery)
    implementation(deps.libs.featureDeliveryKtx)
    implementation(deps.libs.material)
    implementation(deps.libs.playServicesAuth)
    implementation(deps.libs.kotlinxCoroutinesPlayServices)
    implementation(deps.libs.kotlinxCoroutinesAndroid)

    // Android
    implementation(deps.libs.constraintLayout)

    // API
    implementation(deps.libs.retrofit)
    implementation(deps.libs.googleApiClient)
    implementation(deps.libs.googleApiClientAndroid)
    implementation(deps.libs.googleApiServicesDrive)

    // Lifecycle
    implementation(deps.libs.lifecycleCommonJava8)
    kapt(deps.libs.lifecycleCompiler)

    // Dependency Injection
    implementation(deps.libs.dagger)
}
