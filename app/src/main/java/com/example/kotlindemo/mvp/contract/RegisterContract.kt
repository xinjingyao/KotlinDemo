package com.example.kotlindemo.mvp.contract

import com.example.kotlindemo.base.IModel
import com.example.kotlindemo.base.IView
import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.model.entity.UserInfo
import io.reactivex.disposables.Disposable

interface RegisterContract {

    interface IRegisterView: IView {

        fun registerSuccess(userInfo: UserInfo?)
    }

    interface IRegisterModel: IModel {
        fun register(account: String, pwd: String, pwdAgain: String, listener: ModelListener<UserInfo>): Disposable?

    }
}