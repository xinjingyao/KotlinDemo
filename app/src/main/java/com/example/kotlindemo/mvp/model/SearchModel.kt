package com.example.kotlindemo.mvp.model

import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.contract.NavContract
import com.example.kotlindemo.mvp.contract.SearchContract
import com.example.kotlindemo.mvp.model.entity.HotSearchBean
import com.example.kotlindemo.mvp.model.entity.NavBean
import com.mg.axechen.wanandroid.network.RetrofitHelper
import com.mg.axechen.wanandroid.network.response.ResponseTransformer
import io.reactivex.disposables.Disposable
import network.schedules.SchedulerProvider

class SearchModel : SearchContract.ISearchModel {

    override fun getHotSearchData(listener: ModelListener<MutableList<HotSearchBean>>): Disposable? {
        return RetrofitHelper.getInstance().getRequest()
            ?.getHotSearchTag()
            ?.compose(SchedulerProvider.getInstatnce()?.applySchedulers())
            ?.compose(ResponseTransformer.handleResult())
            ?.subscribe(
                {t -> listener.onResponse(true, t, null, null)},
                {throwable -> listener.onResponse(false, null, throwable.message, throwable)}
            )
    }
}