import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
}

android {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    api(deps.libs.androidx.lifecycle.commonJava8)
    implementation(deps.libs.arch.work.runtime)
    implementation(deps.libs.arch.work.runtimeKtx)
    implementation(deps.libs.androidx.appcompat.appcompat)
    implementation(deps.libs.androidx.leanback.leanbackPreference)
    // implementation("androidx.preference:preference-ktx:1.1.0-rc01")
    implementation(deps.libs.androidx.ktx.collection)
    implementation(deps.libs.androidx.ktx.core)
    implementation(deps.libs.androidx.ktx.coreKtx)
    implementation(deps.libs.androidx.fragment.fragment)
    implementation(deps.libs.androidx.fragment.ktx)
    implementation(deps.libs.androidx.activity.activity)
    implementation(deps.libs.androidx.activity.activityKtx)
    implementation(deps.libs.androidx.ktx.coreKtx)
    implementation(deps.libs.androidx.paging.common)
    implementation(deps.libs.androidx.paging.runtime)
    implementation(deps.libs.androidx.room.runtime)
    implementation(deps.libs.androidx.room.ktx)
    implementation(deps.libs.androidx.room.paging)
    implementation(deps.libs.androidx.documentfile)
    implementation(deps.libs.dagger.android.core)
    implementation(deps.libs.dagger.android.support)
    implementation(deps.libs.okHttp3)
    implementation(deps.libs.okio)
    implementation(deps.libs.retrofit)
    implementation(deps.libs.kotlin.serialization)
    implementation(deps.libs.kotlin.serializationJson)
    implementation(deps.libs.harmony)
    implementation(deps.libs.multitouchGestures)
    implementation(deps.libs.material)
    implementation(deps.libs.kotlinxCoroutinesAndroid)
    kapt(deps.libs.androidx.room.compiler)

    implementation(deps.libs.androidx.appcompat.constraintLayout)
    implementation(deps.libs.androidx.appcompat.appcompat)
    implementation(deps.libs.androidx.lifecycle.commonJava8)
    implementation(deps.libs.material)
    implementation(deps.libs.androidx.preferences.preferencesKtx)
    api(deps.libs.radialgamepad)
    implementation(kotlin(deps.libs.kotlin.stdlib))
    kapt(deps.libs.androidx.lifecycle.processor)

    api(deps.libs.timber)
    implementation(deps.libs.androidx.appcompat.appcompat)
    implementation(deps.libs.androidx.appcompat.recyclerView)
    implementation(deps.libs.androidx.room.runtime)
    implementation(deps.libs.androidx.documentfile)
    implementation(deps.libs.androidx.preferences.preferencesKtx)
    implementation(deps.libs.androidx.lifecycle.runtime)
    implementation(deps.libs.kotlinxCoroutinesAndroid)
    implementation(deps.libs.okHttp3)
    implementation(deps.libs.androidx.paging.common)
    implementation(deps.libs.androidx.paging.runtime)
}

android {
    defaultConfig {
        javaCompileOptions {
            annotationProcessorOptions {
                argument("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }
    kotlinOptions {
        this as KotlinJvmOptions
        jvmTarget = "1.8"
    }
}
