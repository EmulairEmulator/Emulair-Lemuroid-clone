package com.bigbratan.emulair.managerCovers

import android.content.Context
import android.widget.ImageView
import coil.ImageLoader
// import coil.disk.DiskCache
import coil.load
import coil.util.CoilUtils
import com.bigbratan.emulair.common.utils.drawable.TextDrawable
import com.bigbratan.emulair.common.utils.graphics.ColorUtils
import com.bigbratan.emulair.common.metadataRetrograde.db.entity.Game
import okhttp3.OkHttpClient

class CoverLoader(applicationContext: Context) {

    private val imageLoader = ImageLoader.Builder(applicationContext)
        .crossfade(true)
        // /*
        .okHttpClient {
            OkHttpClient.Builder()
                .cache(CoilUtils.createDefaultCache(applicationContext))
                .addNetworkInterceptor(ThrottleFailedThumbnailsInterceptor)
                .build()
        }
        // */
        /*
        .diskCache {
            DiskCache.Builder()
                .directory(applicationContext.cacheDir.resolve("image_cache"))
                .build()
        }
        .okHttpClient {
            OkHttpClient.Builder()
                .addNetworkInterceptor(ThrottleFailedThumbnailsInterceptor)
                .build()
        }
        */
        .build()

    fun loadCover(game: Game, imageView: ImageView?) {
        if (imageView == null) return

        imageView.load(game.coverFrontUrl, imageLoader) {
            val fallbackDrawable = getFallbackDrawable(game)
            fallback(fallbackDrawable)
            error(fallbackDrawable)
        }
    }

    fun cancelRequest(imageView: ImageView) {
        // coil-kt automatically does that for us
    }

    companion object {
        fun getFallbackDrawable(game: Game) =
            TextDrawable(computeTitle(game), computeColor(game))

        fun getFallbackRemoteUrl(game: Game): String {
            val color = Integer.toHexString(computeColor(game)).substring(2)
            val title = computeTitle(game)
            return "https://fakeimg.pl/512x512/$color/fff/?font=bebas&text=$title"
        }

        private fun computeTitle(game: Game): String {
            val sanitizedName = game.title
                .replace(Regex("\\(.*\\)"), "")

            return sanitizedName.asSequence()
                .filter { it.isDigit() or it.isUpperCase() or (it == '&') }
                .take(3)
                .joinToString("")
                .ifBlank { game.title.first().toString() }
                .replaceFirstChar(Char::titlecase)
        }

        private fun computeColor(game: Game): Int {
            return ColorUtils.randomColor(game.title)
        }
    }
}
