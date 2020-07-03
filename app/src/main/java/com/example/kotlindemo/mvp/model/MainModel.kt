package com.example.kotlindemo.mvp.model

import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.contract.MainContract
import com.example.kotlindemo.mvp.model.entity.UserScoreInfo
import com.mg.axechen.wanandroid.network.RetrofitHelper
import com.mg.axechen.wanandroid.network.response.ResponseTransformer
import io.reactivex.disposables.Disposable
import network.schedules.SchedulerProvider

class MainModel : MainContract.IMainModel {

    override fun getUserScoreInfo(listener: ModelListener<UserScoreInfo>): Disposable? {
       return RetrofitHelper.getInstance().getRequest()
            ?.getUserScoreInfo()
            ?.compose(SchedulerProvider.getInstatnce()?.applySchedulers())
            ?.compose(ResponseTransformer.handleResult())
            ?.subscribe(
                {t -> listener.onResponse(true, t, null, null)},
                {throwable -> listener.onResponse(false, null, throwable.message, throwable)}
            )
    }

}