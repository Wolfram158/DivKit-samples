package ru.wolfram.divkitsamples.di

import okhttp3.OkHttpClient
import ru.wolfram.divkitsamples.data.repository.InteractionRepositoryImpl
import ru.wolfram.divkitsamples.domain.usecase.ConnectUseCase
import ru.wolfram.divkitsamples.domain.usecase.DisconnectUseCase
import ru.wolfram.divkitsamples.presentation.MainActivity
import ru.wolfram.divkitsamples.presentation.MainModel

class AppComponent {
    private lateinit var onMessage: (String) -> Unit

    fun addOnMessage(onMessage: (String) -> Unit): AppComponent {
        this.onMessage = onMessage
        return this
    }

    fun inject(mainActivity: MainActivity) {
        val client = OkHttpClient()
        val interactionRepository = InteractionRepositoryImpl(client, onMessage)
        val connectUseCase = ConnectUseCase(interactionRepository)
        val disconnectUseCase = DisconnectUseCase(interactionRepository)
        mainActivity.setMainModel(MainModel(connectUseCase, disconnectUseCase))
    }

    companion object {
        const val URL = "ws://10.0.2.2:8080/view"
    }
}