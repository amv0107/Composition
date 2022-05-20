package ua.amv0107.composition.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Level : Parcelable {
    // Класс перечислений уровней
    TEST, EASY, NORMAL, HARD
}