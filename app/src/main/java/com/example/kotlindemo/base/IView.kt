package com.example.kotlindemo.base

interface IView {

    fun showLoading(msg: String?)

    fun hideLoading()

    fun showToast(msg: String?)
}