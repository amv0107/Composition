package ua.amv0107.composition.domain.entity

data class Question(
    val sum: Int,           // Сумма
    val visibleNumber: Int, // Видимое число в левом квадрате
    val options: List<Int>  // Варианты ответов
) {
    val rightAnswer: Int
        get() = sum - visibleNumber
}