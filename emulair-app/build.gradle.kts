plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    // id("safeargs.kotlin")
    id("kotlinx-serialization")
}

android {
    defaultConfig {
        applicationId = "com.bigbratan.emulair"
        versionCode = 4
        versionName = "0.0.4"
    }

    /*
    To test out the error screens, wrap `dynamicFeatures.addAll(...)` under `if (usePlayDynamicFeatures()) {..}`
    This way, the cores in the debug version of the app will not be installed, and an error screen will appear
    Follow similar instructions in settings.gradle.kts for changes to actually take effect
    */
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

    flavorDimensions.addAll(listOf("buildType", "coresType"))

    productFlavors {
        create("nogplay") {
            dimension = "buildType"
        }

        // Include cores in the final apk
        create("bundledCores") {
            dimension = "coresType"
        }

        // Download cores on demand from GitHub
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
            resValue("string", "app_name", "Emubug")
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

    // Google
    implementation(deps.libs.kotlinxCoroutinesAndroid)
    implementation(deps.libs.material)

    // Android
    implementation(deps.libs.appcompat)
    implementation(deps.libs.recyclerView)
    implementation(deps.libs.constraintLayout)
    implementation(deps.libs.preferenceKtx)
    implementation(deps.libs.pagingCommon)
    implementation(deps.libs.pagingRuntime)
    implementation(deps.libs.navigationFragmentKtx)
    implementation(deps.libs.navigationUiKtx)
    implementation(deps.libs.documentFile)
    implementation(deps.libs.activity)
    implementation(deps.libs.activityKtx)
    implementation(deps.libs.startupRuntime)
    implementation(deps.libs.leanbackPreference)
    kapt(deps.libs.databinding)

    // Compose
    implementation(platform(deps.libs.compose))
    implementation(deps.libs.composeUi)
    implementation(deps.libs.composeUiGraphics)
    implementation(deps.libs.composeUiToolingPreview)
    debugImplementation(deps.libs.composeUiTooling)
    debugImplementation(deps.libs.composeUiTestManifest)
    implementation(deps.libs.composeRuntimeLivedata)
    implementation(deps.libs.composeUiGoogleFonts)
    implementation(deps.libs.composeNavigation)
    implementation(deps.libs.composeMaterial)
    implementation(deps.libs.composeMaterial3)
    implementation(deps.libs.composeMaterialIconsExtended)

    // Lifecycle
    implementation(deps.libs.lifecycleCommonJava8)
    kapt(deps.libs.lifecycleRuntimeKtx)
    implementation(deps.libs.lifecycleReactiveStreams)
    implementation(deps.libs.roomCommon)
    implementation(deps.libs.roomRuntime)
    kapt(deps.libs.roomCompiler)
    implementation(deps.libs.roomKtx)

    // Dependency Injection
    implementation(deps.libs.dagger)
    kapt(deps.libs.daggerCompiler)
    kapt(deps.libs.daggerAndroid)
    implementation(deps.libs.daggerAndroidProcessor)
    implementation(deps.libs.daggerAndroidSupport)
    implementation(deps.libs.workRuntime)
    implementation(deps.libs.workRuntimeKtx)

    // API
    implementation(deps.libs.okHttp3)
    implementation(deps.libs.okio)
    implementation(deps.libs.coil)
    implementation(deps.libs.retrofit)
    implementation(deps.libs.kotlinxSerializationCore)
    implementation(deps.libs.kotlinxSerializationJson)

    // Extras
    implementation(deps.libs.expoxy)
    implementation(deps.libs.epoxyPaging)
    kapt(deps.libs.epoxyProcessor)
    implementation(deps.libs.libretroDroid)
    implementation(deps.libs.flowPreferences)
    implementation(deps.libs.guava)
    implementation(deps.libs.harmony)
    implementation(deps.libs.circleImageView)

    // Uncomment this when using a local .aar file
    // implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
}
