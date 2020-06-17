package com.example.kotlindemo.base

interface IPresenter<V: IView> {

    fun attachView(view: V)

    fun detachView()

    fun isDetach(): Boolean
}