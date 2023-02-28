package com.bigbratan.emulair.common.utils.files

import java.io.File
import java.util.Locale

fun File.safeDelete() = exists() && delete()

class FileUtils {
    companion object {
        fun extractExtension(fileName: String): String =
            fileName.substringAfterLast(".", "").toLowerCase(Locale.US)

        fun discardExtension(fileName: String): String = fileName.substringBeforeLast(".")
    }
}
