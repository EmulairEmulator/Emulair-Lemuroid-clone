package com.bigbratan.emulair.common.managers.bios

import com.bigbratan.emulair.common.utils.files.safeDelete
import com.bigbratan.emulair.common.utils.kotlin.associateByNotNull
import com.bigbratan.emulair.common.utils.kotlin.writeToFile
import com.bigbratan.emulair.common.metadata.retrograde.SystemCoreConfig
import com.bigbratan.emulair.common.metadata.retrograde.SystemID
import com.bigbratan.emulair.common.metadata.retrograde.db.entity.Game
import com.bigbratan.emulair.common.managerStorage.DirectoriesManager
import com.bigbratan.emulair.common.managerStorage.StorageFile
import java.io.File
import java.io.InputStream
import timber.log.Timber

class BiosManager(private val directoriesManager: DirectoriesManager) {

    private val crcLookup = _root_ide_package_.com.bigbratan.emulair.common.managers.bios.BiosManager.Companion.SUPPORTED_BIOS.associateByNotNull { it.externalCRC32 }
    private val nameLookup = _root_ide_package_.com.bigbratan.emulair.common.managers.bios.BiosManager.Companion.SUPPORTED_BIOS.associateByNotNull { it.externalName }

    fun getMissingBiosFiles(coreConfig: SystemCoreConfig, game: Game): List<String> {
        val regionalBiosFiles = coreConfig.regionalBIOSFiles

        val gameLabels = Regex("\\([A-Za-z]+\\)")
            .findAll(game.title)
            .map { it.value.drop(1).dropLast(1) }
            .filter { it.isNotBlank() }
            .toSet()

        Timber.d("Found game labels: $gameLabels")

        val requiredRegionalFiles = gameLabels.intersect(regionalBiosFiles.keys)
            .ifEmpty { regionalBiosFiles.keys }
            .mapNotNull { regionalBiosFiles[it] }

        Timber.d("Required regional files for game: $requiredRegionalFiles")

        return (coreConfig.requiredBIOSFiles + requiredRegionalFiles)
            .filter { !File(directoriesManager.getSystemDirectory(), it).exists() }
    }

    fun deleteBiosBefore(timestampMs: Long) {
        Timber.i("Pruning old bios files")
        _root_ide_package_.com.bigbratan.emulair.common.managers.bios.BiosManager.Companion.SUPPORTED_BIOS
            .map { File(directoriesManager.getSystemDirectory(), it.libretroFileName) }
            .filter { it.lastModified() < normalizeTimestamp(timestampMs) }
            .forEach {
                Timber.d("Pruning old bios file: ${it.path}")
                it.safeDelete()
            }
    }

    fun getBiosInfo(): _root_ide_package_.com.bigbratan.emulair.common.managers.bios.BiosManager.BiosInfo {
        val bios = _root_ide_package_.com.bigbratan.emulair.common.managers.bios.BiosManager.Companion.SUPPORTED_BIOS.groupBy {
            File(directoriesManager.getSystemDirectory(), it.libretroFileName).exists()
        }.withDefault { listOf() }

        return _root_ide_package_.com.bigbratan.emulair.common.managers.bios.BiosManager.BiosInfo(
            bios.getValue(true),
            bios.getValue(false)
        )
    }

    fun tryAddBiosAfter(
        storageFile: StorageFile,
        inputStream: InputStream,
        timestampMs: Long
    ): Boolean {
        val bios = findByCRC(storageFile) ?: findByName(storageFile) ?: return false

        Timber.i("Importing bios file: $bios")

        val biosFile = File(directoriesManager.getSystemDirectory(), bios.libretroFileName)
        if (biosFile.exists() && biosFile.setLastModified(normalizeTimestamp(timestampMs))) {
            Timber.d("Bios file already present. Updated last modification date.")
        } else {
            Timber.d("Bios file not available. Copying new file.")
            inputStream.writeToFile(biosFile)
        }
        return true
    }

    private fun findByCRC(storageFile: StorageFile): _root_ide_package_.com.bigbratan.emulair.common.managers.bios.Bios? {
        return crcLookup[storageFile.crc]
    }

    private fun findByName(storageFile: StorageFile): _root_ide_package_.com.bigbratan.emulair.common.managers.bios.Bios? {
        return nameLookup[storageFile.name]
    }

    private fun normalizeTimestamp(timestamp: Long) = (timestamp / 1000) * 1000

    data class BiosInfo(val detected: List<_root_ide_package_.com.bigbratan.emulair.common.managers.bios.Bios>, val notDetected: List<_root_ide_package_.com.bigbratan.emulair.common.managers.bios.Bios>)

    companion object {
        private val SUPPORTED_BIOS = listOf(
            _root_ide_package_.com.bigbratan.emulair.common.managers.bios.Bios(
                "scph101.bin",
                "6E3735FF4C7DC899EE98981385F6F3D0",
                "PS One 4.5 NTSC-U/C",
                SystemID.PSX,
                "171BDCEC"
            ),
            _root_ide_package_.com.bigbratan.emulair.common.managers.bios.Bios(
                "scph7001.bin",
                "1E68C231D0896B7EADCAD1D7D8E76129",
                "PS Original 4.1 NTSC-U/C",
                SystemID.PSX,
                "502224B6"
            ),
            _root_ide_package_.com.bigbratan.emulair.common.managers.bios.Bios(
                "scph5501.bin",
                "490F666E1AFB15B7362B406ED1CEA246",
                "PS Original 3.0 NTSC-U/C",
                SystemID.PSX,
                "8D8CB7E4"
            ),
            _root_ide_package_.com.bigbratan.emulair.common.managers.bios.Bios(
                "scph1001.bin",
                "924E392ED05558FFDB115408C263DCCF",
                "PS Original 2.2 NTSC-U/C",
                SystemID.PSX,
                "37157331"
            ),
            _root_ide_package_.com.bigbratan.emulair.common.managers.bios.Bios(
                "lynxboot.img",
                "FCD403DB69F54290B51035D82F835E7B",
                "Lynx Boot Image",
                SystemID.LYNX,
                "0D973C9D"
            ),
            _root_ide_package_.com.bigbratan.emulair.common.managers.bios.Bios(
                "bios_CD_E.bin",
                "E66FA1DC5820D254611FDCDBA0662372",
                "Sega CD E",
                SystemID.SEGACD,
                "529AC15A"
            ),
            _root_ide_package_.com.bigbratan.emulair.common.managers.bios.Bios(
                "bios_CD_J.bin",
                "278A9397D192149E84E820AC621A8EDD",
                "Sega CD J",
                SystemID.SEGACD,
                "9D2DA8F2"
            ),
            _root_ide_package_.com.bigbratan.emulair.common.managers.bios.Bios(
                "bios_CD_U.bin",
                "2EFD74E3232FF260E371B99F84024F7F",
                "Sega CD U",
                SystemID.SEGACD,
                "C6D10268"
            ),
            _root_ide_package_.com.bigbratan.emulair.common.managers.bios.Bios(
                "bios7.bin",
                "DF692A80A5B1BC90728BC3DFC76CD948",
                "Nintendo DS ARM7",
                SystemID.NDS,
                "1280F0D5"
            ),
            _root_ide_package_.com.bigbratan.emulair.common.managers.bios.Bios(
                "bios9.bin",
                "A392174EB3E572FED6447E956BDE4B25",
                "Nintendo DS ARM9",
                SystemID.NDS,
                "2AB23573"
            ),
            _root_ide_package_.com.bigbratan.emulair.common.managers.bios.Bios(
                "firmware.bin",
                "E45033D9B0FA6B0DE071292BBA7C9D13",
                "Nintendo DS Firmware",
                SystemID.NDS,
                "945F9DC9",
                "nds_firmware.bin"
            )
        )
    }
}
