package com.bigbratan

import android.app.Activity
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith

@RunWith(MockitoJUnitRunner::class)
class GameManagerTests {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Mock
    private lateinit var activity: Activity
    @Test
    fun onGamePlay() {
        val mockActivity = mock<Activity> {
            on isBusy doReturn false
        }
        val result = GameContextMenuListener.onGamePlay(mockActivity)
        assertEquals(false, result)
    }
}

