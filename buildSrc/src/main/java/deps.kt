/* ktlint-disable no-multi-spaces max-line-length */
object deps {
    object android {
        const val targetSdkVersion              = 33
        const val compileSdkVersion             = 33
        const val minSdkVersion                 = 24
        const val buildToolsVersion             = "30.0.2"
    }

    object versions {
        // Google
        const val kotlin                        = "1.6.21"
        const val gms                           = "17.0.0"

        // Android
        const val navigation                    = "2.3.5"
        const val paging                        = "3.1.1"
        const val fragment                      = "1.5.1"
        const val activity                      = "1.5.1"
        const val leanback                      = "1.1.0-rc01"

        // Compose
        const val compose                       = "2023.03.00"

        // Lifecycle
        const val lifecycle                     = "2.5.1"
        const val room                          = "2.4.2"

        // Dependency Injection
        const val dagger                        = "2.19"
        const val work                          = "2.7.1"

        // API
        const val okHttp                        = "4.9.1"
        const val retrofit                      = "2.9.0"
        const val googleApiClient               = "1.32.1"
        const val serialization                 = "1.2.2"

        // Extras
        const val epoxy                         = "4.6.3-vinay-compose"
        const val libretroDroid                 = "0.9.0"
        const val radialGamePad                 = "2.0.0"
    }

    object libs {
        // Google
        const val stdlib                        = "stdlib"
        const val collectionKtx                 = "androidx.collection:collection-ktx:1.1.0"
        const val coreKtx                       = "androidx.core:core-ktx:1.8.0"
        const val review                        = "com.google.android.play:review:2.0.0"
        const val reviewKtx                     = "com.google.android.play:review-ktx:2.0.0"
        const val featureDelivery               = "com.google.android.play:feature-delivery:2.0.0"
        const val featureDeliveryKtx            = "com.google.android.play:feature-delivery-ktx:2.0.0"
        const val playServicesAuth              = "com.google.android.gms:play-services-auth:17.0.0"
        const val kotlinxCoroutinesPlayServices = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4"
        const val kotlinxCoroutinesAndroid      = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4"
        const val material                      = "com.google.android.material:material:1.9.0-beta01"

        // Android
        const val appcompat                     = "androidx.appcompat:appcompat:1.4.2"
        const val recyclerView                  = "androidx.recyclerview:recyclerview:1.2.1"
        const val constraintLayout              = "androidx.constraintlayout:constraintlayout:2.1.4"
        const val preferenceKtx                 = "androidx.preference:preference-ktx:1.1.1"
        const val pagingCommon                  = "androidx.paging:paging-common:${versions.paging}"
        const val pagingRuntime                 = "androidx.paging:paging-runtime:${versions.paging}"
        const val navigationFragmentKtx         = "androidx.navigation:navigation-fragment-ktx:${versions.navigation}"
        const val navigationUiKtx               = "androidx.navigation:navigation-ui-ktx:${versions.navigation}"
        const val navigationSafeArgs            = "androidx.navigation:navigation-safe-args-gradle-plugin:${versions.navigation}"
        const val fragment                      = "androidx.fragment:fragment:${versions.fragment}"
        const val fragmentKtx                   = "androidx.fragment:fragment-ktx:${versions.fragment}"
        const val documentFile                  = "androidx.documentfile:documentfile:1.0.1"
        const val activity                      = "androidx.activity:activity:${versions.activity}"
        const val activityKtx                   = "androidx.activity:activity-ktx:${versions.activity}"
        const val startupRuntime                = "androidx.startup:startup-runtime:1.1.1"
        const val leanbackPreference            = "androidx.leanback:leanback-preference:${versions.leanback}"
        const val databinding                   = "com.android.databinding:compiler:3.1.4"

        // Compose
        const val compose                       = "androidx.compose:compose-bom:${versions.compose}"
        const val composeUi                     = "androidx.compose.ui:ui"
        const val composeUiGraphics             = "androidx.compose.ui:ui-graphics"
        const val composeUiToolingPreview       = "androidx.compose.ui:ui-tooling-preview"
        const val composeUiTooling              = "androidx.compose.ui:ui-tooling"
        const val composeUiTestManifest         = "androidx.compose.ui:ui-test-manifest"
        const val composeUiGoogleFonts          = "androidx.compose.ui:ui-text-google-fonts"
        const val composeRuntimeLivedata        = "androidx.compose.runtime:runtime-livedata"
        const val composeNavigation             = "androidx.navigation:navigation-compose:2.6.0"
        const val composeMaterial               = "androidx.compose.material:material:1.4.3"
        const val composeMaterial3              = "androidx.compose.material3:material3:1.1.1"
        const val composeMaterialIconsExtended  = "androidx.compose.material:material-icons-extended:1.4.3"

        // Lifecycle
        const val lifecycleCommonJava8          = "androidx.lifecycle:lifecycle-common-java8:${versions.lifecycle}"
        const val lifecycleCompiler             = "androidx.lifecycle:lifecycle-compiler:${versions.lifecycle}"
        const val lifecycleRuntimeKtx           = "androidx.lifecycle:lifecycle-runtime-ktx:${versions.lifecycle}"
        const val lifecycleReactiveStreams      = "android.arch.lifecycle:reactivestreams:1.1.1"
        const val roomCommon                    = "androidx.room:room-common:${versions.room}"
        const val roomRuntime                   = "androidx.room:room-runtime:${versions.room}"
        const val roomCompiler                  = "androidx.room:room-compiler:${versions.room}"
        const val roomPaging                    = "androidx.room:room-paging:${versions.room}"
        const val roomKtx                       = "androidx.room:room-ktx:${versions.room}"

        // Dependency Injection
        const val dagger                        = "com.google.dagger:dagger:${versions.dagger}"
        const val daggerCompiler                = "com.google.dagger:dagger-compiler:${versions.dagger}"
        const val daggerAndroid                 = "com.google.dagger:dagger-android:${versions.dagger}"
        const val daggerAndroidProcessor        = "com.google.dagger:dagger-android-processor:${versions.dagger}"
        const val daggerAndroidSupport          = "com.google.dagger:dagger-android-support:${versions.dagger}"
        const val workRuntime                   = "androidx.work:work-runtime:${versions.work}"
        const val workRuntimeKtx                = "androidx.work:work-runtime-ktx:${versions.work}"

        // API
        const val googleApiClient               = "com.google.api-client:google-api-client:${versions.googleApiClient}"
        const val googleApiClientAndroid        = "com.google.api-client:google-api-client-android:${versions.googleApiClient}"
        const val googleApiServicesDrive        = "com.google.apis:google-api-services-drive:v3-rev20210725-${versions.googleApiClient}"
        const val okHttp3                       = "com.squareup.okhttp3:okhttp:${versions.okHttp}"
        const val okio                          = "com.squareup.okio:okio:2.10.0"
        const val coil                          = "io.coil-kt:coil:1.4.0"
        const val retrofit                      = "com.squareup.retrofit2:retrofit:${versions.retrofit}"
        const val kotlinxSerializationCore      = "org.jetbrains.kotlinx:kotlinx-serialization-core:${versions.serialization}"
        const val kotlinxSerializationJson      = "org.jetbrains.kotlinx:kotlinx-serialization-json:${versions.serialization}"

        // Extras
        const val expoxy                        = "com.airbnb.android:epoxy:${versions.epoxy}"
        const val epoxyPaging                   = "com.airbnb.android:epoxy-paging:${versions.epoxy}"
        const val epoxyProcessor                = "com.airbnb.android:epoxy-processor:${versions.epoxy}"
        const val radialGamePad                 = "com.github.Swordfish90:RadialGamePad:${versions.radialGamePad}"
        const val libretroDroid                 = "com.github.Swordfish90:LibretroDroid:${versions.libretroDroid}"
        const val ktlint                        = "com.github.shyiko:ktlint:0.29.0"
        const val flowPreferences               = "com.fredporciuncula:flow-preferences:1.8.0"
        const val timber                        = "com.jakewharton.timber:timber:5.0.1"
        const val multitouchGestures            = "com.dinuscxj:multitouchgesturedetector:1.0.0"
        const val guava                         = "com.google.guava:guava:30.1.1-android"
        const val harmony                       = "com.frybits.harmony:harmony:1.1.9"
        const val circleImageView               = "de.hdodenhof:circleimageview:3.1.0"
    }

    object plugins {
        const val android                       = "com.android.tools.build:gradle:7.1.3"
    }
}
