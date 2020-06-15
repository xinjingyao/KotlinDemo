package com.example.kotlindemo

class Person(name: String, age: Int) {
    private var name = name
    private var age = age
    val isBigPerson: Boolean
        get() {
            return age > 20
        }
}