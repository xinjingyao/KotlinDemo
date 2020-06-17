package com.example.kotlindemo.mvp

import com.example.kotlindemo.base.IModel
import com.example.kotlindemo.base.IView
import com.example.kotlindemo.listener.ModelListener

interface LoginContract {

    interface ILoginView: IView {

        fun loginSuccess()
    }

    interface ILoginModel: IModel {
        fun login(account: String?, pwd: String?, listener: ModelListener<String>)
    }
}