package com.example.kotlindemo.listener

import java.util.*

interface ModelListener<T> {

    fun onResponse(success: Boolean, data: T, errorMsg: String, ext: Objects?)
}