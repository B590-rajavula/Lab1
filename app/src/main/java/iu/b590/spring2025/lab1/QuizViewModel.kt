package iu.b590.spring2025.lab1

import Question
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import java.util.Objects

private const val TAG = "QuizViewModel"
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val IS_CHEATER_KEY = "IS_CHEATER_KEY"
const val CHEATED_QUESTIONS_KEY = "CHEATED_QUESTIONS_KEY"

class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel(){
//    init{
//        Log.d(TAG, "ViewModel instance created")
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        Log.d(TAG, "ViewModel instance about to be destroyed")
//    }

    private val questionBank= listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, true),
        Question(R.string.question_africa, true),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

//    var isCheater: Boolean
//        get() = savedStateHandle.get(IS_CHEATER_KEY) ?: false
//        set(value) = savedStateHandle.set(IS_CHEATER_KEY, value)


    private var currentIndex
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    private var cheatedQuestions: MutableSet<Int>
        get() = savedStateHandle.get<MutableSet<Int>>(CHEATED_QUESTIONS_KEY) ?: mutableSetOf()
        set(value) = savedStateHandle.set(CHEATED_QUESTIONS_KEY, value)

    val currentQuestionAnswer:Boolean
        get() = questionBank[currentIndex].answer
    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext(){
        Log.d(TAG, "Updating question text", Exception())
        currentIndex = (currentIndex+1)% questionBank.size
    }

    fun moveToprev(){
        currentIndex = if (currentIndex == 0){
            questionBank.size-1
        } else{
            currentIndex-1
        }
    }

    val questionsize: Int
        get() = questionBank.size

    fun markQuestionAsCheated() {
        cheatedQuestions = cheatedQuestions.apply { add(currentIndex) }
    }

    fun isQuestionCheated(): Boolean {
        return cheatedQuestions.contains(currentIndex)
    }
}