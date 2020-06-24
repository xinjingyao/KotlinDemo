package com.example.kotlindemo.mvp.presenter

import com.example.kotlindemo.base.BasePresenter
import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.MainContract
import com.example.kotlindemo.mvp.model.MainModel
import com.example.kotlindemo.mvp.model.entity.UserScoreInfo

class MainPresenter: BasePresenter<MainContract.IMainView>() {

    var mainModel: MainContract.IMainModel? = null
    init {
        mainModel = MainModel()
    }

    fun getUserScoreInfo() {
        val disposable = mainModel?.getUserScoreInfo(object : ModelListener<UserScoreInfo> {
            override fun onResponse(
                success: Boolean,
                data: UserScoreInfo?,
                errorMsg: String?,
                ext: Throwable?
            ) {
                if (success)
                    mView?.showUserScore(data)
                else
                    mView?.showToast(errorMsg)
            }
        })
        addSubscription(disposable)
    }
}