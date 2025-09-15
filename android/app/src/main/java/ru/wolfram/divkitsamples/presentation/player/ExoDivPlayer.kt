package ru.wolfram.divkitsamples.presentation.player

import android.content.Context
import android.util.Log
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.yandex.div.core.player.DivPlayer
import com.yandex.div.core.player.DivPlayerPlaybackConfig
import com.yandex.div.core.player.DivVideoSource

class ExoDivPlayer(
    private val context: Context,
    val src: List<DivVideoSource>,
    val config: DivPlayerPlaybackConfig
) : DivPlayer {

    val player by lazy {
        Log.e("DivKit", "ExoPlayer created")
        ExoPlayer.Builder(context).build()
    }

    override fun pause() {
        player.pause()
    }

    override fun play() {
        player.play()
    }

    override fun release() {
        player.release()
    }

    override fun seek(toMs: Long) {
        Log.e("DivKit", "seek to ms: $toMs")
        player.seekTo(toMs)
    }

    fun prepare() {
        player.prepare()
    }

    @OptIn(UnstableApi::class)
    override fun setSource(
        sourceVariants: List<DivVideoSource>,
        config: DivPlayerPlaybackConfig
    ) {
        player.setMediaItems(sourceVariants.map { source ->
            MediaItem.Builder()
                .setMimeType(source.mimeType)
                .setUri(source.url)
                .build()
        })
    }
}