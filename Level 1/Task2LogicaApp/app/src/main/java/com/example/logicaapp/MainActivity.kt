package com.example.logicaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // Correct answers in order from top to bottom
    private val correctAnswers = arrayOf<String>("T", "F", "F", "F")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Set event on check button
        val checkBtn = findViewById<Button>(R.id.checkBtn)
        checkBtn.setOnClickListener() {
            checkConjunctions()
        }
    }

    /**
     * Checks number of correct conjunctions
     */
    private fun checkConjunctions() {
        var correct = 0

        // Check for each input if it is correct
        if (inputRow1.text.toString() == correctAnswers[0]) {
            correct++
        }
        if (inputRow2.text.toString() == correctAnswers[1]) {
            correct++
        }
        if (inputRow3.text.toString() == correctAnswers[2]) {
            correct++
        }
        if (inputRow4.text.toString() == correctAnswers[3]) {
            correct++
        }

        // Show toast with number of correct answers
        Toast.makeText(this@MainActivity, getString(R.string.number_answers_correct, correct), Toast.LENGTH_SHORT).show()
    }
}
