package com.example.calculator

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ActionMenuView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.example.calculator.databinding.ActivityMainBinding
import com.ezylang.evalex.Expression

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val numberStringBuilder: StringBuilder = StringBuilder()
    private val calculationHistory: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setListener()


    }

    private fun setListener() = with(binding) {

        oneButton.setOnClickListener {
            numberStringBuilder.append(1)
            resultTextView.text = numberStringBuilder
        }


        twoButton.setOnClickListener {
            numberStringBuilder.append(2)
            resultTextView.text = numberStringBuilder
        }

        threeButton.setOnClickListener {
            numberStringBuilder.append(3)
            resultTextView.text = numberStringBuilder
        }

        fourButton.setOnClickListener {
            numberStringBuilder.append(4)
            resultTextView.text = numberStringBuilder
        }

        fiveButton.setOnClickListener {
            numberStringBuilder.append(5)
            resultTextView.text = numberStringBuilder
        }

        sixButton.setOnClickListener {
            numberStringBuilder.append(6)
            resultTextView.text = numberStringBuilder
        }

        sevenButton.setOnClickListener {
            numberStringBuilder.append(7)
            resultTextView.text = numberStringBuilder
        }

        eightButton.setOnClickListener {
            numberStringBuilder.append(8)
            resultTextView.text = numberStringBuilder
        }

        nineButton.setOnClickListener {
            numberStringBuilder.append(9)
            resultTextView.text = numberStringBuilder
        }

        zeroButton.setOnClickListener {
            numberStringBuilder.append(0)
            resultTextView.text = numberStringBuilder
        }

        pointButton.setOnClickListener {

            val checkList: MutableList<String> = mutableListOf("/", "*", "-", "+", "%")

            val regex = checkList.joinToString(separator = "|") { Regex.escape(it) }

            val parts = numberStringBuilder.toString().split(Regex(regex))

            if ("." !in parts.last()) {
                numberStringBuilder.append(".")
                resultTextView.text = numberStringBuilder
            }
        }

        plusButton.setOnClickListener {
            checkingRepeatedCharacters("+")
        }

        minusButton.setOnClickListener {
            checkingRepeatedCharacters("-")
        }

        multiplyButton.setOnClickListener {
            checkingRepeatedCharacters("*")
        }

        divideButton.setOnClickListener {
            checkingRepeatedCharacters("")
        }

        percentButton.setOnClickListener {
            checkingRepeatedCharacters("%")
        }

        backSpaceButton.setOnClickListener {
            val lastIndex: Int = numberStringBuilder.indices.last

            if (lastIndex >= 0) {
                numberStringBuilder.deleteAt(lastIndex)
                resultTextView.text = numberStringBuilder

            } else {
                numberStringBuilder.append("")
            }

        }

        deleteAllButton.setOnClickListener {
            numberStringBuilder.clear()
            resultTextView.text = numberStringBuilder

        }

        equalButton.setOnClickListener {

            calculate()

        }

        historyButton.setOnClickListener {
            val intent = Intent(this@MainActivity, HistoryActivity::class.java)

            intent.putExtra("history_list", calculationHistory.toTypedArray())

            startActivity(intent)
        }


    }

    private fun ActivityMainBinding.checkingRepeatedCharacters(characters: String) {

        val checkList: MutableList<String> = mutableListOf("/", "*", "-", "+", "%")

        checkList.remove(characters)

        if (numberStringBuilder.isNotEmpty()) {
            if (numberStringBuilder.last().toString() in checkList || numberStringBuilder.last().toString() == characters) {
                val lastIndex: Int = numberStringBuilder.indices.last()
                numberStringBuilder.setCharAt(lastIndex, characters[0])
                resultTextView.text = numberStringBuilder
            } else {
                numberStringBuilder.append(characters)
                resultTextView.text = numberStringBuilder
            }
        }

    }

    private fun ActivityMainBinding.calculate() {

        if (numberStringBuilder.isEmpty()) {
            numberStringBuilder.isEmpty()
        } else {
            try {
                val expression = Expression(numberStringBuilder.toString())
                val expressionResult = expression.evaluate().stringValue

                saveToHistory(numberStringBuilder.toString())


                numberStringBuilder.clear().append(expressionResult)

                resultTextView.text = expressionResult
            } catch (t: Throwable) {
                Toast.makeText(
                    this@MainActivity, "Exception $t",
                    Toast.LENGTH_LONG
                ).show()

                if (t.message == "Division by zero") {

                    numberStringBuilder.clear().append("You can't divide by zero")
                    resultTextView.text = numberStringBuilder
                    numberStringBuilder.clear()

                } else if (t.message == "Number contains more than one decimal point") {

                    numberStringBuilder.clear().append(
                        "Number contains more than one decimal point"
                    )
                    resultTextView.text = numberStringBuilder
                    numberStringBuilder.clear()
                }
            }
        }

    }

    private fun saveToHistory(story: String) {

        if (calculationHistory.size > 10) {
            calculationHistory.removeAt(0)
            calculationHistory.add(story)
        } else {
            calculationHistory.add(story)
        }
    }
}