package com.example.mycalculator

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    private lateinit var display: TextView
    private var current = "0"
    private var first = 0.0
    private var operator = ""
    private var newNumber = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(16, 30, 16, 16)
        layout.setBackgroundColor(Color.WHITE)

        display = TextView(this)
        display.text = "0"
        display.textSize = 40f
        display.gravity = Gravity.END or Gravity.CENTER_VERTICAL
        display.setTextColor(Color.BLACK)
        layout.addView(display, LinearLayout.LayoutParams(-1, 120))

        val keys = listOf(
            listOf("7", "8", "9", "÷"),
            listOf("4", "5", "6", "×"),
            listOf("1", "2", "3", "-"),
            listOf("C", "0", "=", "+")
        )

        for (rowKeys in keys) {
            val row = LinearLayout(this)
            row.orientation = LinearLayout.HORIZONTAL

            for (key in rowKeys) {
                val button = Button(this)
                button.text = key
                button.textSize = 22f
                row.addView(button, LinearLayout.LayoutParams(0, 90, 1f))
                button.setOnClickListener { press(key) }
            }

            layout.addView(row)
        }

        setContentView(layout)
    }

    private fun press(key: String) {
        when (key) {
            "C" -> {
                current = "0"
                first = 0.0
                operator = ""
                newNumber = true
            }
            "+", "-", "×", "÷" -> {
                first = current.toDoubleOrNull() ?: 0.0
                operator = key
                newNumber = true
            }
            "=" -> {
                val second = current.toDoubleOrNull() ?: 0.0
                val result = when (operator) {
                    "+" -> first + second
                    "-" -> first - second
                    "×" -> first * second
                    "÷" -> if (second != 0.0) first / second else Double.NaN
                    else -> second
                }
                current = if (result.isNaN()) "Error"
                else if (result % 1.0 == 0.0) result.toLong().toString()
                else result.toString()
                operator = ""
                newNumber = true
            }
            else -> {
                if (newNumber || current == "0" || current == "Error") {
                    current = key
                    newNumber = false
                } else {
                    current += key
                }
            }
        }
        display.text = current
    }
}
