package com.example.kotlindemo.mvp.presenter

import com.example.kotlindemo.base.BasePresenter
import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.contract.ShareContract
import com.example.kotlindemo.mvp.model.ShareModel

class SharePresenter : BasePresenter<ShareContract.IShareView>() {

    private var shareModel: ShareModel? = null

    init {
        shareModel = ShareModel()
    }

    fun share(title: String, link: String) {
        val articles = shareModel?.shareArticle(title, link, object : ModelListener<Any> {
            override fun onResponse(
                success: Boolean,
                data: Any?,
                errorMsg: String?,
                ext: Throwable?
            ) {
                if (success)
                    mView?.showShareResult(success)
                else
                    mView?.showToast(errorMsg)
            }
        })
        addSubscription(articles)
    }
}