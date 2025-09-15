package ru.wolfram.divkitsamples.presentation

import android.view.ViewGroup
import androidx.core.view.children
import ru.wolfram.divkitsamples.presentation.player.ExoDivPlayerView

fun ViewGroup.release() {
    children.forEach { child ->
        when (child) {
            is ExoDivPlayerView -> {
                child.detach()
            }

            is ViewGroup -> {
                child.release()
            }
        }
    }
}