package ru.wolfram.divkitsamples.domain.usecase

import ru.wolfram.divkitsamples.domain.repository.InteractionRepository

class ConnectUseCase(
    private val interactionRepository: InteractionRepository
) {
    operator fun invoke() = interactionRepository.connect()
}