package com.bigbratan.emulair.managerTilt

import com.swordfish.radialgamepad.library.RadialGamePad

class StickTiltTracker(val id: Int) : TiltTracker {

    override fun updateTracking(xTilt: Float, yTilt: Float, pads: Sequence<RadialGamePad>) {
        pads.forEach { it.simulateMotionEvent(id, xTilt, yTilt) }
    }

    override fun stopTracking(pads: Sequence<RadialGamePad>) {
        pads.forEach { it.simulateClearMotionEvent(id) }
    }

    override fun trackedIds(): Set<Int> = setOf(id)
}
