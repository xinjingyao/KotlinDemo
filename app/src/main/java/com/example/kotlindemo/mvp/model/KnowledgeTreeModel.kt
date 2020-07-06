package com.example.kotlindemo.mvp.model

import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.contract.KnowledgeTreeContract
import com.example.kotlindemo.mvp.model.entity.KnowledgeTree
import com.mg.axechen.wanandroid.network.RetrofitHelper
import com.mg.axechen.wanandroid.network.response.ResponseTransformer
import io.reactivex.disposables.Disposable
import network.schedules.SchedulerProvider

class KnowledgeTreeModel : CommonModel(), KnowledgeTreeContract.IKnowledgeTreeModel {

    override fun getKnowledgeTreeList(listener: ModelListener<List<KnowledgeTree>>): Disposable? {
        return RetrofitHelper.getInstance().getRequest()?.getKnowledgeTreeList()
            ?.compose(SchedulerProvider.getInstatnce()?.applySchedulers())
            ?.compose(ResponseTransformer.handleResult())
            ?.subscribe(
                { t -> listener.onResponse(true, t, null, null) },
                { throwable -> listener.onResponse(false, null, throwable.message, throwable) }
            )
    }

}