package com.bigbratan.emulair.utils.systems

import android.content.Context
import com.bigbratan.emulair.common.metadata.retrograde.MetaSystemID

data class SystemUtils(val metaSystemId: MetaSystemID, val count: Int) {
    fun getName(context: Context) = context.resources.getString(metaSystemId.titleResId)
}
