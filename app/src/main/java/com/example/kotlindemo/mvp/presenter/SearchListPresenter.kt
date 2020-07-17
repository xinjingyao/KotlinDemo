package com.example.kotlindemo.mvp.presenter

import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.contract.ArticleContract
import com.example.kotlindemo.mvp.contract.SearchListContract
import com.example.kotlindemo.mvp.model.ArticleModel
import com.example.kotlindemo.mvp.model.SearchListModel
import com.example.kotlindemo.mvp.model.entity.ArticleResponse

class SearchListPresenter : CommonPresenter<SearchListContract.ISearchListView>() {

    private var searchModel: SearchListModel? = null

    init {
        searchModel = SearchListModel()
    }

    fun getArticles(page: Int, key: String) {
        val articles = searchModel?.getArticles(page, key, object : ModelListener<ArticleResponse> {
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