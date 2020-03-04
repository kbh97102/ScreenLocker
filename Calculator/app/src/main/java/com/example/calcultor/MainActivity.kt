package com.example.calcultor

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var numberValue: HashMap<Button, String> = hashMapOf()
    private var featureValue: HashMap<Button, String> = hashMapOf()
    private var firstNumber = 0
    private var secondNumber = 0
    private var results: Int = 0
    private var isFirstFilled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeButtonValue()
        setButtonEvent()
        setTextChangeListener()

        var imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(firstNumberView.windowToken, 0)
        imm.hideSoftInputFromWindow(secondNumberView.windowToken, 0)
    }

    private fun initializeButtonValue() {
        numberValue.put(num_0, "0")
        numberValue.put(num_1, "1")
        numberValue.put(num_2, "2")
        numberValue.put(num_3, "3")
        numberValue.put(num_4, "4")
        numberValue.put(num_5, "5")
        numberValue.put(num_6, "6")
        numberValue.put(num_7, "7")
        numberValue.put(num_8, "8")
        numberValue.put(num_9, "9")
        featureValue[plus] = "+"
        featureValue[minus] = "-"
        featureValue[division] = "/"
        featureValue[multiply] = "*"
    }

    private fun setButtonEvent() {
        var iter: Iterator<Button> = numberValue.keys.iterator()
        while (iter.hasNext()) {
            var btn = iter.next()
            btn.setOnClickListener {
                when (isFirstFilled) {
                    true -> setSecondButtonEvent(btn)
                    false -> setFirstButtonEvent(btn)
                }
            }
        }
        result.setOnClickListener { calculateLogic() }
    }

    private fun setFirstButtonEvent(btn: Button) {
        firstNumber = numberValue[btn]!!.toInt()
        firstNumberView.setText(numberValue[btn])
    }

    private fun setSecondButtonEvent(btn: Button) {
        secondNumber = numberValue[btn]!!.toInt()
        secondNumberView.setText(numberValue[btn])
    }

    private fun calculateLogic() {
        results = firstNumber + secondNumber

        firstNumberView.setText(results.toString())
        secondNumberView.setText("")
        isFirstFilled = false
    }

    private fun setTextChangeListener() {
        firstNumberView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                isFirstFilled = true
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        secondNumberView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                isFirstFilled = false
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }
}
