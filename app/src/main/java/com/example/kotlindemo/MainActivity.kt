package com.example.kotlindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
    }

    private fun initData() {
        var name = "yao"
        println("--hello, $name")
        println("--max, ${testMax(7, 9)}")

        var person = Person("yao", 24)
        var person1 = Person("jay", 19)
        println("--person::$person")
        println("--person是否是大人：${person.isBigPerson}")
        println("--person1是否是大人：${person1.isBigPerson}")
        println("--color--${getColor(Color.YELLOW)}")
        // for循环
        for (i in 1..15) {
            println("--${fizzBuzz(i)}")
        }
        println("--------\n")
        // downTo 倒序  step 步长
        for (i in 15 downTo 1 step 2) {
            println("--${fizzBuzz(i)}")
        }
    }

    private fun testMax(a: Int, b: Int): Int {
        return if (a > b) a else b
    }

    private fun getColor(color: Color): String {
        return when (color) {
            Color.RED -> "红"
            Color.BLUE -> "蓝"
            Color.GREEN -> "绿"
            Color.YELLOW -> "黄"
        }
    }

    private fun fizzBuzz(i: Int) = when {
        i % 15 == 0 -> "FizzBuzz "
        i % 3 == 0 -> "Fizz "
        i % 5 == 0 -> "Buzz "
        else -> "$i "
    }
}
