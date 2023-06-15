package com.bigbratan.emulair.activities.pauseMenu.main

import android.app.Activity

interface BusyActivity {
    fun activity(): Activity
    fun isBusy(): Boolean
}
