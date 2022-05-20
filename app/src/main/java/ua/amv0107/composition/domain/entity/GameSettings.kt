package ua.amv0107.composition.domain.entity

import java.io.Serializable

data class GameSettings(
    val maxSumValue: Int,               //  Максимальное возможное значение
    val minCountOfRightAnswer: Int,     // Минимальное кол-во правильных ответов
    val minPercentOfRightAnswer: Int,   // минимальный процент правильных ответов
    val gameTimeInSeconds: Int          // вемя игры в секундах
): Serializable