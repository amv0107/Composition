package ua.amv0107.composition.domain.usecases

import ua.amv0107.composition.domain.entity.GameSettings
import ua.amv0107.composition.domain.entity.Level
import ua.amv0107.composition.domain.repository.GameRepository

// Загружаем настройки игры в зависимости от уровня

class GetGameSettingsUseCase(private val repository: GameRepository) {

    operator fun invoke(level: Level): GameSettings{
        return repository.getGameSettings(level)
    }
}