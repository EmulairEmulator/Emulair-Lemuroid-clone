package com.example.diceroller

import android.app.Activity
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

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
}
