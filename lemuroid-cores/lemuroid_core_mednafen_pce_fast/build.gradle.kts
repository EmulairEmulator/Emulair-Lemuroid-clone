
plugins {
    id("com.android.dynamic-feature")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    defaultConfig {
        missingDimensionStrategy("buildType", "gplay")
        missingDimensionStrategy("coresType", "downloadedCores")
    }
    packagingOptions {
        doNotStrip("*/*/*_libretro_android.so")
    }
}

dependencies {
    implementation(project(":emulair-app"))
    implementation(kotlin(deps.libs.kotlin.stdlib))
}
