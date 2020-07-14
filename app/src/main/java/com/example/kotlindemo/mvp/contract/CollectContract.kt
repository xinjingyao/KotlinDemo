package com.example.kotlindemo.mvp.contract

import com.example.kotlindemo.base.IModel
import com.example.kotlindemo.base.IView
import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.model.entity.ArticleResponse
import com.example.kotlindemo.mvp.model.entity.BaseListResponse
import com.example.kotlindemo.mvp.model.entity.CollectArticle
import io.reactivex.disposables.Disposable

/**
 * contract
 */
interface CollectContract {

    interface ICollectView : IView {
        /**
         * 收藏结果
         */
        fun showCollectList(articleResponse: BaseListResponse<CollectArticle>?)

        /**
         * 取消收藏结果
         */
        fun removeCollectResult(success: Boolean)
    }

    interface ICollectModel : IModel {

        /**
         * 收藏文章
         */
        fun getCollectArticles(page: Int, listener: ModelListener<BaseListResponse<CollectArticle>>): Disposable?

        /**
         * 取消收藏
         */
        fun unCollectArticle(id: Int, originId: Int, listener: ModelListener<Any>): Disposable?
    }
}