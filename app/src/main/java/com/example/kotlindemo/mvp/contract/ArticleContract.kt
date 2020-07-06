package com.example.kotlindemo.mvp.contract

import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.model.entity.ArticleResponse
import io.reactivex.disposables.Disposable

interface ArticleContract {

    interface IArticleView: CommonContract.ICommonView {

        fun showArticleList(articleResponse: ArticleResponse?)
    }

    interface IArticleModel: CommonContract.ICommonModel{


        /**
         * 获取文章列表
         */
        fun getArticles(page: Int, cid: Int, listener: ModelListener<ArticleResponse>): Disposable?
    }
}