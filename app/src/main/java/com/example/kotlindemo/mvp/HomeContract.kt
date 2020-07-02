package com.example.kotlindemo.mvp

import com.example.kotlindemo.base.IModel
import com.example.kotlindemo.base.IView
import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.model.entity.ArticleResponse
import com.example.kotlindemo.mvp.model.entity.Banner
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import network.response.Response

interface HomeContract {

    interface IHomeView: IView {
        fun showBanner(
            banners: List<Banner>?,
            imageList: ArrayList<String>,
            titleList: ArrayList<String>
        )

        fun showArticleList(articleResponse: ArticleResponse?)
    }

    interface IHomeModel: IModel{

        /**
         * 获取banner数据
         */
        fun getBanner(listener: ModelListener<List<Banner>>): Disposable?

        /**
         * 获取文章列表
         */
        fun getArticles(page: Int, listener: ModelListener<ArticleResponse>): Disposable?

        /**
         * 获取主页数据
         */
        fun getHomeData(listener: ModelListener<ArticleResponse>): Disposable?
    }
}