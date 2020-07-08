package com.example.kotlindemo.mvp.model

import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.contract.NavContract
import com.example.kotlindemo.mvp.model.entity.NavBean
import com.mg.axechen.wanandroid.network.RetrofitHelper
import com.mg.axechen.wanandroid.network.response.ResponseTransformer
import io.reactivex.disposables.Disposable
import network.schedules.SchedulerProvider

class NavModel : NavContract.INavModel {

    override fun getNavList(listener: ModelListener<MutableList<NavBean>>): Disposable? {
        return RetrofitHelper.getInstance().getRequest()
            ?.getNaviJson()
            ?.compose(SchedulerProvider.getInstatnce()?.applySchedulers())
            ?.compose(ResponseTransformer.handleResult())
            ?.subscribe(
                {t -> listener.onResponse(true, t, null, null)},
                {throwable -> listener.onResponse(false, null, throwable.message, throwable)}
            )
    }
}