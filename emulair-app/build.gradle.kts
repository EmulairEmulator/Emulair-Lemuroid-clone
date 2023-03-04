plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlinx-serialization")
}

android {
    defaultConfig {
        applicationId = "com.bigbratan.emulair"
        versionCode = 1
        versionName = "1.0.0"
    }

    // To test out the error screens, wrap `dynamicFeatures.addAll(...)` under `if (usePlayDynamicFeatures()) {..}`
    // This way, the cores in the debug version of the app will not be installed, and an error screen will appear
    // Follow similar instructions in settings.gradle.kts for changes to actually take effect
    dynamicFeatures.addAll(
        setOf(
            ":libretro_core_desmume",
            ":libretro_core_dosbox_pure",
            ":libretro_core_fbneo",
            ":libretro_core_fceumm",
            ":libretro_core_gambatte",
            ":libretro_core_genesis_plus_gx",
            ":libretro_core_handy",
            ":libretro_core_mame2003_plus",
            ":libretro_core_mednafen_ngp",
            ":libretro_core_mednafen_pce_fast",
            ":libretro_core_mednafen_wswan",
            ":libretro_core_melonds",
            ":libretro_core_mgba",
            ":libretro_core_mupen64plus_next_gles3",
            ":libretro_core_pcsx_rearmed",
            ":libretro_core_ppsspp",
            ":libretro_core_prosystem",
            ":libretro_core_snes9x",
            ":libretro_core_stella",
            ":libretro_core_citra"
        )
    )

    flavorDimensions("buildType", "coresType")

    productFlavors {

        create("nogplay") {
            dimension = "buildType"
        }

        create("gplay") {
            dimension = "buildType"
        }

        // Include cores in the final apk
        create("bundledCores") {
            dimension = "coresType"
        }

        // Download cores on demand (from GooglePlay or GitHub)
        create("downloadedCores") {
            dimension = "coresType"
        }
    }

    packagingOptions {
        doNotStrip("*/*/*_libretro_android.so")
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/library_release.kotlin_module")
    }

    signingConfigs {
        maybeCreate("debug").apply {
            storeFile = file("$rootDir/debug.keystore")
        }
        maybeCreate("release").apply {
            storeFile = file("$rootDir/release.jks")
            keyAlias = "emu...lator?"
            storePassword = "emu...lator?"
            keyPassword = "emu...lator?"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            signingConfig = signingConfigs["release"]
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            resValue("string", "app_name", "Emulair")
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
            resValue("string", "app_name", "Emulair D")
        }
    }

    lint {
        disable += setOf("MissingTranslation", "ExtraTranslation", "EnsureInitializerMetadata")
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":emulair-app-common"))
    "bundledCoresImplementation"(project(":bundled-cores"))
    "nogplayImplementation"(project(":emulair-app-build-nogplay"))
    "gplayImplementation"(project(":emulair-app-build-gplay"))

    implementation(deps.libs.material)
    implementation(deps.libs.androidx.navigation.navigationFragment)
    implementation(deps.libs.androidx.navigation.navigationUi)
    implementation(deps.libs.coil)
    implementation(deps.libs.androidx.appcompat.constraintLayout)
    implementation(deps.libs.androidx.activity.activity)
    implementation(deps.libs.androidx.activity.activityKtx)
    implementation(deps.libs.androidx.appcompat.appcompat)
    implementation(deps.libs.androidx.preferences.preferencesKtx)
    implementation(deps.libs.arch.work.runtime)
    implementation(deps.libs.arch.work.runtimeKtx)
    implementation(deps.libs.androidx.lifecycle.commonJava8)
    implementation(deps.libs.androidx.lifecycle.reactiveStreams)
    implementation(deps.libs.epoxy.expoxy)
    implementation(deps.libs.epoxy.paging)
    kapt(deps.libs.epoxy.processor)
    kapt(deps.libs.androidx.lifecycle.processor)
    // implementation(deps.libs.androidx.leanback.leanback)
    implementation(deps.libs.androidx.leanback.leanbackPreference)
    // implementation("androidx.preference:preference-ktx:1.1.0-rc01")
    // implementation(deps.libs.androidx.leanback.leanbackPaging)
    implementation(deps.libs.androidx.appcompat.recyclerView)
    implementation(deps.libs.androidx.paging.common)
    implementation(deps.libs.androidx.paging.runtime)
    implementation(deps.libs.androidx.room.common)
    implementation(deps.libs.androidx.room.runtime)
    implementation(deps.libs.androidx.room.ktx)
    implementation(deps.libs.dagger.android.core)
    implementation(deps.libs.dagger.android.support)
    implementation(deps.libs.dagger.core)
    implementation(deps.libs.kotlinxCoroutinesAndroid)
    implementation(deps.libs.okHttp3)
    implementation(deps.libs.okio)
    implementation(deps.libs.retrofit)
    implementation(deps.libs.flowPreferences)
    implementation(deps.libs.guava)
    implementation(deps.libs.androidx.documentfile)
    // implementation(deps.libs.androidx.leanback.tvProvider)
    implementation(deps.libs.harmony)
    implementation(deps.libs.startup)
    implementation(deps.libs.kotlin.serialization)
    implementation(deps.libs.kotlin.serializationJson)
    implementation(deps.libs.libretrodroid)
    kapt(deps.libs.dagger.android.processor)
    kapt(deps.libs.dagger.compiler)
    kapt(deps.libs.androidx.room.compiler)
    kapt("com.android.databinding:compiler:3.1.4")
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Uncomment this when using a local aar file.
    //implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
}

fun usePlayDynamicFeatures(): Boolean {
    val task = gradle.startParameter.taskRequests.toString()
    return task.contains("Gplay") && task.contains("DownloadedCores")
}
