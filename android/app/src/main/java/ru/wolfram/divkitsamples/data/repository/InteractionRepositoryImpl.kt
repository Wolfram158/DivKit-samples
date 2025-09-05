package ru.wolfram.divkitsamples.data.repository

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import ru.wolfram.divkitsamples.data.network.EchoWebSocketListener
import ru.wolfram.divkitsamples.di.AppComponent
import ru.wolfram.divkitsamples.domain.repository.InteractionRepository

class InteractionRepositoryImpl(
    private val client: OkHttpClient,
    private val onMessage: (String) -> Unit
) : InteractionRepository {
    private var webSocket: WebSocket? = null

    override fun connect() {
        val request = Request.Builder().url(AppComponent.URL).build()
        val listener = EchoWebSocketListener(onMessage)
        webSocket = client.newWebSocket(request, listener)
    }

    override fun disconnect() {
        webSocket?.close(NORMAL_CLOSURE, null)
    }

    companion object {
        private const val NORMAL_CLOSURE = 1000
    }
}