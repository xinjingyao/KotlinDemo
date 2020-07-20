package com.example.kotlindemo.mvp.model

import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.contract.ScoreContract
import com.example.kotlindemo.mvp.model.entity.BaseListResponse
import com.example.kotlindemo.mvp.model.entity.ScoreBean
import com.example.kotlindemo.mvp.model.entity.UserScoreInfo
import com.mg.axechen.wanandroid.network.RetrofitHelper
import com.mg.axechen.wanandroid.network.response.ResponseTransformer
import io.reactivex.disposables.Disposable
import network.schedules.SchedulerProvider

class ScoreModel : ScoreContract.IScoreModel {

    override fun getScoreList(
        page: Int,
        listener: ModelListener<BaseListResponse<ScoreBean>>
    ): Disposable? {
        return RetrofitHelper.getInstance().getRequest()?.getUserScoreList(page)
            ?.compose(SchedulerProvider.getInstatnce()?.applySchedulers())
            ?.compose(ResponseTransformer.handleResult())
            ?.subscribe(
                { t -> listener.onResponse(true, t, null, null) },
                { throwable -> listener.onResponse(false, null, throwable.message, throwable) }
            )
    }
}