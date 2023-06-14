package com.bigbratan.emulair.metadata.libretro.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bigbratan.emulair.metadata.libretro.db.dao.GameDao
import com.bigbratan.emulair.metadata.libretro.db.entity.LibretroRom

@Database(
    entities = [LibretroRom::class],
    version = 8,
    exportSchema = false
)
abstract class LibretroDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
}
