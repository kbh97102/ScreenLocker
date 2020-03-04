package com.example.calcultor

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {

    private var numberValue: HashMap<Button, Int> = hashMapOf()
    private var firstNumber = 0
    private var secondNumber = 0
    private var currentFeature: Button? = null

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
        plus.setOnClickListener { currentFeature = plus }
        minus.setOnClickListener { currentFeature = minus }
        division.setOnClickListener { currentFeature = division }
        multiply.setOnClickListener { currentFeature = multiply }
        equal.setOnClickListener { calculate() }
    }

    private fun setNumberButtonEvent(btn: Button) {
        if(firstNumber == 0){
            firstNumber = numberValue[btn]!!
            numberView.setText(firstNumber.toString())
        }
        else{
            secondNumber = numberValue[btn]!!
            numberView.setText(secondNumber.toString())
        }
    }

    private fun calculate() {
        if (firstNumber == 0 || secondNumber == 0 || Objects.isNull(currentFeature)){
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
    }

}
