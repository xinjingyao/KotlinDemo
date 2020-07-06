package com.example.kotlindemo.mvp.contract

import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.model.entity.ArticleResponse
import io.reactivex.disposables.Disposable

interface SquareContract {

    interface ISquareView: CommonContract.ICommonView {

        fun showArticleList(articleResponse: ArticleResponse?)
    }

    interface ISquareModel: CommonContract.ICommonModel{


        /**
         * 获取广场文章列表
         */
        fun getSquareList(page: Int, listener: ModelListener<ArticleResponse>): Disposable?
    }
}