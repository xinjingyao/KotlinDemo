package com.example.kotlindemo.mvp.model

import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.contract.CommonContract
import com.mg.axechen.wanandroid.network.RetrofitHelper
import com.mg.axechen.wanandroid.network.response.ResponseTransformer
import io.reactivex.disposables.Disposable
import network.schedules.SchedulerProvider
import org.json.JSONObject

open class CommonModel : CommonContract.ICommonModel {

    override fun collectArticle(id: Int, listener: ModelListener<JSONObject>): Disposable? {
        return RetrofitHelper.getInstance().getRequest()
            ?.collectInArticle(id)
            ?.compose(SchedulerProvider.getInstatnce()?.applySchedulers())
            ?.compose(ResponseTransformer.handleResult())
            ?.subscribe(
                { t -> listener.onResponse(true, t, null, null) },
                { throwable -> listener.onResponse(false, null, throwable.message, throwable) }
            )
    }

    override fun cancelCollectArticle(id: Int, listener: ModelListener<JSONObject>): Disposable? {
        return RetrofitHelper.getInstance().getRequest()
            ?.unCollectArticle(id)
            ?.compose(SchedulerProvider.getInstatnce()?.applySchedulers())
            ?.compose(ResponseTransformer.handleResult())
            ?.subscribe(
                { t -> listener.onResponse(true, t, null, null) },
                { throwable -> listener.onResponse(false, null, throwable.message, throwable) }
            )
    }
}