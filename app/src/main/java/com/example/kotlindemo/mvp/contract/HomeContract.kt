package com.example.kotlindemo.mvp.contract

import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.model.entity.ArticleResponse
import com.example.kotlindemo.mvp.model.entity.Banner
import io.reactivex.disposables.Disposable

interface HomeContract {

    interface IHomeView: CommonContract.ICommonView {
        fun showBanner(
            banners: List<Banner>?,
            imageList: ArrayList<String>,
            titleList: ArrayList<String>
        )

        fun showArticleList(articleResponse: ArticleResponse?)
    }

    interface IHomeModel: CommonContract.ICommonModel{

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