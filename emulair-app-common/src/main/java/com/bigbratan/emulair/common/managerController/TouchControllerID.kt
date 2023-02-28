package com.bigbratan.emulair.common.managerController

enum class TouchControllerID {
    GB,
    NES,
    DESMUME,
    MELONDS,
    PSX,
    PSX_DUALSHOCK,
    N64,
    PSP,
    SNES,
    GBA,
    GENESIS_3,
    GENESIS_6,
    ATARI2600,
    SMS,
    GG,
    ARCADE_4,
    ARCADE_6,
    LYNX,
    ATARI7800,
    PCE,
    NGP,
    DOS,
    WS_LANDSCAPE,
    WS_PORTRAIT,
    NINTENDO_3DS;

    class Config(
        val leftConfig: EmulairTouchConfigs.Kind,
        val rightConfig: EmulairTouchConfigs.Kind,
        val leftScale: Float = 1.0f,
        val rightScale: Float = 1.0f,
        val verticalMarginDP: Float = 0f
    )

    companion object {
        fun getConfig(id: TouchControllerID): Config {
            return when (id) {
                GB -> Config(
                    EmulairTouchConfigs.Kind.GB_LEFT,
                    EmulairTouchConfigs.Kind.GB_RIGHT
                )
                NES -> Config(
                    EmulairTouchConfigs.Kind.NES_LEFT,
                    EmulairTouchConfigs.Kind.NES_RIGHT
                )
                DESMUME -> Config(
                    EmulairTouchConfigs.Kind.DESMUME_LEFT,
                    EmulairTouchConfigs.Kind.DESMUME_RIGHT
                )
                MELONDS -> Config(
                    EmulairTouchConfigs.Kind.MELONDS_NDS_LEFT,
                    EmulairTouchConfigs.Kind.MELONDS_NDS_RIGHT
                )
                PSX -> Config(
                    EmulairTouchConfigs.Kind.PSX_LEFT,
                    EmulairTouchConfigs.Kind.PSX_RIGHT
                )
                PSX_DUALSHOCK -> Config(
                    EmulairTouchConfigs.Kind.PSX_DUALSHOCK_LEFT,
                    EmulairTouchConfigs.Kind.PSX_DUALSHOCK_RIGHT
                )
                N64 -> Config(
                    EmulairTouchConfigs.Kind.N64_LEFT,
                    EmulairTouchConfigs.Kind.N64_RIGHT,
                    verticalMarginDP = 8f
                )
                PSP -> Config(
                    EmulairTouchConfigs.Kind.PSP_LEFT,
                    EmulairTouchConfigs.Kind.PSP_RIGHT
                )
                SNES -> Config(
                    EmulairTouchConfigs.Kind.SNES_LEFT,
                    EmulairTouchConfigs.Kind.SNES_RIGHT
                )
                GBA -> Config(
                    EmulairTouchConfigs.Kind.GBA_LEFT,
                    EmulairTouchConfigs.Kind.GBA_RIGHT
                )
                GENESIS_3 -> Config(
                    EmulairTouchConfigs.Kind.GENESIS_3_LEFT,
                    EmulairTouchConfigs.Kind.GENESIS_3_RIGHT
                )
                GENESIS_6 -> Config(
                    EmulairTouchConfigs.Kind.GENESIS_6_LEFT,
                    EmulairTouchConfigs.Kind.GENESIS_6_RIGHT,
                    1.0f,
                    1.2f
                )
                ATARI2600 -> Config(
                    EmulairTouchConfigs.Kind.ATARI2600_LEFT,
                    EmulairTouchConfigs.Kind.ATARI2600_RIGHT
                )
                SMS -> Config(
                    EmulairTouchConfigs.Kind.SMS_LEFT,
                    EmulairTouchConfigs.Kind.SMS_RIGHT
                )
                GG -> Config(
                    EmulairTouchConfigs.Kind.GG_LEFT,
                    EmulairTouchConfigs.Kind.GG_RIGHT
                )
                ARCADE_4 -> Config(
                    EmulairTouchConfigs.Kind.ARCADE_4_LEFT,
                    EmulairTouchConfigs.Kind.ARCADE_4_RIGHT
                )
                ARCADE_6 -> Config(
                    EmulairTouchConfigs.Kind.ARCADE_6_LEFT,
                    EmulairTouchConfigs.Kind.ARCADE_6_RIGHT,
                    1.0f,
                    1.2f
                )
                LYNX -> Config(
                    EmulairTouchConfigs.Kind.LYNX_LEFT,
                    EmulairTouchConfigs.Kind.LYNX_RIGHT
                )
                ATARI7800 -> Config(
                    EmulairTouchConfigs.Kind.ATARI7800_LEFT,
                    EmulairTouchConfigs.Kind.ATARI7800_RIGHT
                )
                PCE -> Config(
                    EmulairTouchConfigs.Kind.PCE_LEFT,
                    EmulairTouchConfigs.Kind.PCE_RIGHT
                )
                NGP -> Config(
                    EmulairTouchConfigs.Kind.NGP_LEFT,
                    EmulairTouchConfigs.Kind.NGP_RIGHT
                )
                DOS -> Config(
                    EmulairTouchConfigs.Kind.DOS_LEFT,
                    EmulairTouchConfigs.Kind.DOS_RIGHT
                )
                WS_LANDSCAPE -> Config(
                    EmulairTouchConfigs.Kind.WS_LANDSCAPE_LEFT,
                    EmulairTouchConfigs.Kind.WS_LANDSCAPE_RIGHT
                )
                WS_PORTRAIT -> Config(
                    EmulairTouchConfigs.Kind.WS_PORTRAIT_LEFT,
                    EmulairTouchConfigs.Kind.WS_PORTRAIT_RIGHT
                )
                NINTENDO_3DS -> Config(
                    EmulairTouchConfigs.Kind.NINTENDO_3DS_LEFT,
                    EmulairTouchConfigs.Kind.NINTENDO_3DS_RIGHT
                )
            }
        }
    }
}
