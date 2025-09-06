package ru.wolfram.divkitsamples.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.wolfram.divkitsamples.domain.usecase.ConnectUseCase
import ru.wolfram.divkitsamples.domain.usecase.DisconnectUseCase

class MainModel(
    private val connectUseCase: ConnectUseCase,
    private val disconnectUseCase: DisconnectUseCase,
    private val scope: CoroutineScope
) {
    fun connect() {
        scope.launch {
            connectUseCase()
        }
    }

    fun disconnect() {
        scope.launch {
            disconnectUseCase()
        }
    }
}