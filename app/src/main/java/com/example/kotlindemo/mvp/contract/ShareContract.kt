package com.example.kotlindemo.mvp.contract

import com.example.kotlindemo.base.IModel
import com.example.kotlindemo.base.IView
import com.example.kotlindemo.listener.ModelListener
import io.reactivex.disposables.Disposable

interface ShareContract {

    interface IShareView: IView {

        fun showShareResult(success: Boolean)
    }

    interface IShareModel: IModel{


        /**
         * 获取文章列表
         */
        fun shareArticle(title: String, link: String, listener: ModelListener<Any>): Disposable?
    }
}