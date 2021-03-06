package ua.amv0107.composition.data

import ua.amv0107.composition.domain.entity.GameSettings
import ua.amv0107.composition.domain.entity.Level
import ua.amv0107.composition.domain.entity.Question
import ua.amv0107.composition.domain.repository.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GemaRepositoryImpl : GameRepository {

    private const val MIN_SUM_VALUE = 2
    private const val MIN_ANSWER_VALUE = 1

    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE, sum)
        val option = HashSet<Int>() // TODO: Что такое HashSet<>
        val rightAnswer = sum - visibleNumber
        option.add(rightAnswer)
        val from = max(rightAnswer - countOfOptions, MIN_ANSWER_VALUE)
        val to = min(maxSumValue, rightAnswer + countOfOptions)
        while (option.size < countOfOptions) {
            option.add(Random.nextInt(from, to))
        }
        return  Question(sum,visibleNumber, option.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when(level){
            Level.TEST -> GameSettings(
                10,
                3,
                50,
                8
            )
            Level.EASY -> GameSettings(
                10,
                10,
                70,
                60
            )
            Level.NORMAL -> GameSettings(
                20,
                20,
                80,
                40
            )
            Level.HARD -> GameSettings(
                30,
                30,
                90,
                40
            )
        }
    }
}