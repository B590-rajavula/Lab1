package iu.b590.spring2025.proj1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
//It is used for the button and TextView events
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    // Declaring TextView for displaying values
    private lateinit var textval: TextView

    // Declaring buttons for numbers
    private lateinit var btn0: Button
    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var btn3: Button
    private lateinit var btn4: Button
    private lateinit var btn5: Button
    private lateinit var btn6: Button
    private lateinit var btn7: Button
    private lateinit var btn8: Button
    private lateinit var btn9: Button
    private lateinit var btnDot: Button

    // Declaring buttons for arithmetic operations
    private lateinit var btnAdd: Button
    private lateinit var btnSubtract: Button
    private lateinit var btnMultiply: Button
    private lateinit var btnDivide: Button

    // Declaring functional buttons
    private lateinit var btnEquals: Button
    private lateinit var btnClear: Button
    private lateinit var btnNegate: Button
    private lateinit var btnPercent: Button

    // Variables to store values for calculations
    private var currentNumber = ""
    private var previous = ""
    private var operator = ""
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()
        // Initializing TextView
        textval = findViewById(R.id.textval)

        // Initializing number buttons
        btn0 = findViewById(R.id.btn0)
        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)
        btn4 = findViewById(R.id.btn4)
        btn5 = findViewById(R.id.btn5)
        btn6 = findViewById(R.id.btn6)
        btn7 = findViewById(R.id.btn7)
        btn8 = findViewById(R.id.btn8)
        btn9 = findViewById(R.id.btn9)
        btnDot = findViewById(R.id.btnDecimal)

        // Initializing operator buttons
        btnAdd = findViewById(R.id.btnAdd)
        btnSubtract = findViewById(R.id.btnSubtract)
        btnMultiply = findViewById(R.id.btnMultiply)
        btnDivide = findViewById(R.id.btnDivide)

        // Initializing functional buttons
        btnEquals = findViewById(R.id.btnEquals)
        btnClear = findViewById(R.id.btnClear)
        btnNegate = findViewById(R.id.btnSign)
        btnPercent = findViewById(R.id.btnPercent)

        // Setting click listeners for number buttons
        btn0.setOnClickListener { addNumber("0") }
        btn1.setOnClickListener { addNumber("1") }
        btn2.setOnClickListener { addNumber("2") }
        btn3.setOnClickListener { addNumber("3") }
        btn4.setOnClickListener { addNumber("4") }
        btn5.setOnClickListener { addNumber("5") }
        btn6.setOnClickListener { addNumber("6") }
        btn7.setOnClickListener { addNumber("7") }
        btn8.setOnClickListener { addNumber("8") }
        btn9.setOnClickListener { addNumber("9") }
        btnDot.setOnClickListener { addNumber(".") }

        // Setting click listeners for operator buttons
        btnAdd.setOnClickListener { btnoperator("+") }
        btnSubtract.setOnClickListener { btnoperator("-") }
        btnMultiply.setOnClickListener { btnoperator("x") }
        btnDivide.setOnClickListener { btnoperator("/") }

        // Setting click listeners for functional buttons
        btnEquals.setOnClickListener { finalResult() }
        btnClear.setOnClickListener { clearAll() }
        btnNegate.setOnClickListener { negateNumber() }
        btnPercent.setOnClickListener { percentage() }
    }

    /**
     * adds a number input and updates the display.
     */
    private fun addNumber(num: String) {
        if (isNewOperation) {
            currentNumber = ""
            isNewOperation = false
        }

        if (num == "." && currentNumber.isEmpty()) {
            currentNumber = "0."
        } else if (!(num == "." && currentNumber.contains("."))) {
            currentNumber += num
        }

        textval.text = currentNumber
    }

    /**
     * Stores the selected operator and prepares for the next number.
     */
    private fun btnoperator(op: String) {
        if (currentNumber.isNotEmpty()) {
            if (previous.isEmpty()) {
                previous = currentNumber
            }
            operator = op
            isNewOperation = true
        } else if (previous.isNotEmpty()) {
            operator = op
            isNewOperation = true
        }
    }

    /**
     * Performs the calculation based on the selected operator and updates the display.
     */
    private fun finalResult() {
        if (previous.isNotEmpty() && operator.isNotEmpty() && currentNumber.isNotEmpty()) {
            val result: Double = when (operator) {
                "+" -> previous.toDouble() + currentNumber.toDouble()
                "-" -> previous.toDouble() - currentNumber.toDouble()
                "x" -> previous.toDouble() * currentNumber.toDouble()
                "/" -> previous.toDouble() / currentNumber.toDouble()
                else -> 0.0
            }

            textval.text = if (result % 1 != 0.0) result.toString() else result.toInt().toString()

            // Store result and reset variables
            previous = textval.text.toString()
            currentNumber = ""
            operator = ""
            isNewOperation = true
        }
    }

    /**
     * Clears all values and resets the calculator.
     */
    private fun clearAll() {
        currentNumber = ""
        previous = ""
        operator = ""
        isNewOperation = true
        textval.text = "0"
    }

    /**
     * Negates the current number (changes sign).
     */
    private fun negateNumber() {
        val value = when {
            currentNumber.isNotEmpty() -> currentNumber.toDouble()
            textval.text.isNotEmpty() -> textval.text.toString().toDouble()
            else -> return
        }

        val negatedValue = -value
        currentNumber = if (negatedValue % 1 == 0.0) negatedValue.toInt().toString() else negatedValue.toString()
        textval.text = currentNumber
    }

    /**
     * Converts the current number to a percentage (divides by 100).
     */
    private fun percentage() {
        val value = when {
            currentNumber.isNotEmpty() -> currentNumber.toDouble()
            textval.text.isNotEmpty() -> textval.text.toString().toDouble()
            else -> return
        }

        val result = value / 100
        currentNumber = if (result % 1 == 0.0) result.toInt().toString() else result.toString()
        textval.text = currentNumber
    }
}
