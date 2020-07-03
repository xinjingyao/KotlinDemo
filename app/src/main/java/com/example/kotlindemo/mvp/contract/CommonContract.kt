package com.example.kotlindemo.mvp.contract

import com.example.kotlindemo.base.IModel
import com.example.kotlindemo.base.IView
import com.example.kotlindemo.listener.ModelListener
import io.reactivex.disposables.Disposable
import org.json.JSONObject

/**
 * 通用contract
 */
interface CommonContract {

    interface ICommonView : IView {
        /**
         * 收藏结果
         */
        fun showCollectResult(success: Boolean)

        /**
         * 取消收藏结果
         */
        fun showCancelCollectResult(success: Boolean)
    }

    interface ICommonModel : IModel {

        /**
         * 收藏文章
         */
        fun collectArticle(id: Int, listener: ModelListener<JSONObject>): Disposable?

        /**
         * 取消收藏
         */
        fun cancelCollectArticle(id: Int, listener: ModelListener<JSONObject>): Disposable?
    }
}