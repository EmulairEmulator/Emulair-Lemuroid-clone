package com.bigbratan.emulair.activityMain

import android.app.Activity

interface BusyActivity {
    fun activity(): Activity
    fun isBusy(): Boolean
}
