package com.example.kotlindemo.mvp.presenter

import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.contract.ArticleContract
import com.example.kotlindemo.mvp.model.ArticleModel
import com.example.kotlindemo.mvp.model.entity.ArticleResponse

class ArticlePresenter : CommonPresenter<ArticleContract.IArticleView>() {

    private var articleModel: ArticleModel? = null

    init {
        articleModel = ArticleModel()
    }

    fun getArticles(page: Int, cid: Int) {
        val articles = articleModel?.getArticles(page, cid, object : ModelListener<ArticleResponse> {
            override fun onResponse(
                success: Boolean,
                data: ArticleResponse?,
                errorMsg: String?,
                ext: Throwable?
            ) {
                if (success)
                    mView?.showArticleList(data)
                else
                    mView?.showError(errorMsg)
            }
        })
        addSubscription(articles)
    }
}