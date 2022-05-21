package ua.amv0107.composition.domain.repository

import ua.amv0107.composition.domain.entity.GameSettings
import ua.amv0107.composition.domain.entity.Level
import ua.amv0107.composition.domain.entity.Question

interface GameRepository {
    fun generateQuestion(
        maxSumValue: Int,   // Максимальное значение
        countOfOptions: Int // кол-во вариантов ответов
    ) : Question

    fun getGameSettings(level: Level): GameSettings
}