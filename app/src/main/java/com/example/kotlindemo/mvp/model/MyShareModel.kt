package com.example.kotlindemo.mvp.model

import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.contract.MyShareContract
import com.example.kotlindemo.mvp.model.entity.MyShareBean
import com.mg.axechen.wanandroid.network.RetrofitHelper
import com.mg.axechen.wanandroid.network.response.ResponseTransformer
import io.reactivex.disposables.Disposable
import network.schedules.SchedulerProvider

class MyShareModel : CommonModel(), MyShareContract.IMyShareModel {


    override fun getShareArticles(page: Int, listener: ModelListener<MyShareBean>): Disposable? {
        return RetrofitHelper.getInstance().getRequest()?.getShareList(page)
            ?.compose(SchedulerProvider.getInstatnce()?.applySchedulers())
            ?.compose(ResponseTransformer.handleResult())
            ?.subscribe(
                { t -> listener.onResponse(true, t, null, null) },
                { throwable -> listener.onResponse(false, null, throwable.message, throwable) }
            )
    }

    override fun deleteShareArticle(id: Int, listener: ModelListener<Any>): Disposable? {
        return RetrofitHelper.getInstance().getRequest()?.deleteShareArticle(id)
            ?.compose(SchedulerProvider.getInstatnce()?.applySchedulers())
            ?.compose(ResponseTransformer.handleResult())
            ?.subscribe(
                { t -> listener.onResponse(true, t, null, null) },
                { throwable -> listener.onResponse(false, null, throwable.message, throwable) }
            )
    }
}