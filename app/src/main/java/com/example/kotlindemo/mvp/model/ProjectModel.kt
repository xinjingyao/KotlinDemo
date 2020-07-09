package com.example.kotlindemo.mvp.model

import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.contract.ProjectContract
import com.example.kotlindemo.mvp.model.entity.ProjectBean
import com.mg.axechen.wanandroid.network.RetrofitHelper
import com.mg.axechen.wanandroid.network.response.ResponseTransformer
import io.reactivex.disposables.Disposable
import network.schedules.SchedulerProvider

class ProjectModel : ProjectContract.IProjectModel {


    override fun getProjectTree(listener: ModelListener<List<ProjectBean>>): Disposable? {
        return RetrofitHelper.getInstance().getRequest()?.getProjectTree()
            ?.compose(SchedulerProvider.getInstatnce()?.applySchedulers())
            ?.compose(ResponseTransformer.handleResult())
            ?.subscribe(
                { t -> listener.onResponse(true, t, null, null) },
                { throwable -> listener.onResponse(false, null, throwable.message, throwable) }
            )
    }
}