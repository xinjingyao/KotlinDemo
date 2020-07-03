package com.example.kotlindemo.mvp.contract

import com.example.kotlindemo.mvp.model.entity.UserInfo
import com.example.kotlindemo.base.IModel
import com.example.kotlindemo.base.IView
import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.model.entity.UserScoreInfo
import io.reactivex.disposables.Disposable

interface LoginContract {

    interface ILoginView: IView {

        fun loginSuccess(userInfo: UserInfo?)
    }

    interface ILoginModel: IModel {
        fun login(account: String, pwd: String, listener: ModelListener<UserInfo>): Disposable?

    }
}