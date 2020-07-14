package com.example.kotlindemo.mvp.presenter

import com.example.kotlindemo.base.BasePresenter
import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.contract.CollectContract
import com.example.kotlindemo.mvp.model.CollectModel
import com.example.kotlindemo.mvp.model.entity.ArticleResponse
import com.example.kotlindemo.mvp.model.entity.BaseListResponse
import com.example.kotlindemo.mvp.model.entity.CollectArticle

class CollectPresenter : BasePresenter<CollectContract.ICollectView>() {

    private var collectModel: CollectModel? = null

    init {
        collectModel = CollectModel()
    }

    fun getCollectArticles(page: Int) {
        val collectArticles =
            collectModel?.getCollectArticles(page, object : ModelListener<BaseListResponse<CollectArticle>> {
                override fun onResponse(
                    success: Boolean,
                    data: BaseListResponse<CollectArticle>?,
                    errorMsg: String?,
                    ext: Throwable?
                ) {
                    if (success)
                        mView?.showCollectList(data)
                    else
                        mView?.showError(errorMsg)
                }

            })
        addSubscription(collectArticles)
    }

    fun removeCollectArticle(id: Int, originId:Int) {
        collectModel?.unCollectArticle(id, originId, object : ModelListener<Any> {
            override fun onResponse(
                success: Boolean,
                data: Any?,
                errorMsg: String?,
                ext: Throwable?
            ) {
                if (success)
                    mView?.removeCollectResult(success)
                else
                    mView?.showToast(errorMsg)
            }

        })
    }
}