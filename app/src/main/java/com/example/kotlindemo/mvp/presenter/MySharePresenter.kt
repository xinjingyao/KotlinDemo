package com.example.kotlindemo.mvp.presenter

import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.contract.MyShareContract
import com.example.kotlindemo.mvp.model.MyShareModel
import com.example.kotlindemo.mvp.model.entity.MyShareBean

class MySharePresenter : CommonPresenter<MyShareContract.IMyShareView>() {

    private var articleModel: MyShareModel? = null

    init {
        articleModel = MyShareModel()
    }

    fun getArticles(page: Int) {
        val articles = articleModel?.getShareArticles(page, object : ModelListener<MyShareBean> {
            override fun onResponse(
                success: Boolean,
                data: MyShareBean?,
                errorMsg: String?,
                ext: Throwable?
            ) {
                if (success)
                    mView?.showShareArticleList(data)
                else
                    mView?.showError(errorMsg)
            }
        })
        addSubscription(articles)
    }

    fun delete(id: Int) {
        articleModel?.deleteShareArticle(id, object : ModelListener<Any>{
            override fun onResponse(
                success: Boolean,
                data: Any?,
                errorMsg: String?,
                ext: Throwable?
            ) {
                if (success)
                    mView?.deleteSuccess()
                else
                    mView?.showToast(errorMsg)
            }

        })
    }
}