package ua.amv0107.composition.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ua.amv0107.composition.R
import ua.amv0107.composition.data.GemaRepositoryImpl
import ua.amv0107.composition.domain.entity.GameResult
import ua.amv0107.composition.domain.entity.GameSettings
import ua.amv0107.composition.domain.entity.Level
import ua.amv0107.composition.domain.entity.Question
import ua.amv0107.composition.domain.usecases.GenerateQuestionUseCase
import ua.amv0107.composition.domain.usecases.GetGameSettingsUseCase

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var gameSettings: GameSettings
    private lateinit var level: Level

    private val context = application
    private val repository = GemaRepositoryImpl

    private var generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private var timer: CountDownTimer? = null

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private var countOfRightAnswers = 0
    private var countOfQuestions = 0

    private val _percentRightAnswer = MutableLiveData<Int>()
    val percentRightAnswer: LiveData<Int>
        get() = _percentRightAnswer

    private val _progressAnswer = MutableLiveData<String>()
    val progressAnswer: LiveData<String>
        get() = _progressAnswer

    private val _enoughCount = MutableLiveData<Boolean>()
    val enoughCount: LiveData<Boolean>
        get() = _enoughCount

    private val _enoughPercent = MutableLiveData<Boolean>()
    val enoughPercent: LiveData<Boolean>
        get() = _enoughPercent

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    fun startGame(level: Level) {
        getGameSettings(level)
        startTimer()
        generateQuestion()
    }

    fun chooseAnswer(numder: Int) {
        checkAnswer(numder)
        updateProgress()
        generateQuestion()
    }

    private fun updateProgress() {
        val percent = calculatePercentOfRightAnswer()
        _percentRightAnswer.value = percent
        _progressAnswer.value = String.format(
            context.resources.getString(R.string.progress_answers),
            countOfRightAnswers,
            gameSettings.minCountOfRightAnswer
        )
        _enoughCount.value = countOfRightAnswers >= gameSettings.minCountOfRightAnswer
        _enoughPercent.value = percent >= gameSettings.minPercentOfRightAnswer
    }

    private fun calculatePercentOfRightAnswer(): Int {
        return ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }

    private fun checkAnswer(numder: Int) {
        val rightAnswer = question.value?.rightAnswer
        if (numder == rightAnswer) {
            countOfRightAnswers++
        }
        countOfQuestions++
    }

    private fun getGameSettings(level: Level) {
        this.level = level
        this.gameSettings = getGameSettingsUseCase(level)
        this._minPercent.value = gameSettings.minPercentOfRightAnswer
    }

    private fun startTimer() {
        timer =
            object : CountDownTimer(
                gameSettings.gameTimeInSeconds * MILLIS_IN_SECONDS,
                1000
            ) {
                override fun onTick(millisUntilFinished: Long) {
                    _formattedTime.value = formatTime(millisUntilFinished)
                }

                override fun onFinish() {
                    finishGame()
                }
            }
        timer?.start()
    }

    private fun generateQuestion() {
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    private fun formatTime(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTES)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            enoughCount.value == true && enoughPercent.value == true,
            countOfRightAnswers,
            countOfQuestions,
            gameSettings
        )
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60
    }
}