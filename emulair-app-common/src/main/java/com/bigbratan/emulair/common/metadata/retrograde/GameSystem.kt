/*
 * GameSystem.kt
 *
 * Copyright (C) 2017 Retrograde Project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.bigbratan.emulair.common.metadata.retrograde

import androidx.annotation.StringRes
import com.bigbratan.emulair.common.R
import com.bigbratan.emulair.common.managerCoresLibrary.CoreVariable
import java.util.Locale

data class GameSystem(
    val id: SystemID,

    val libretroFullName: String,

    @StringRes
    val titleResId: Int,

    @StringRes
    val shortTitleResId: Int,

    val systemCoreConfigs: List<SystemCoreConfig>,

    val uniqueExtensions: List<String>,

    val scanOptions: ScanOptions = ScanOptions(),

    val supportedExtensions: List<String> = uniqueExtensions,

    val hasMultiDiskSupport: Boolean = false,

    val fastForwardSupport: Boolean = true,
) {
    companion object {
        private val SYSTEMS = listOf(
            GameSystem(
                SystemID.ATARI2600,
                "Atari - 2600",
                R.string.game_system_title_atr_2600,
                R.string.game_system_title_abbr_atr_2600,
                listOf(
                    SystemCoreConfig(
                        coreID = CoreID.STELLA,
                        exposedSettings = listOf(
                            ExposedSetting(
                                "stella_filter",
                                R.string.option_stella_filter,
                                arrayListOf(
                                    ExposedSetting.Value(
                                        "disabled",
                                        R.string.value_stella_filter_disabled
                                    ),
                                    ExposedSetting.Value(
                                        "composite",
                                        R.string.value_stella_filter_composite
                                    ),
                                    ExposedSetting.Value(
                                        "s-video",
                                        R.string.value_stella_filter_svideo
                                    ),
                                    ExposedSetting.Value("rgb", R.string.value_stella_filter_rgb),
                                    ExposedSetting.Value(
                                        "badly adjusted",
                                        R.string.value_stella_filter_badlyadjusted
                                    ),
                                )
                            ),
                            ExposedSetting(
                                "stella_crop_hoverscan",
                                R.string.option_stella_crop_hoverscan
                            )
                        ),
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.ATARI_2600)
                        )
                    )
                ),
                uniqueExtensions = listOf("a26"),
            ),
            GameSystem(
                SystemID.NES,
                "Nintendo - Nintendo Entertainment System",
                R.string.game_system_title_ntd_nes1,
                R.string.game_system_title_abbr_ntd_nes1,
                listOf(
                    SystemCoreConfig(
                        CoreID.FCEUMM,
                        exposedSettings = listOf(
                            ExposedSetting(
                                "fceumm_overscan_h",
                                R.string.option_fceumm_overscan_h
                            ),
                            ExposedSetting(
                                "fceumm_overscan_v",
                                R.string.option_fceumm_overscan_v
                            ),
                        ),
                        exposedAdvancedSettings = listOf(
                            ExposedSetting(
                                "fceumm_nospritelimit",
                                R.string.option_fceumm_nospritelimit
                            ),
                        ),
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.NES)
                        ),
                    )
                ),
                uniqueExtensions = listOf("nes"),
            ),
            GameSystem(
                SystemID.SNES,
                "Nintendo - Super Nintendo Entertainment System",
                R.string.game_system_title_ntd_nes2_s,
                R.string.game_system_title_abbr_ntd_nes2_s,
                listOf(
                    SystemCoreConfig(
                        CoreID.SNES9X,
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.SNES)
                        )
                    )
                ),
                uniqueExtensions = listOf("smc", "sfc"),
            ),
            GameSystem(
                SystemID.SMS,
                "Sega - Master System - Mark III",
                R.string.game_system_title_seg_ms,
                R.string.game_system_title_abbr_seg_ms,
                listOf(
                    SystemCoreConfig(
                        CoreID.GENESIS_PLUS_GX,
                        exposedSettings = listOf(
                            ExposedSetting(
                                "genesis_plus_gx_blargg_ntsc_filter",
                                R.string.option_genesis_plus_gx_blargg_ntsc_filter,
                                arrayListOf(
                                    ExposedSetting.Value(
                                        "disabled",
                                        R.string.value_genesis_plus_gx_blargg_ntsc_filter_disabled
                                    ),
                                    ExposedSetting.Value(
                                        "monochrome",
                                        R.string.value_genesis_plus_gx_blargg_ntsc_filter_monochrome
                                    ),
                                    ExposedSetting.Value(
                                        "composite",
                                        R.string.value_genesis_plus_gx_blargg_ntsc_filter_composite
                                    ),
                                    ExposedSetting.Value(
                                        "svideo",
                                        R.string.value_genesis_plus_gx_blargg_ntsc_filter_svideo
                                    ),
                                    ExposedSetting.Value(
                                        "rgb",
                                        R.string.value_genesis_plus_gx_blargg_ntsc_filter_rgb
                                    ),
                                )
                            )
                        ),
                        exposedAdvancedSettings = listOf(
                            ExposedSetting(
                                "genesis_plus_gx_no_sprite_limit",
                                R.string.option_genesis_plus_gx_no_sprite_limit
                            ),
                            ExposedSetting(
                                "genesis_plus_gx_overscan",
                                R.string.option_genesis_plus_gx_overscan,
                                arrayListOf(
                                    ExposedSetting.Value(
                                        "disabled",
                                        R.string.value_genesis_plus_gx_overscan_disabled
                                    ),
                                    ExposedSetting.Value(
                                        "top/bottom",
                                        R.string.value_genesis_plus_gx_overscan_topbottom
                                    ),
                                    ExposedSetting.Value(
                                        "left/right",
                                        R.string.value_genesis_plus_gx_overscan_leftright
                                    ),
                                    ExposedSetting.Value(
                                        "full",
                                        R.string.value_genesis_plus_gx_overscan_full
                                    ),
                                )
                            )
                        ),
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.SMS)
                        )
                    )
                ),
                uniqueExtensions = listOf("sms"),
            ),
            GameSystem(
                SystemID.GENESIS,
                "Sega - Mega Drive - Genesis",
                R.string.game_system_title_seg_gns1,
                R.string.game_system_title_abbr_seg_gns1,
                listOf(
                    SystemCoreConfig(
                        CoreID.GENESIS_PLUS_GX,
                        exposedSettings = listOf(
                            ExposedSetting(
                                "genesis_plus_gx_blargg_ntsc_filter",
                                R.string.option_genesis_plus_gx_blargg_ntsc_filter,
                                arrayListOf(
                                    ExposedSetting.Value(
                                        "disabled",
                                        R.string.value_genesis_plus_gx_blargg_ntsc_filter_disabled
                                    ),
                                    ExposedSetting.Value(
                                        "monochrome",
                                        R.string.value_genesis_plus_gx_blargg_ntsc_filter_monochrome
                                    ),
                                    ExposedSetting.Value(
                                        "composite",
                                        R.string.value_genesis_plus_gx_blargg_ntsc_filter_composite
                                    ),
                                    ExposedSetting.Value(
                                        "svideo",
                                        R.string.value_genesis_plus_gx_blargg_ntsc_filter_svideo
                                    ),
                                    ExposedSetting.Value(
                                        "rgb",
                                        R.string.value_genesis_plus_gx_blargg_ntsc_filter_rgb
                                    ),
                                )
                            )
                        ),
                        exposedAdvancedSettings = listOf(
                            ExposedSetting(
                                "genesis_plus_gx_no_sprite_limit",
                                R.string.option_genesis_plus_gx_no_sprite_limit
                            ),
                            ExposedSetting(
                                "genesis_plus_gx_overscan",
                                R.string.option_genesis_plus_gx_overscan,
                                arrayListOf(
                                    ExposedSetting.Value(
                                        "disabled",
                                        R.string.value_genesis_plus_gx_overscan_disabled
                                    ),
                                    ExposedSetting.Value(
                                        "top/bottom",
                                        R.string.value_genesis_plus_gx_overscan_topbottom
                                    ),
                                    ExposedSetting.Value(
                                        "left/right",
                                        R.string.value_genesis_plus_gx_overscan_leftright
                                    ),
                                    ExposedSetting.Value(
                                        "full",
                                        R.string.value_genesis_plus_gx_overscan_full
                                    ),
                                )
                            )
                        ),
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(
                                ControllerConfigs.GENESIS_3,
                                ControllerConfigs.GENESIS_6
                            ),
                            1 to arrayListOf(
                                ControllerConfigs.GENESIS_3,
                                ControllerConfigs.GENESIS_6
                            ),
                            2 to arrayListOf(
                                ControllerConfigs.GENESIS_3,
                                ControllerConfigs.GENESIS_6
                            ),
                            3 to arrayListOf(
                                ControllerConfigs.GENESIS_3,
                                ControllerConfigs.GENESIS_6
                            )
                        )
                    )
                ),
                uniqueExtensions = listOf("gen", "smd", "md"),
            ),
            GameSystem(
                SystemID.SEGACD,
                "Sega - Mega-CD - Sega CD",
                R.string.game_system_title_seg_gns2_cd,
                R.string.game_system_title_abbr_seg_gns2_cd,
                listOf(
                    SystemCoreConfig(
                        CoreID.GENESIS_PLUS_GX,
                        exposedSettings = listOf(
                            ExposedSetting(
                                "genesis_plus_gx_blargg_ntsc_filter",
                                R.string.option_genesis_plus_gx_blargg_ntsc_filter,
                                arrayListOf(
                                    ExposedSetting.Value(
                                        "disabled",
                                        R.string.value_genesis_plus_gx_blargg_ntsc_filter_disabled
                                    ),
                                    ExposedSetting.Value(
                                        "monochrome",
                                        R.string.value_genesis_plus_gx_blargg_ntsc_filter_monochrome
                                    ),
                                    ExposedSetting.Value(
                                        "composite",
                                        R.string.value_genesis_plus_gx_blargg_ntsc_filter_composite
                                    ),
                                    ExposedSetting.Value(
                                        "svideo",
                                        R.string.value_genesis_plus_gx_blargg_ntsc_filter_svideo
                                    ),
                                    ExposedSetting.Value(
                                        "rgb",
                                        R.string.value_genesis_plus_gx_blargg_ntsc_filter_rgb
                                    ),
                                )
                            )
                        ),
                        exposedAdvancedSettings = listOf(
                            ExposedSetting(
                                "genesis_plus_gx_no_sprite_limit",
                                R.string.option_genesis_plus_gx_no_sprite_limit
                            ),
                            ExposedSetting(
                                "genesis_plus_gx_overscan",
                                R.string.option_genesis_plus_gx_overscan,
                                arrayListOf(
                                    ExposedSetting.Value(
                                        "disabled",
                                        R.string.value_genesis_plus_gx_overscan_disabled
                                    ),
                                    ExposedSetting.Value(
                                        "top/bottom",
                                        R.string.value_genesis_plus_gx_overscan_topbottom
                                    ),
                                    ExposedSetting.Value(
                                        "left/right",
                                        R.string.value_genesis_plus_gx_overscan_leftright
                                    ),
                                    ExposedSetting.Value(
                                        "full",
                                        R.string.value_genesis_plus_gx_overscan_full
                                    ),
                                )
                            )
                        ),
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(
                                ControllerConfigs.GENESIS_3,
                                ControllerConfigs.GENESIS_6
                            ),
                            1 to arrayListOf(
                                ControllerConfigs.GENESIS_3,
                                ControllerConfigs.GENESIS_6
                            ),
                            2 to arrayListOf(
                                ControllerConfigs.GENESIS_3,
                                ControllerConfigs.GENESIS_6
                            ),
                            3 to arrayListOf(
                                ControllerConfigs.GENESIS_3,
                                ControllerConfigs.GENESIS_6
                            )
                        ),
                        regionalBIOSFiles = mapOf(
                            "Europe" to "bios_CD_E.bin",
                            "Japan" to "bios_CD_J.bin",
                            "USA" to "bios_CD_U.bin"
                        ),
                    )
                ),
                scanOptions = ScanOptions(
                    scanByFilename = false,
                    scanByUniqueExtension = false,
                    scanByPathAndSupportedExtensions = true,
                    scanBySimilarSerial = true
                ),
                uniqueExtensions = listOf(),
                supportedExtensions = listOf("cue", "iso", "chd"),
            ),
            GameSystem(
                SystemID.GG,
                "Sega - Game Gear",
                R.string.game_system_title_seg_gg,
                R.string.game_system_title_abbr_seg_gg,
                listOf(
                    SystemCoreConfig(
                        CoreID.GENESIS_PLUS_GX,
                        exposedSettings = listOf(
                            ExposedSetting(
                                "genesis_plus_gx_lcd_filter",
                                R.string.option_genesis_plus_gx_lcd_filter
                            )
                        ),
                        exposedAdvancedSettings = listOf(
                            ExposedSetting(
                                "genesis_plus_gx_no_sprite_limit",
                                R.string.option_genesis_plus_gx_no_sprite_limit
                            ),
                        ),
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.GG)
                        )
                    )
                ),
                uniqueExtensions = listOf("gg"),
            ),
            GameSystem(
                SystemID.GB,
                "Nintendo - Game Boy",
                R.string.game_system_title_ntd_gb1,
                R.string.game_system_title_abbr_ntd_gb1,
                listOf(
                    SystemCoreConfig(
                        CoreID.GAMBATTE,
                        exposedSettings = listOf(
                            ExposedSetting(
                                "gambatte_gb_colorization",
                                R.string.option_gambatte_gb_colorization
                            ),
                            ExposedSetting(
                                "gambatte_gb_internal_palette",
                                R.string.option_gambatte_gb_internal_palette
                            ),
                            ExposedSetting(
                                "gambatte_mix_frames",
                                R.string.option_gambatte_mix_frames,
                                arrayListOf(
                                    ExposedSetting.Value(
                                        "disabled",
                                        R.string.value_gambatte_mix_frames_disabled
                                    ),
                                    ExposedSetting.Value(
                                        "mix",
                                        R.string.value_gambatte_mix_frames_mix
                                    ),
                                    ExposedSetting.Value(
                                        "lcd_ghosting",
                                        R.string.value_gambatte_mix_frames_lcd_ghosting
                                    ),
                                    ExposedSetting.Value(
                                        "lcd_ghosting_fast",
                                        R.string.value_gambatte_mix_frames_lcd_ghosting_fast
                                    ),
                                )
                            ),
                            ExposedSetting(
                                "gambatte_dark_filter_level",
                                R.string.option_gambatte_dark_filter_level
                            )
                        ),
                        defaultSettings = listOf(
                            CoreVariable("gambatte_gb_colorization", "internal"),
                            CoreVariable("gambatte_gb_internal_palette", "GB - Pocket")
                        ),
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.GB)
                        )
                    ),
                ),
                uniqueExtensions = listOf("gb"),
            ),
            GameSystem(
                SystemID.GBC,
                "Nintendo - Game Boy Color",
                R.string.game_system_title_ntd_gb2_c,
                R.string.game_system_title_abbr_ntd_gb2_c,
                listOf(
                    SystemCoreConfig(
                        CoreID.GAMBATTE,
                        exposedSettings = listOf(
                            ExposedSetting(
                                "gambatte_mix_frames",
                                R.string.option_gambatte_mix_frames,
                                arrayListOf(
                                    ExposedSetting.Value(
                                        "disabled",
                                        R.string.value_gambatte_mix_frames_disabled
                                    ),
                                    ExposedSetting.Value(
                                        "mix",
                                        R.string.value_gambatte_mix_frames_mix
                                    ),
                                    ExposedSetting.Value(
                                        "lcd_ghosting",
                                        R.string.value_gambatte_mix_frames_lcd_ghosting
                                    ),
                                    ExposedSetting.Value(
                                        "lcd_ghosting_fast",
                                        R.string.value_gambatte_mix_frames_lcd_ghosting_fast
                                    ),
                                )
                            ),
                            ExposedSetting(
                                "gambatte_gbc_color_correction",
                                R.string.option_gambatte_gbc_color_correction,
                                arrayListOf(
                                    ExposedSetting.Value(
                                        "disabled",
                                        R.string.value_gambatte_gbc_color_correction_disabled
                                    ),
                                    ExposedSetting.Value(
                                        "always",
                                        R.string.value_gambatte_gbc_color_correction_always
                                    )
                                )
                            ),
                            ExposedSetting(
                                "gambatte_dark_filter_level",
                                R.string.option_gambatte_dark_filter_level
                            )
                        ),
                        rumbleSupported = true,
                        defaultSettings = listOf(
                            CoreVariable("gambatte_gbc_color_correction", "disabled"),
                        ),
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.GB)
                        )
                    ),
                ),
                uniqueExtensions = listOf("gbc"),
            ),
            GameSystem(
                SystemID.GBA,
                "Nintendo - Game Boy Advance",
                R.string.game_system_title_ntd_gb3_a,
                R.string.game_system_title_abbr_ntd_gb3_a,
                listOf(
                    SystemCoreConfig(
                        CoreID.MGBA,
                        exposedSettings = listOf(
                            ExposedSetting(
                                "mgba_solar_sensor_level",
                                R.string.option_mgba_solar_sensor_level
                            ),
                            ExposedSetting(
                                "mgba_interframe_blending",
                                R.string.option_mgba_interframe_blending,
                                arrayListOf(
                                    ExposedSetting.Value(
                                        "OFF",
                                        R.string.value_mgba_interframe_blending_off
                                    ),
                                    ExposedSetting.Value(
                                        "mix",
                                        R.string.value_mgba_interframe_blending_mix
                                    ),
                                    ExposedSetting.Value(
                                        "lcd_ghosting",
                                        R.string.value_mgba_interframe_blending_lcd_ghosting
                                    ),
                                    ExposedSetting.Value(
                                        "lcd_ghosting_fast",
                                        R.string.value_mgba_interframe_blending_lcd_ghosting_fast
                                    ),
                                )
                            ),
                            ExposedSetting(
                                "mgba_frameskip",
                                R.string.option_mgba_frameskip,
                                arrayListOf(
                                    ExposedSetting.Value(
                                        "disabled",
                                        R.string.value_mgba_frameskip_disabled
                                    ),
                                    ExposedSetting.Value("auto", R.string.value_mgba_frameskip_auto)
                                )
                            ),
                            ExposedSetting(
                                "mgba_color_correction",
                                R.string.option_mgba_color_correction,
                                arrayListOf(
                                    ExposedSetting.Value(
                                        "OFF",
                                        R.string.value_mgba_color_correction_off
                                    ),
                                    ExposedSetting.Value(
                                        "GBA",
                                        R.string.value_mgba_color_correction_gba
                                    )
                                )
                            ),
                        ),
                        rumbleSupported = true,
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.GBA)
                        )
                    ),
                ),
                uniqueExtensions = listOf("gba"),
            ),
            GameSystem(
                SystemID.N64,
                "Nintendo - Nintendo 64",
                R.string.game_system_title_ntd_64,
                R.string.game_system_title_abbr_ntd_64,
                listOf(
                    SystemCoreConfig(
                        CoreID.MUPEN64_PLUS_NEXT,
                        exposedSettings = listOf(
                            ExposedSetting(
                                "mupen64plus-43screensize",
                                R.string.option_mupen64plus_43screensize
                            ),
                            ExposedSetting(
                                "mupen64plus-cpucore",
                                R.string.option_mupen64plus_cpucore,
                                arrayListOf(
                                    ExposedSetting.Value(
                                        "dynamic_recompiler",
                                        R.string.value_mupen64plus_cpucore_dynamicrecompiler
                                    ),
                                    ExposedSetting.Value(
                                        "pure_interpreter",
                                        R.string.value_mupen64plus_cpucore_pureinterpreter
                                    ),
                                    ExposedSetting.Value(
                                        "cached_interpreter",
                                        R.string.value_mupen64plus_cpucore_cachedinterpreter
                                    ),
                                )
                            ),
                            ExposedSetting(
                                "mupen64plus-BilinearMode",
                                R.string.option_mupen64plus_BilinearMode,
                                arrayListOf(
                                    ExposedSetting.Value(
                                        "standard",
                                        R.string.value_mupen64plus_bilinearmode_standard
                                    ),
                                    ExposedSetting.Value(
                                        "3point",
                                        R.string.value_mupen64plus_bilinearmode_3point
                                    ),
                                )
                            ),
                            ExposedSetting(
                                "mupen64plus-pak1",
                                R.string.option_mupen64plus_pak1,
                                arrayListOf(
                                    ExposedSetting.Value(
                                        "memory",
                                        R.string.value_mupen64plus_mupen64plus_pak1_memory
                                    ),
                                    ExposedSetting.Value(
                                        "rumble",
                                        R.string.value_mupen64plus_mupen64plus_pak1_rumble
                                    ),
                                    ExposedSetting.Value(
                                        "none",
                                        R.string.value_mupen64plus_mupen64plus_pak1_none
                                    )
                                )
                            ),
                            ExposedSetting(
                                "mupen64plus-pak2",
                                R.string.option_mupen64plus_pak2,
                                arrayListOf(
                                    ExposedSetting.Value(
                                        "none",
                                        R.string.value_mupen64plus_mupen64plus_pak2_none
                                    ),
                                    ExposedSetting.Value(
                                        "rumble",
                                        R.string.value_mupen64plus_mupen64plus_pak2_rumble
                                    )
                                )
                            )
                        ),
                        defaultSettings = listOf(
                            CoreVariable("mupen64plus-43screensize", "320x240"),
                            CoreVariable("mupen64plus-FrameDuping", "True")
                        ),
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.N64)
                        ),
                        rumbleSupported = true,
                        skipDuplicateFrames = false
                    )
                ),
                uniqueExtensions = listOf("n64", "z64"),
            ),
            GameSystem(
                SystemID.PSX,
                "Sony - PlayStation",
                R.string.game_system_title_sony_ps1,
                R.string.game_system_title_abbr_sony_ps1,
                listOf(
                    SystemCoreConfig(
                        CoreID.PCSX_REARMED,
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(
                                ControllerConfigs.PSX_STANDARD,
                                ControllerConfigs.PSX_DUALSHOCK
                            ),
                            1 to arrayListOf(
                                ControllerConfigs.PSX_STANDARD,
                                ControllerConfigs.PSX_DUALSHOCK
                            ),
                            2 to arrayListOf(
                                ControllerConfigs.PSX_STANDARD,
                                ControllerConfigs.PSX_DUALSHOCK
                            ),
                            3 to arrayListOf(
                                ControllerConfigs.PSX_STANDARD,
                                ControllerConfigs.PSX_DUALSHOCK
                            ),
                        ),
                        exposedSettings = listOf(
                            ExposedSetting(
                                "pcsx_rearmed_frameskip",
                                R.string.option_pcsx_rearmed_frameskip
                            )
                        ),
                        exposedAdvancedSettings = listOf(
                            ExposedSetting(
                                "pcsx_rearmed_drc",
                                R.string.option_pcsx_rearmed_drc
                            )
                        ),
                        defaultSettings = listOf(
                            CoreVariable("pcsx_rearmed_drc", "disabled"),
                            CoreVariable("pcsx_rearmed_duping_enable", "enabled"),
                        ),
                        rumbleSupported = true,
                        supportsLibretroVFS = true,
                        skipDuplicateFrames = false
                    )
                ),
                uniqueExtensions = listOf(),
                supportedExtensions = listOf("iso", "pbp", "chd", "cue", "m3u"),
                scanOptions = ScanOptions(
                    scanByFilename = false,
                    scanByUniqueExtension = false,
                    scanByPathAndSupportedExtensions = true
                ),
                hasMultiDiskSupport = true
            ),
            GameSystem(
                SystemID.PSP,
                "Sony - PlayStation Portable",
                R.string.game_system_title_sony_psp,
                R.string.game_system_title_abbr_sony_psp,
                listOf(
                    SystemCoreConfig(
                        CoreID.PPSSPP,
                        exposedSettings = listOf(
                            ExposedSetting(
                                "ppsspp_auto_frameskip",
                                R.string.option_ppsspp_auto_frameskip
                            ),
                            ExposedSetting(
                                "ppsspp_frameskip",
                                R.string.option_mgba_frameskip
                            )
                        ),
                        exposedAdvancedSettings = listOf(
                            ExposedSetting(
                                "ppsspp_cpu_core",
                                R.string.option_ppsspp_cpu_core,
                                arrayListOf(
                                    ExposedSetting.Value("JIT", R.string.value_ppsspp_cpu_core_jit),
                                    ExposedSetting.Value(
                                        "IR JIT",
                                        R.string.value_ppsspp_cpu_core_irjit
                                    ),
                                    ExposedSetting.Value(
                                        "Interpreter",
                                        R.string.value_ppsspp_cpu_core_interpreter
                                    ),
                                )
                            ),
                            ExposedSetting(
                                "ppsspp_internal_resolution",
                                R.string.option_ppsspp_internal_resolution
                            ),
                            ExposedSetting(
                                "ppsspp_texture_scaling_level",
                                R.string.option_ppsspp_texture_scaling_level
                            ),
                        ),
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.PSP)
                        ),
                        supportsLibretroVFS = true
                    )
                ),
                uniqueExtensions = listOf(),
                supportedExtensions = listOf("iso", "cso", "pbp"),
                scanOptions = ScanOptions(
                    scanByFilename = false,
                    scanByUniqueExtension = false,
                    scanByPathAndSupportedExtensions = true
                ),
                fastForwardSupport = false
            ),
            GameSystem(
                SystemID.FBNEO,
                "FBNeo - Arcade Games",
                R.string.game_system_title_arcade_fbneo,
                R.string.game_system_title_abbr_arcade_fbneo,
                listOf(
                    SystemCoreConfig(
                        CoreID.FBNEO,
                        exposedSettings = listOf(
                            ExposedSetting(
                                "fbneo-frameskip",
                                R.string.option_fbneo_frameskip
                            ),
                            ExposedSetting(
                                "fbneo-cpu-speed-adjust",
                                R.string.option_fbneo_cpu_speed_adjust
                            )
                        ),
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.FB_NEO_4, ControllerConfigs.FB_NEO_6)
                        )
                    )
                ),
                uniqueExtensions = listOf(),
                supportedExtensions = listOf("zip"),
                scanOptions = ScanOptions(
                    scanByFilename = false,
                    scanByUniqueExtension = false,
                    scanByPathAndFilename = true,
                    scanByPathAndSupportedExtensions = false
                ),
            ),
            GameSystem(
                SystemID.MAME2003PLUS,
                "MAME 2003-Plus",
                R.string.game_system_title_arcade_mame2003plus,
                R.string.game_system_title_abbr_arcade_mame2003plus,
                listOf(
                    SystemCoreConfig(
                        CoreID.MAME2003PLUS,
                        statesSupported = false,
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(
                                ControllerConfigs.MAME_2003_4,
                                ControllerConfigs.MAME_2003_6
                            )
                        )
                    )
                ),
                uniqueExtensions = listOf(),
                supportedExtensions = listOf("zip"),
                scanOptions = ScanOptions(
                    scanByFilename = false,
                    scanByUniqueExtension = false,
                    scanByPathAndFilename = true,
                    scanByPathAndSupportedExtensions = false
                ),
            ),
            GameSystem(
                SystemID.NDS,
                "Nintendo - Nintendo DS",
                R.string.game_system_title_ntd_ds1,
                R.string.game_system_title_abbr_ntd_ds1,
                listOf(
                    SystemCoreConfig(
                        CoreID.DESMUME,
                        exposedSettings = listOf(
                            ExposedSetting(
                                "desmume_screens_layout",
                                R.string.option_desmume_screens_layout,
                                arrayListOf(
                                    ExposedSetting.Value(
                                        "top/bottom",
                                        R.string.value_desmume_screens_layout_topbottom
                                    ),
                                    ExposedSetting.Value(
                                        "left/right",
                                        R.string.value_desmume_screens_layout_leftright
                                    )
                                )
                            ),
                            ExposedSetting(
                                "desmume_frameskip",
                                R.string.option_desmume_frameskip
                            ),
                        ),
                        defaultSettings = listOf(
                            CoreVariable("desmume_pointer_type", "touch"),
                            CoreVariable("desmume_frameskip", "1")
                        ),
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.DESMUME)
                        ),
                        skipDuplicateFrames = false
                    ),
                    SystemCoreConfig(
                        CoreID.MELONDS,
                        exposedSettings = listOf(
                            ExposedSetting(
                                "melonds_screen_layout",
                                R.string.option_melonds_screen_layout,
                                arrayListOf(
                                    ExposedSetting.Value(
                                        "Top/Bottom",
                                        R.string.value_melonds_screen_layout_topbottom
                                    ),
                                    ExposedSetting.Value(
                                        "Left/Right",
                                        R.string.value_melonds_screen_layout_leftright
                                    ),
                                    ExposedSetting.Value(
                                        "Hybrid Top",
                                        R.string.value_melonds_screen_layout_hybridtop
                                    ),
                                    ExposedSetting.Value(
                                        "Hybrid Bottom",
                                        R.string.value_melonds_screen_layout_hybridbottom
                                    ),
                                )
                            )
                        ),
                        exposedAdvancedSettings = listOf(
                            ExposedSetting(
                                "melonds_threaded_renderer",
                                R.string.option_melonds_threaded_renderer
                            ),
                            ExposedSetting(
                                "melonds_jit_enable",
                                R.string.option_melonds_jit_enable
                            ),
                        ),
                        defaultSettings = listOf(
                            CoreVariable("melonds_touch_mode", "Touch"),
                            CoreVariable("melonds_threaded_renderer", "enabled")
                        ),
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.MELONDS)
                        ),
                        statesVersion = 1,
                    )
                ),
                uniqueExtensions = listOf("nds"),
            ),
            GameSystem(
                SystemID.ATARI7800,
                "Atari - 7800",
                R.string.game_system_title_atr_7800,
                R.string.game_system_title_abbr_atr_7800,
                listOf(
                    SystemCoreConfig(
                        CoreID.PROSYSTEM,
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.ATARI7800)
                        )
                    ),
                ),
                uniqueExtensions = listOf("a78"),
                supportedExtensions = listOf("bin")
            ),
            GameSystem(
                SystemID.LYNX,
                "Atari - Lynx",
                R.string.game_system_title_atr_lynx,
                R.string.game_system_title_abbr_atr_lynx,
                listOf(
                    SystemCoreConfig(
                        CoreID.HANDY,
                        requiredBIOSFiles = listOf(
                            "lynxboot.img"
                        ),
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.LYNX)
                        ),
                        exposedSettings = listOf(
                            ExposedSetting(
                                "handy_rot",
                                R.string.option_handy_rot,
                                arrayListOf(
                                    ExposedSetting.Value(
                                        "None",
                                        R.string.value_handy_rot_none
                                    ),
                                    ExposedSetting.Value(
                                        "90",
                                        R.string.value_handy_rot_90
                                    ),
                                    ExposedSetting.Value(
                                        "270",
                                        R.string.value_handy_rot_270
                                    ),
                                )
                            )
                        ),
                        defaultSettings = listOf(
                            CoreVariable("handy_rot", "None"),
                            CoreVariable("handy_refresh_rate", "60")
                        )
                    ),
                ),
                uniqueExtensions = listOf("lnx"),
            ),
            GameSystem(
                SystemID.PC_ENGINE,
                "NEC - PC Engine - TurboGrafx 16",
                R.string.game_system_title_nec_pce,
                R.string.game_system_title_abbr_nec_pce,
                listOf(
                    SystemCoreConfig(
                        CoreID.MEDNAFEN_PCE_FAST,
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.PCE)
                        )
                    ),
                ),
                uniqueExtensions = listOf("pce"),
                supportedExtensions = listOf("bin"),
            ),
            GameSystem(
                SystemID.NGP,
                "SNK - Neo Geo Pocket",
                R.string.game_system_title_snk_ngp1,
                R.string.game_system_title_abbr_snk_ngp1,
                listOf(
                    SystemCoreConfig(
                        CoreID.MEDNAFEN_NGP,
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.NGP)
                        )
                    ),
                ),
                uniqueExtensions = listOf("ngp"),
            ),
            GameSystem(
                SystemID.NGC,
                "SNK - Neo Geo Pocket Color",
                R.string.game_system_title_snk_ngp2_c,
                R.string.game_system_title_abbr_snk_ngp2_c,
                listOf(
                    SystemCoreConfig(
                        CoreID.MEDNAFEN_NGP,
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.NGP)
                        )
                    ),
                ),
                uniqueExtensions = listOf("ngc"),
            ),
            GameSystem(
                SystemID.WS,
                "Bandai - WonderSwan",
                R.string.game_system_title_ban_ws1,
                R.string.game_system_title_abbr_ban_ws1,
                listOf(
                    SystemCoreConfig(
                        CoreID.MEDNAFEN_WSWAN,
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.WS_LANDSCAPE, ControllerConfigs.WS_PORTRAIT)
                        ),
                        exposedSettings = listOf(
                            ExposedSetting(
                                "wswan_rotate_display",
                                R.string.option_wswan_rotate_display,
                                arrayListOf(
                                    ExposedSetting.Value(
                                        "landscape",
                                        R.string.value_wswan_rotate_display_landscape
                                    ),
                                    ExposedSetting.Value(
                                        "portrait",
                                        R.string.value_wswan_rotate_display_portrait
                                    ),
                                )
                            ),
                            ExposedSetting(
                                "wswan_mono_palette",
                                R.string.option_wswan_mono_palette
                            )
                        ),
                        defaultSettings = listOf(
                            CoreVariable("wswan_rotate_display", "landscape"),
                            CoreVariable("wswan_mono_palette", "wonderswan"),
                        )
                    ),
                ),
                uniqueExtensions = listOf("ws"),
            ),
            GameSystem(
                SystemID.WSC,
                "Bandai - WonderSwan Color",
                R.string.game_system_title_ban_ws2_c,
                R.string.game_system_title_abbr_ban_ws2_c,
                listOf(
                    SystemCoreConfig(
                        CoreID.MEDNAFEN_WSWAN,
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.WS_LANDSCAPE, ControllerConfigs.WS_PORTRAIT)
                        ),
                        exposedSettings = listOf(
                            ExposedSetting(
                                "wswan_rotate_display",
                                R.string.option_wswan_rotate_display,
                                arrayListOf(
                                    ExposedSetting.Value(
                                        "landscape",
                                        R.string.value_wswan_rotate_display_landscape
                                    ),
                                    ExposedSetting.Value(
                                        "portrait",
                                        R.string.value_wswan_rotate_display_portrait
                                    ),
                                )
                            )
                        ),
                        defaultSettings = listOf(
                            CoreVariable("wswan_rotate_display", "landscape")
                        )
                    ),
                ),
                uniqueExtensions = listOf("wsc"),
            ),
            GameSystem(
                SystemID.DOS,
                "DOS",
                R.string.game_system_title_microsoft_msdos_pc,
                R.string.game_system_title_abbr_microsoft_msdos_pc,
                listOf(
                    SystemCoreConfig(
                        CoreID.DOSBOX_PURE,
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(
                                ControllerConfigs.DOS_AUTO,
                                ControllerConfigs.DOS_MOUSE_LEFT,
                                ControllerConfigs.DOS_MOUSE_RIGHT,
                            )
                        ),
                        statesSupported = false,
                    )
                ),
                fastForwardSupport = false,
                uniqueExtensions = listOf("dosz"),
                scanOptions = ScanOptions(
                    scanByFilename = false,
                    scanByUniqueExtension = true,
                    scanByPathAndFilename = false,
                    scanByPathAndSupportedExtensions = true
                ),
            ),
            GameSystem(
                SystemID.NINTENDO_3DS,
                "Nintendo - Nintendo 3DS",
                R.string.game_system_title_ntd_ds23,
                R.string.game_system_title_abbr_ntd_ds23,
                listOf(
                    SystemCoreConfig(
                        CoreID.CITRA,
                        controllerConfigs = hashMapOf(
                            0 to arrayListOf(ControllerConfigs.NINTENDO_3DS)
                        ),
                        defaultSettings = listOf(
                            CoreVariable("citra_use_acc_mul", "disabled"),
                            CoreVariable("citra_touch_touchscreen", "enabled"),
                            CoreVariable("citra_mouse_touchscreen", "disabled"),
                            CoreVariable("citra_render_touchscreen", "disabled"),
                            CoreVariable("citra_use_hw_shader_cache", "disabled"),
                        ),
                        exposedSettings = listOf(
                            ExposedSetting(
                                "citra_layout_option",
                                R.string.option_citra_layout_option,
                                arrayListOf(
                                    ExposedSetting.Value(
                                        "Default Top-Bottom Screen",
                                        R.string.value_citra_layout_option_topbottom
                                    ),
                                    ExposedSetting.Value(
                                        "Side by Side",
                                        R.string.value_citra_layout_option_sidebyside
                                    )
                                )
                            ),
                            ExposedSetting(
                                "citra_resolution_factor",
                                R.string.option_citra_resolution_factor
                            ),
                            ExposedSetting(
                                "citra_use_acc_mul",
                                R.string.option_citra_use_acc_mul
                            ),
                            ExposedSetting(
                                "citra_use_acc_geo_shaders",
                                R.string.option_citra_use_acc_geo_shaders
                            ),
                        ),
                        statesSupported = false,
                        supportsLibretroVFS = true,
                        supportedOnlyArchitectures = setOf("arm64-v8a")
                    ),
                ),
                uniqueExtensions = listOf("3ds"),
            ),
        )

        private val byIdCache by lazy { mapOf(*SYSTEMS.map { it.id.dbname to it }.toTypedArray()) }
        private val byExtensionCache by lazy {
            val mutableMap = mutableMapOf<String, GameSystem>()
            for (system in SYSTEMS) {
                for (extension in system.uniqueExtensions) {
                    mutableMap[extension.toLowerCase(Locale.US)] = system
                }
            }
            mutableMap.toMap()
        }

        fun findById(id: String): GameSystem = byIdCache.getValue(id)

        fun all() = SYSTEMS

        fun getSupportedExtensions(): List<String> {
            return SYSTEMS.flatMap { it.supportedExtensions }
        }

        fun findSystemForCore(coreID: CoreID): List<GameSystem> {
            return all().filter { system -> system.systemCoreConfigs.any { it.coreID == coreID } }
        }

        fun findByUniqueFileExtension(fileExtension: String): GameSystem? =
            byExtensionCache[fileExtension.toLowerCase(Locale.US)]

        data class ScanOptions(
            val scanByFilename: Boolean = true,
            val scanByUniqueExtension: Boolean = true,
            val scanByPathAndFilename: Boolean = false,
            val scanByPathAndSupportedExtensions: Boolean = true,
            val scanBySimilarSerial: Boolean = false
        )
    }
}
