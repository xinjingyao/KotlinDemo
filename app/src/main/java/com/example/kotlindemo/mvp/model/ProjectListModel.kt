package com.example.kotlindemo.mvp.model

import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.contract.ArticleContract
import com.example.kotlindemo.mvp.contract.ProjectListContract
import com.example.kotlindemo.mvp.model.entity.ArticleResponse
import com.example.kotlindemo.mvp.model.entity.ProjectListBean
import com.mg.axechen.wanandroid.network.RetrofitHelper
import com.mg.axechen.wanandroid.network.response.ResponseTransformer
import io.reactivex.disposables.Disposable
import network.schedules.SchedulerProvider

class ProjectListModel : CommonModel(), ProjectListContract.IProjectListModel {

    override fun getProjectListByCid(
        page: Int,
        cid: Int,
        listener: ModelListener<ProjectListBean>
    ): Disposable? {
        return RetrofitHelper.getInstance().getRequest()?.getProjectListByCid(page, cid)
            ?.compose(SchedulerProvider.getInstatnce()?.applySchedulers())
            ?.compose(ResponseTransformer.handleResult())
            ?.subscribe(
                { t -> listener.onResponse(true, t, null, null) },
                { throwable -> listener.onResponse(false, null, throwable.message, throwable) }
            )
    }

}