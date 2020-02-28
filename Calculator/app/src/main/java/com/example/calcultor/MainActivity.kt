package com.example.calcultor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var workOrder: PriorityQueue<String> = PriorityQueue()
    private var buttonValue: HashMap<Button, String> = hashMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeButtonValue()
        setButtonEvent()
    }

    private fun initializeButtonValue() {
        buttonValue.put(num_0, "0")
        buttonValue.put(num_1, "1")
        buttonValue.put(num_2, "2")
        buttonValue.put(num_3, "3")
        buttonValue.put(num_4, "4")
        buttonValue.put(num_5, "5")
        buttonValue.put(num_6, "6")
        buttonValue.put(num_7, "7")
        buttonValue.put(num_8, "8")
        buttonValue.put(num_9, "9")
        buttonValue.put(plus, "+")
        buttonValue.put(minus, "-")
        buttonValue.put(division, "/")
        buttonValue.put(multiply, "*")
    }

    private fun setButtonEvent() {
        var iter : Iterator<Button> = buttonValue.keys.iterator()
        while(iter.hasNext()){
            var btn = iter.next()
            btn.setOnClickListener { workOrder.add(buttonValue.get(btn)) }
        }
    }
    
    //Class로 뺄 것
    private fun calculateLogic(){
        /*
        " = " 이 눌린 순간 (setOnclick으로 할당) 계산을 해서 반환
         */
    }
}
