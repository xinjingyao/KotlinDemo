package com.example.kotlindemo.mvp.contract

import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.model.entity.MyShareBean
import io.reactivex.disposables.Disposable

interface MyShareContract {

    interface IMyShareView: CommonContract.ICommonView {

        fun showShareArticleList(myShareBean: MyShareBean?)

        fun deleteSuccess()
    }

    interface IMyShareModel: CommonContract.ICommonModel{


        /**
         * 获取分享文章列表
         */
        fun getShareArticles(page: Int, listener: ModelListener<MyShareBean>): Disposable?

        /**
         * 获取分享文章列表
         */
        fun deleteShareArticle(id: Int, listener: ModelListener<Any>): Disposable?
    }
}