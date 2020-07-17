package com.example.kotlindemo.mvp.model

import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.contract.SearchListContract
import com.example.kotlindemo.mvp.model.entity.ArticleResponse
import com.mg.axechen.wanandroid.network.RetrofitHelper
import com.mg.axechen.wanandroid.network.response.ResponseTransformer
import io.reactivex.disposables.Disposable
import network.schedules.SchedulerProvider

class SearchListModel : CommonModel(), SearchListContract.ISearchListModel{


    override fun getArticles(page: Int, key: String, listener: ModelListener<ArticleResponse>): Disposable? {
        return RetrofitHelper.getInstance().getRequest()?.searchByKey(page, key)
            ?.compose(SchedulerProvider.getInstatnce()?.applySchedulers())
            ?.compose(ResponseTransformer.handleResult())
            ?.subscribe(
                { t -> listener.onResponse(true, t, null, null) },
                { throwable -> listener.onResponse(false, null, throwable.message, throwable) }
            )
    }
}