include(
    ":emulair-app-common",
    ":emulair-app",
    ":emulair-app-build-nogplay",
    ":emulair-app-build-gplay",
    ":bundled-cores"
)

project(":bundled-cores").projectDir = File("libretro-cores/bundled-cores")

fun usePlayDynamicFeatures(): Boolean {
    val task = gradle.startParameter.taskRequests.toString()
    return task.contains("Gplay") && task.contains("DownloadedCores")
}

// To test out the error screens, wrap the below `include(...)`, as well as all the `project(...)`s under `if (usePlayDynamicFeatures()) {..}`
// This way, the cores in the debug version of the app will not be installed, and an error screen will appear
// Follow similar instructions in build.gradle.kts for changes to actually take effect
include(
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

project(":libretro_core_gambatte").projectDir = File("libretro-cores/libretro_core_gambatte")
project(":libretro_core_desmume").projectDir = File("libretro-cores/libretro_core_desmume")
project(":libretro_core_melonds").projectDir = File("libretro-cores/libretro_core_melonds")
project(":libretro_core_fbneo").projectDir = File("libretro-cores/libretro_core_fbneo")
project(":libretro_core_fceumm").projectDir = File("libretro-cores/libretro_core_fceumm")
project(":libretro_core_genesis_plus_gx").projectDir = File("libretro-cores/libretro_core_genesis_plus_gx")
project(":libretro_core_mame2003_plus").projectDir = File("libretro-cores/libretro_core_mame2003_plus")
project(":libretro_core_mgba").projectDir = File("libretro-cores/libretro_core_mgba")
project(":libretro_core_mupen64plus_next_gles3").projectDir = File("libretro-cores/libretro_core_mupen64plus_next_gles3")
project(":libretro_core_pcsx_rearmed").projectDir = File("libretro-cores/libretro_core_pcsx_rearmed")
project(":libretro_core_ppsspp").projectDir = File("libretro-cores/libretro_core_ppsspp")
project(":libretro_core_snes9x").projectDir = File("libretro-cores/libretro_core_snes9x")
project(":libretro_core_stella").projectDir = File("libretro-cores/libretro_core_stella")
project(":libretro_core_handy").projectDir = File("libretro-cores/libretro_core_handy")
project(":libretro_core_prosystem").projectDir = File("libretro-cores/libretro_core_prosystem")
project(":libretro_core_mednafen_pce_fast").projectDir = File("libretro-cores/libretro_core_mednafen_pce_fast")
project(":libretro_core_mednafen_ngp").projectDir = File("libretro-cores/libretro_core_mednafen_ngp")
project(":libretro_core_mednafen_wswan").projectDir = File("libretro-cores/libretro_core_mednafen_wswan")
project(":libretro_core_dosbox_pure").projectDir = File("libretro-cores/libretro_core_dosbox_pure")
project(":libretro_core_citra").projectDir = File("libretro-cores/libretro_core_citra")
