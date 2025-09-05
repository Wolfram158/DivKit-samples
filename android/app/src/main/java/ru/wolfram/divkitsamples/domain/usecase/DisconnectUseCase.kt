package ru.wolfram.divkitsamples.domain.usecase

import ru.wolfram.divkitsamples.domain.repository.InteractionRepository

class DisconnectUseCase(
    private val interactionRepository: InteractionRepository
) {
    operator fun invoke() = interactionRepository.disconnect()
}