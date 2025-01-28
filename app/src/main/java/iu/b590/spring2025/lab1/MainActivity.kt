package iu.b590.spring2025.lab1

import Question
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import iu.b590.spring2025.lab1.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
//    private lateinit var trueButton: Button
//    private lateinit var falseButton: Button

    private val questionBank= listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, true),
        Question(R.string.question_africa, true),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    private var currentIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "ONCreate(Bundle?) called")
        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        trueButton = findViewById(R.id.true_button)
//        falseButton = findViewById(R.id.false_button)
//        trueButton.setOnClickListener{
//            view: View ->
        binding.trueButton.setOnClickListener{
            view: View ->
//            Toast.makeText(
//                this,
//                R.string.correct_toast,
//                Toast.LENGTH_SHORT
//            ).show()
            checkAnswer(true)
        }
        binding.falseButton.setOnClickListener{
                view: View ->
//            Toast.makeText(
//                this,
//                R.string.correct_toast,
//                Toast.LENGTH_SHORT
//            ).show()
            checkAnswer(false)
        }
        binding.nextButton.setOnClickListener{
            currentIndex = (currentIndex+1)%questionBank.size
            updateQuestion()
            binding.trueButton.isEnabled = true
            binding.trueButton.isClickable = true
            binding.falseButton.isEnabled = true
            binding.falseButton.isClickable = true
        }

        binding.previousButton.setOnClickListener {
            currentIndex = if (currentIndex == 0){
                questionBank.size-1
            } else{
                currentIndex-1
            }
            updateQuestion()
            binding.trueButton.isEnabled = true
            binding.trueButton.isClickable = true
            binding.falseButton.isEnabled = true
            binding.falseButton.isClickable = true
        }

        binding.questionTextView.setOnClickListener{
            currentIndex = (currentIndex+1)%questionBank.size
            updateQuestion()
        }

//        val questionTextResId = questionBank[currentIndex].textResId
//        binding.questionTextView.setText(questionTextResId)
        updateQuestion()
    }

    override fun onStart(){
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override  fun onResume(){
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override  fun onPause(){
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override  fun onStop(){
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override  fun onDestroy(){
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }




    private  fun updateQuestion(){
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer:Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast

        } else {
            R.string.incorrect_toast
        }
        binding.trueButton.isEnabled = false
        binding.trueButton.isClickable = false
        binding.falseButton.isEnabled = false
        binding.falseButton.isClickable = false



        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
}