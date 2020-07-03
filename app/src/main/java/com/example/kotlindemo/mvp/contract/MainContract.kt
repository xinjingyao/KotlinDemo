package com.example.kotlindemo.mvp.contract

import com.example.kotlindemo.base.IModel
import com.example.kotlindemo.base.IView
import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.model.entity.UserScoreInfo
import io.reactivex.disposables.Disposable

interface MainContract {

    interface IMainView: IView {

        fun showUserScore(userScoreInfo: UserScoreInfo?)
    }

    interface IMainModel: IModel {
        fun getUserScoreInfo(listener: ModelListener<UserScoreInfo>): Disposable?
    }
}