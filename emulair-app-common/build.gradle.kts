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
    // Google
    implementation(kotlin(deps.libs.stdlib))
    implementation(deps.libs.collectionKtx)
    implementation(deps.libs.coreKtx)
    implementation(deps.libs.kotlinxCoroutinesAndroid)
    implementation(deps.libs.material)

    // Android
    implementation(deps.libs.appcompat)
    implementation(deps.libs.recyclerView)
    implementation(deps.libs.constraintLayout)
    implementation(deps.libs.preferenceKtx)
    implementation(deps.libs.pagingCommon)
    implementation(deps.libs.pagingRuntime)
    implementation(deps.libs.fragment)
    implementation(deps.libs.fragmentKtx)
    implementation(deps.libs.documentFile)
    implementation(deps.libs.activity)
    implementation(deps.libs.activityKtx)
    implementation(deps.libs.leanbackPreference)

    // Lifecycle
    api(deps.libs.lifecycleCommonJava8)
    kapt(deps.libs.lifecycleCompiler)
    implementation(deps.libs.lifecycleRuntimeKtx)
    implementation(deps.libs.roomRuntime)
    kapt(deps.libs.roomCompiler)
    implementation(deps.libs.roomPaging)
    implementation(deps.libs.roomKtx)

    // Dependency Injection
    implementation(deps.libs.daggerAndroid)
    implementation(deps.libs.daggerAndroidSupport)
    implementation(deps.libs.workRuntime)
    implementation(deps.libs.workRuntimeKtx)

    // API
    implementation(deps.libs.okHttp3)
    implementation(deps.libs.okio)
    implementation(deps.libs.retrofit)
    implementation(deps.libs.kotlinxSerializationCore)
    implementation(deps.libs.kotlinxSerializationJson)

    // Extras
    api(deps.libs.radialGamePad)
    api(deps.libs.timber)
    implementation(deps.libs.multitouchGestures)
    implementation(deps.libs.harmony)
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
        this
        jvmTarget = "1.8"
    }
}
