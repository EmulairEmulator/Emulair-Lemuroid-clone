package com.bigbratan.emulair.activities.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.bigbratan.emulair.R
import com.bigbratan.emulair.activities.game.BaseGameActivity
import com.bigbratan.emulair.activities.crash.GameCrashActivity
import com.bigbratan.emulair.managers.saveSync.SaveSyncWork
import com.bigbratan.emulair.managers.storage.cache.CacheCleanerWork
import com.bigbratan.emulair.ext.managers.review.ReviewManager
import com.bigbratan.emulair.common.metadata.retrograde.db.RetrogradeDatabase
import com.bigbratan.emulair.common.metadata.retrograde.db.entity.Game
import kotlinx.coroutines.delay

class GameLaunchTaskHandler(
    private val reviewManager: ReviewManager,
    private val retrogradeDb: RetrogradeDatabase
) {

    fun handleGameStart(context: Context) {
        cancelBackgroundWork(context)
    }

    suspend fun handleGameFinish(
        enableRatingFlow: Boolean,
        activity: Activity,
        resultCode: Int,
        data: Intent?
    ) {
        rescheduleBackgroundWork(activity.applicationContext)
        when (resultCode) {
            Activity.RESULT_OK -> handleSuccessfulGameFinish(activity, enableRatingFlow, data)
            BaseGameActivity.RESULT_ERROR -> handleUnsuccessfulGameFinish(
                activity,
                data?.getStringExtra(BaseGameActivity.PLAY_GAME_RESULT_ERROR)!!,
                null
            )
            BaseGameActivity.RESULT_UNEXPECTED_ERROR -> handleUnsuccessfulGameFinish(
                activity,
                activity.getString(R.string.crash_message),
                data?.getStringExtra(BaseGameActivity.PLAY_GAME_RESULT_ERROR)
            )
        }
    }

    private fun cancelBackgroundWork(context: Context) {
        SaveSyncWork.cancelAutoWork(context)
        SaveSyncWork.cancelManualWork(context)
        CacheCleanerWork.cancelCleanCacheLRU(context)
    }

    private fun rescheduleBackgroundWork(context: Context) {
        // Let's slightly delay the sync. Maybe the user wants to play another game.
        SaveSyncWork.enqueueAutoWork(context, 5)
        CacheCleanerWork.enqueueCleanCacheLRU(context)
    }

    private fun handleUnsuccessfulGameFinish(
        activity: Activity,
        message: String,
        messageDetail: String?
    ) {
        GameCrashActivity.launch(activity, message, messageDetail)
    }

    private suspend fun handleSuccessfulGameFinish(
        activity: Activity,
        enableRatingFlow: Boolean,
        data: Intent?
    ) {
        val duration = data?.extras?.getLong(BaseGameActivity.PLAY_GAME_RESULT_SESSION_DURATION)
            ?: 0L
        val game = data?.extras?.getSerializable(BaseGameActivity.PLAY_GAME_RESULT_GAME) as Game

        updateGamePlayedTimestamp(game)
        if (enableRatingFlow) {
            displayReviewRequest(activity, duration)
        }
    }

    private suspend fun displayReviewRequest(activity: Activity, durationMillis: Long) {
        delay(500)
        reviewManager.launchReviewFlow(activity, durationMillis)
    }

    private suspend fun updateGamePlayedTimestamp(game: Game) {
        retrogradeDb.gameDao().update(game.copy(lastPlayedAt = System.currentTimeMillis()))
    }
}
