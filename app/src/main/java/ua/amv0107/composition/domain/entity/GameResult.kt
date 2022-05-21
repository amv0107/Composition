package ua.amv0107.composition.domain.entity

data class GameResult(
    val winner: Boolean,            // Значение победили мы или нет
    val countOfRightAnswers: Int,   // кол-во правильных ответов
    val countOfQuestions: Int,      // общее кол-во вопросов на которые ответи пользователь
    val gameSettings: GameSettings  // настройки игры
)