package com.example.kotlindemo.mvp.contract

import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.model.entity.ArticleResponse
import com.example.kotlindemo.mvp.model.entity.WXChapterBean
import io.reactivex.disposables.Disposable

interface WeChatContract {

    interface IWeChatView: CommonContract.ICommonView {

        fun showWXChapters(wxChapters: MutableList<WXChapterBean>?)
    }

    interface IWeChatModel: CommonContract.ICommonModel{


        /**
         * 获取广场文章列表
         */
        fun getWXChapters(listener: ModelListener<MutableList<WXChapterBean>>): Disposable?
    }
}