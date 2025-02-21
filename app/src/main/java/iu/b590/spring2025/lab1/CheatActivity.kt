package iu.b590.spring2025.lab1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import iu.b590.spring2025.lab1.databinding.ActivityCheatBinding
private const val EXTRA_ANSWER_IS_TRUE="iu.b590.spring2025.lab1.answer_is_true"
const val EXTRA_ANSWER_SHOWN = "iu.b590.spring2025.lab1.answer_shown"
private const val KEY_ANSWER_SHOWN = "answer_shown"

class CheatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheatBinding
    private var answerIsTrue = false
    private var isAnswerShown = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        isAnswerShown = savedInstanceState?.getBoolean(KEY_ANSWER_SHOWN, false) ?: false
        if (isAnswerShown) {
            showAnswer() // Restore answer visibility
            setAnswerShownResult(true)
        }

        binding.showAnswerButton.setOnClickListener {
            showAnswer()
            isAnswerShown = true
            setAnswerShownResult(true)
        }
    }

    private fun showAnswer() {
        val answerText = if (answerIsTrue) R.string.true_button else R.string.false_button
        binding.answerTextView.setText(answerText)
    }

//        binding.showAnswerButton.setOnClickListener {
//            val answerText = when {
//                answerIsTrue -> R.string.true_button
//                else -> R.string.false_button
//            }
//            binding.answerTextView.setText(answerText)
//            setAnswerShownResult(true)
//        }
//    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_ANSWER_SHOWN, isAnswerShown) // Save cheat state
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue:
        Boolean): Intent {
            return Intent(packageContext, CheatActivity::
            class.java).apply{
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}