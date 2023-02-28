package com.bigbratan.emulair.common.utils.db

import android.database.Cursor

fun Cursor.asSequence(): Sequence<Cursor> = generateSequence { if (moveToNext()) this else null }
