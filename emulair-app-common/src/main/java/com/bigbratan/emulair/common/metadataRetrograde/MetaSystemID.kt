package com.bigbratan.emulair.common.metadataRetrograde

import com.bigbratan.emulair.common.R

fun GameSystem.metaSystemID() = MetaSystemID.fromSystemID(id)

/** Meta systems represents a collection of systems which appear the same to the user. It's currently
 *  only for Arcade (it places FBNeo and MAME2003 under the same name, "Arcade"). */
enum class MetaSystemID(val titleResId: Int, val imageResId: Int, val systemIDs: List<SystemID>) {
    NES(
        R.string.game_system_title_ntd_nes1,
        R.drawable.game_system_ntd_nes1,
        listOf(SystemID.NES)
    ),
    SNES(
        R.string.game_system_title_ntd_nes2_s,
        R.drawable.game_system_ntd_nes2_s,
        listOf(SystemID.SNES)
    ),
    GENESIS(
        R.string.game_system_title_seg_gns1,
        R.drawable.game_system_seg_gns1,
        listOf(SystemID.GENESIS)
    ),
    SEGACD(
        R.string.game_system_title_seg_gns2_cd,
        R.drawable.game_system_seg_gns2_cd,
        listOf(SystemID.SEGACD)
    ),
    GB(
        R.string.game_system_title_ntd_gb1,
        R.drawable.game_system_ntd_gb1,
        listOf(SystemID.GB)
    ),
    GBC(
        R.string.game_system_title_ntd_gb2_c,
        R.drawable.game_system_ntd_gb2_c,
        listOf(SystemID.GBC)
    ),
    GBA(
        R.string.game_system_title_ntd_gb3_a,
        R.drawable.game_system_ntd_gb3_a,
        listOf(SystemID.GBA)
    ),
    N64(
        R.string.game_system_title_ntd_64,
        R.drawable.game_system_ntd_64,
        listOf(SystemID.N64)
    ),
    SMS(
        R.string.game_system_title_seg_ms,
        R.drawable.game_system_seg_ms,
        listOf(SystemID.SMS)
    ),
    PSP(
        R.string.game_system_title_sony_psp,
        R.drawable.game_system_sony_psp,
        listOf(SystemID.PSP)
    ),
    NDS(
        R.string.game_system_title_ntd_ds1,
        R.drawable.game_system_ntd_ds1,
        listOf(SystemID.NDS)
    ),
    GG(
        R.string.game_system_title_seg_gg,
        R.drawable.game_system_seg_gg,
        listOf(SystemID.GG)
    ),
    ATARI2600(
        R.string.game_system_title_atr_2600,
        R.drawable.game_system_atr_2600,
        listOf(SystemID.ATARI2600)
    ),
    PSX(
        R.string.game_system_title_sony_ps1,
        R.drawable.game_system_sony_ps1,
        listOf(SystemID.PSX)
    ),
    ARCADE(
        R.string.game_system_title_arcade,
        R.drawable.game_system_arcade,
        listOf(SystemID.FBNEO, SystemID.MAME2003PLUS)
    ),
    ATARI7800(
        R.string.game_system_title_atr_7800,
        R.drawable.game_system_atr_7800,
        listOf(SystemID.ATARI7800)
    ),
    LYNX(
        R.string.game_system_title_atr_lynx,
        R.drawable.game_system_atr_lynx,
        listOf(SystemID.LYNX)
    ),
    PC_ENGINE(
        R.string.game_system_title_nec_pce,
        R.drawable.game_system_nec_pce,
        listOf(SystemID.PC_ENGINE)
    ),
    NGP(
        R.string.game_system_title_snk_ngp1,
        R.drawable.game_system_snk_ngp1,
        listOf(SystemID.NGP, SystemID.NGC)
    ),
    NGC(
        R.string.game_system_title_snk_ngp2_c,
        R.drawable.game_system_snk_ngp2_c,
        listOf(SystemID.NGC)
    ),
    WS(
        R.string.game_system_title_ban_ws1,
        R.drawable.game_system_ban_ws1,
        listOf(SystemID.WS, SystemID.WSC)
    ),
    WSC(
        R.string.game_system_title_ban_ws2_c,
        R.drawable.game_system_ban_ws2_c,
        listOf(SystemID.WSC)
    ),
    DOS(
        R.string.game_system_title_multisys_computers_dos,
        R.drawable.game_system_multisys_computers_dos_tandy_1000,
        listOf(SystemID.DOS)
    ),
    NINTENDO_3DS(
        R.string.game_system_title_ntd_ds23,
        R.drawable.game_system_ntd_ds23,
        listOf(SystemID.NINTENDO_3DS)
    );

    companion object {
        fun fromSystemID(systemID: SystemID): MetaSystemID {
            return when (systemID) {
                SystemID.FBNEO -> ARCADE
                SystemID.MAME2003PLUS -> ARCADE
                SystemID.ATARI2600 -> ATARI2600
                SystemID.GB -> GB
                SystemID.GBC -> GBC
                SystemID.GBA -> GBA
                SystemID.GENESIS -> GENESIS
                SystemID.SEGACD -> SEGACD
                SystemID.GG -> GG
                SystemID.N64 -> N64
                SystemID.NDS -> NDS
                SystemID.NES -> NES
                SystemID.PSP -> PSP
                SystemID.PSX -> PSX
                SystemID.SMS -> SMS
                SystemID.SNES -> SNES
                SystemID.PC_ENGINE -> PC_ENGINE
                SystemID.LYNX -> LYNX
                SystemID.ATARI7800 -> ATARI7800
                SystemID.DOS -> DOS
                SystemID.NGP -> NGP
                SystemID.NGC -> NGC
                SystemID.WS -> WS
                SystemID.WSC -> WSC
                SystemID.NINTENDO_3DS -> NINTENDO_3DS
            }
        }
    }
}
