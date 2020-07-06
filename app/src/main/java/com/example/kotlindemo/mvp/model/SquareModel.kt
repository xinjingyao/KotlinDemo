package com.example.kotlindemo.mvp.model

import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.contract.SquareContract
import com.example.kotlindemo.mvp.model.entity.ArticleResponse
import com.mg.axechen.wanandroid.network.RetrofitHelper
import com.mg.axechen.wanandroid.network.response.ResponseTransformer
import io.reactivex.disposables.Disposable
import network.schedules.SchedulerProvider

class SquareModel : CommonModel(), SquareContract.ISquareModel {

    override fun getSquareList(page: Int, listener: ModelListener<ArticleResponse>): Disposable? {
        return RetrofitHelper.getInstance().getRequest()?.getSquareList(page)
            ?.compose(SchedulerProvider.getInstatnce()?.applySchedulers())
            ?.compose(ResponseTransformer.handleResult())
            ?.subscribe(
                { t -> listener.onResponse(true, t, null, null) },
                { throwable -> listener.onResponse(false, null, throwable.message, throwable) }
            )
    }

}