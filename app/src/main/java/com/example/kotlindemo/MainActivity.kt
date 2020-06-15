package com.example.kotlindemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
        // kotlin 不需要再findviewbyid了 直接调用即可
        btn_login.setOnClickListener { LoginActivity.launch(this) }
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

        println("--c是否在区间内：${isLetter('c')}")
        println("--3是否不在区间内：${isNotDigit('3')}")
        listTest()
    }

    private fun testMax(a: Int, b: Int): Int {
        return if (a > b) a else b
    }

    /**
     * when带参数的用法
     */
    private fun getColor(color: Color): String {
        return when (color) {
            Color.RED -> "红"
            Color.BLUE -> "蓝"
            Color.GREEN -> "绿"
            Color.YELLOW -> "黄"
        }
    }

    /**
     * when不带参数的用法
     */
    private fun fizzBuzz(i: Int) = when {
        i % 15 == 0 -> "FizzBuzz "
        i % 3 == 0 -> "Fizz "
        i % 5 == 0 -> "Buzz "
        else -> "$i "
    }

    /**
     * in 检查是否在区间内
     */
    private fun isLetter(c: Char) = c in 'a'..'z' || c in 'A'..'Z'

    /**
     * !in 检查是否不在区间内
     */
    private fun isNotDigit(c: Char) = c !in '0'..'9'

    /**
     * list 的创建和 list的新api
     */
    private fun listTest() {
        val list = arrayListOf(1, 3, 5, 14, 7)
        println("--list的值是$list")
        println("--最后一个数字是${list.last()}")
        println("--最大的数是${list.max()}")
    }
}
