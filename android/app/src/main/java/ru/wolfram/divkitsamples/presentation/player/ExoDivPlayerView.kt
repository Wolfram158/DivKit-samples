package ru.wolfram.divkitsamples.presentation.player

import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.util.AttributeSet
import android.util.Xml
import android.view.SurfaceView
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.yandex.div.core.player.DivPlayer
import com.yandex.div.core.player.DivPlayerView
import com.yandex.div2.DivVideoScale
import ru.wolfram.divkitsamples.R

internal class ExoDivPlayerView(context: Context) : DivPlayerView(context) {
    private var playerView = setupPlayerView { PlayerView(context, getAttributeSet()) }

    private var _attachedPlayer: DivPlayer? = null

    val player get() = _attachedPlayer

    private var didFallbackToSurfaceView: Boolean = false

    override fun attach(player: DivPlayer) {
        detach()
        playerView.player = (player as ExoDivPlayer).player
        _attachedPlayer = player
        player.setSource(player.src, player.config)
        player.prepare()
    }

    override fun detach() {
        playerView.player?.release()
        playerView.player = null
        _attachedPlayer = null
    }

    override fun getAttachedPlayer(): DivPlayer? = _attachedPlayer

    @OptIn(UnstableApi::class)
    override fun setScale(videoScale: DivVideoScale) {
        if (didFallbackToSurfaceView && videoScale == DivVideoScale.FILL) {
            TODO()
        }

        playerView.resizeMode = when (videoScale) {
            DivVideoScale.NO_SCALE -> AspectRatioFrameLayout.RESIZE_MODE_FIT
            DivVideoScale.FIT -> AspectRatioFrameLayout.RESIZE_MODE_FIT
            DivVideoScale.FILL -> AspectRatioFrameLayout.RESIZE_MODE_ZOOM
        }
    }

    override fun onAttachedToWindow() {
        if (!didFallbackToSurfaceView && !isHardwareAccelerated) {
            definitelyNoHardwareAcceleration = true
            didFallbackToSurfaceView = true

            val currentPlayer = (getAttachedPlayer() as? ExoDivPlayer)?.player ?: return

            playerView.player = null
            removeView(playerView)

            playerView = setupPlayerView { PlayerView(context) }.apply {
                player = currentPlayer
            }
        }

        super.onAttachedToWindow()
    }

    @OptIn(UnstableApi::class)
    private inline fun setupPlayerView(viewCreator: () -> PlayerView): PlayerView {
        return viewCreator().apply {
            useController = false
            setShutterBackgroundColor(Color.TRANSPARENT)
            (videoSurfaceView as? SurfaceView)?.apply {
                setZOrderOnTop(false)
                setBackgroundColor(Color.TRANSPARENT)
                holder.setFormat(PixelFormat.TRANSPARENT)
            }
        }.also {
            addView(it)
        }
    }

    private fun getAttributeSet(): AttributeSet? {
        if (definitelyNoHardwareAcceleration) {
            didFallbackToSurfaceView = true
            return null
        }
        attributeSet?.let {
            return it
        }

        var attrs: AttributeSet? = null
        synchronized(lock) {
            val parser = resources.getLayout(R.layout.player_view)
            var state: Int
            do {
                state = parser.next()
                if (parser.name == TYPE_STYLED_PLAYER_VIEW) {
                    attrs = Xml.asAttributeSet(parser)
                    break
                }
            } while (state != org.xmlpull.v1.XmlPullParser.END_DOCUMENT)

            if (attrs == null) {
                TODO()
            }

            attributeSet = attrs
        }
        return attrs
    }

    companion object {
        private const val TYPE_STYLED_PLAYER_VIEW = "androidx.media3.ui.PlayerView"
        private const val TAG = "ExoPlayerView"

        private val lock = Any()
        private var definitelyNoHardwareAcceleration: Boolean = false
        private var attributeSet: AttributeSet? = null
            get() {
                if (field == null) {
                    synchronized(lock) {
                        return field
                    }
                } else {
                    return field
                }
            }
    }
}