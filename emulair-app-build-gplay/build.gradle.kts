plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    kotlinOptions {
        jvmTarget = "1.8"
    }
    namespace = "com.bigbratan.emulair.ext"
}

dependencies {

    implementation(project(":emulair-app-common"))
    implementation(deps.libs.material)
    implementation(deps.libs.retrofit)
    implementation(deps.libs.play.featureDelivery)
    implementation(deps.libs.play.featureDeliveryKtx)
    implementation(deps.libs.play.review)
    implementation(deps.libs.play.reviewKtx)
    implementation(deps.libs.gdrive.apiClient)
    implementation(deps.libs.gdrive.apiClientAndroid)
    implementation(deps.libs.gdrive.apiServicesDrive)
    implementation(deps.libs.play.playServicesAuth)
    implementation(deps.libs.play.playServicesAuthApiPhone)
    implementation(deps.libs.play.playServicesTasks)
    implementation(deps.libs.play.playServicesBase)
    implementation(deps.libs.play.coroutine)
    implementation(deps.libs.androidx.lifecycle.commonJava8)
    kapt(deps.libs.androidx.lifecycle.processor)
    // implementation(deps.libs.androidx.leanback.leanback)
    implementation(deps.libs.androidx.appcompat.constraintLayout)
    implementation(deps.libs.dagger.core)
    implementation(deps.libs.kotlinxCoroutinesAndroid)
}
