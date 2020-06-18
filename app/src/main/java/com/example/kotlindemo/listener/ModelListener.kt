package com.example.kotlindemo.listener

interface ModelListener<T> {

    fun onResponse(success: Boolean, data: T?, errorMsg: String?, ext: Throwable?)
}