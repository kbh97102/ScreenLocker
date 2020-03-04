package com.example.calcultor

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    private var numberValue: HashMap<Button, Int> = hashMapOf()
    private var firstNumber = 0
    private var secondNumber = 0
    private var currentFeature: Button? = null
    private var isFirstFilled = false
    private var digit = 0

    private var firstNumber2: String = ""
    private var secondNumber2: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeButtonValue()
        setButtonEvent()

        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(numberView.windowToken, 0)
    }

    private fun initializeButtonValue() {
        numberValue.put(num_0, 0)
        numberValue.put(num_1, 1)
        numberValue.put(num_2, 2)
        numberValue.put(num_3, 3)
        numberValue.put(num_4, 4)
        numberValue.put(num_5, 5)
        numberValue.put(num_6, 6)
        numberValue.put(num_7, 7)
        numberValue.put(num_8, 8)
        numberValue.put(num_9, 9)
    }

    private fun setButtonEvent() {
        val iter: Iterator<Button> = numberValue.keys.iterator()
        while (iter.hasNext()) {
            val btn = iter.next()
            btn.setOnClickListener {
                setNumberButtonEvent(btn)
            }
        }
        plus.setOnClickListener { setFeatureEvent(plus) }
        minus.setOnClickListener { setFeatureEvent(minus) }
        division.setOnClickListener { setFeatureEvent(division) }
        multiply.setOnClickListener { setFeatureEvent(multiply) }
        equal.setOnClickListener { calculate2() }
    }

    private fun setNumberButtonEvent(btn: Button) {
        if (isFirstFilled) {
//            secondNumber += getNumber(numberValue[btn]!!, secondNumber)
//            numberView.setText(secondNumber.toString())
//            processView.append(numberValue[btn].toString())
            secondNumber2 += btn.text
            numberView.setText(secondNumber2)
            processView.append(btn.text)
        } else {
//            firstNumber += getNumber(numberValue[btn]!!, firstNumber)
//            numberView.setText(firstNumber.toString())
//            processView.append(numberValue[btn].toString())
            firstNumber2 += btn.text
            numberView.setText(firstNumber2)
            processView.append(btn.text)
        }
    }

    private fun getNumber(number: Int, target: Int): Int {
        if (number == 0) {
            return (target * 10.toDouble().pow(digit.toDouble()).toInt()) - target
        }
        var result = 0
        result = if (digit > 0) {
            (number * 10.toDouble().pow(digit.toDouble())).toInt()
        } else {
            number
        }
        digit++
        return result
    }

    private fun setFeatureEvent(btn: Button) {
        isFirstFilled = true
        digit = 0
        currentFeature = btn
        processView.append(btn.text)
    }

    private fun calculate() {
        if (firstNumber == 0 || secondNumber == 0 || Objects.isNull(currentFeature)) {
            return
        }
        var calculateResult = 0
        when (currentFeature) {
            plus -> calculateResult = firstNumber + secondNumber
            minus -> calculateResult = firstNumber - secondNumber
            division -> calculateResult = (firstNumber / secondNumber)
            multiply -> calculateResult = firstNumber * secondNumber
        }
        numberView.setText(calculateResult.toString())
        firstNumber = calculateResult
        secondNumber = 0
        digit = 0
        processView.append("=")
        processView.append(calculateResult.toString())
    }

    private fun calculate2() {
        if (firstNumber2 == "" || secondNumber2 == "" || Objects.isNull(currentFeature)) {
            return
        }
        var calculateResult = 0
        when (currentFeature) {
            plus -> calculateResult = firstNumber2.toInt() + secondNumber2.toInt()
            minus -> calculateResult = firstNumber2.toInt() - secondNumber2.toInt()
            division -> calculateResult = firstNumber2.toInt() / secondNumber2.toInt()
            multiply -> calculateResult = firstNumber2.toInt() * secondNumber2.toInt()
        }
        numberView.setText(calculateResult.toString())
        processView.append("=")
        processView.append(calculateResult.toString())
        firstNumber2 = calculateResult.toString()
        secondNumber2 = ""
        digit = 0
    }
}
