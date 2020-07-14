package com.example.kotlindemo.mvp.model

import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.contract.CollectContract
import com.example.kotlindemo.mvp.model.entity.BaseListResponse
import com.example.kotlindemo.mvp.model.entity.CollectArticle
import com.mg.axechen.wanandroid.network.RetrofitHelper
import com.mg.axechen.wanandroid.network.response.ResponseTransformer
import io.reactivex.disposables.Disposable
import network.schedules.SchedulerProvider

class CollectModel : CollectContract.ICollectModel {


    override fun getCollectArticles(page: Int, listener: ModelListener<BaseListResponse<CollectArticle>>): Disposable? {
        return RetrofitHelper.getInstance().getRequest()?.getCollectArticleList(page)
            ?.compose(SchedulerProvider.getInstatnce()?.applySchedulers())
            ?.compose(ResponseTransformer.handleResult())
            ?.subscribe(
                { t -> listener.onResponse(true, t, null, null) },
                { throwable -> listener.onResponse(false, null, throwable.message, throwable) }
            )
    }

    override fun unCollectArticle(
        id: Int,
        originId: Int,
        listener: ModelListener<Any>
    ): Disposable? {
        return RetrofitHelper.getInstance().getRequest()?.removeCollectArticle(id, originId)
            ?.compose(SchedulerProvider.getInstatnce()?.applySchedulers())
            ?.compose(ResponseTransformer.handleResult())
            ?.subscribe(
                { t -> listener.onResponse(true, t, null, null) },
                { throwable -> listener.onResponse(false, null, throwable.message, throwable) }
            )
    }
}