/*
 * GameLibraryProvider.kt
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

package com.bigbratan.emulair.common.managers.storage

import android.net.Uri
// import androidx.leanback.preference.LeanbackPreferenceFragment
import com.bigbratan.emulair.common.metadata.retrograde.db.entity.DataFile
import com.bigbratan.emulair.common.metadata.retrograde.db.entity.Game
import java.io.InputStream
import kotlinx.coroutines.flow.Flow

interface StorageProvider {

    val id: String

    val name: String

    val uriSchemes: List<String>

    // val prefsFragmentClass: Class<out LeanbackPreferenceFragment>?

    val enabledByDefault: Boolean

    fun listBaseStorageFiles(): Flow<List<BaseStorageFile>>

    fun getInputStream(uri: Uri): InputStream?

    fun getStorageFile(baseStorageFile: BaseStorageFile): StorageFile?

    fun getGameRomFiles(
        game: Game,
        dataFiles: List<DataFile>,
        allowVirtualFiles: Boolean
    ): RomFiles
}
