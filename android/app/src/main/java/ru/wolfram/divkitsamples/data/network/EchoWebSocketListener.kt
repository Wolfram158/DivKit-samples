package ru.wolfram.divkitsamples.data.network

import okhttp3.WebSocket
import okhttp3.WebSocketListener

class EchoWebSocketListener(
    private val onMessage: (String) -> Unit
) : WebSocketListener() {
    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        onMessage(text)
    }

}