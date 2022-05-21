package ua.amv0107.composition.domain.entity

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameSettings(
    val maxSumValue: Int,               //  Максимальное возможное значение
    val minCountOfRightAnswer: Int,     // Минимальное кол-во правильных ответов
    val minPercentOfRightAnswer: Int,   // минимальный процент правильных ответов
    val gameTimeInSeconds: Int          // вемя игры в секундах
): Parcelable