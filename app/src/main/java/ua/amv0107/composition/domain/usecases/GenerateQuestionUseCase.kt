package ua.amv0107.composition.domain.usecases

import ua.amv0107.composition.domain.entity.Question
import ua.amv0107.composition.domain.repository.GameRepository

// Генерирует вопрос

class GenerateQuestionUseCase(private val repository: GameRepository) {

    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object{
        private const val COUNT_OF_OPTIONS = 6
    }

}