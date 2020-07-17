package com.example.kotlindemo.mvp.contract

import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.model.entity.ArticleResponse
import io.reactivex.disposables.Disposable

interface SearchListContract {

    interface ISearchListView: CommonContract.ICommonView {

        fun showArticleList(articleResponse: ArticleResponse?)
    }

    interface ISearchListModel: CommonContract.ICommonModel{


        /**
         * 获取文章列表
         */
        fun getArticles(page: Int, key: String, listener: ModelListener<ArticleResponse>): Disposable?
    }
}