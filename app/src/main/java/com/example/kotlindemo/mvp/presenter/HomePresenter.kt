package com.example.kotlindemo.mvp.presenter

import com.example.kotlindemo.base.BasePresenter
import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.HomeContract
import com.example.kotlindemo.mvp.model.HomeModel
import com.example.kotlindemo.mvp.model.entity.ArticleResponse
import com.example.kotlindemo.mvp.model.entity.Banner
import io.reactivex.Observable

class HomePresenter : BasePresenter<HomeContract.IHomeView>() {

    var homeModel: HomeModel? = null

    init {
        homeModel = HomeModel()
    }

    fun getHomeData() {
        homeModel?.getBanner(object : ModelListener<List<Banner>> {
            override fun onResponse(
                success: Boolean,
                data: List<Banner>?,
                errorMsg: String?,
                ext: Throwable?
            ) {
                if (success) {
                    val imageList = ArrayList<String>()
                    val titleList = ArrayList<String>()
                    // 检出图片和title
                    Observable.fromIterable(data)
                        .subscribe {
                            imageList.add(it.imagePath)
                            titleList.add(it.title)
                        }
                    mView?.showBanner(data, imageList, titleList)
                } else
                    mView?.showToast(errorMsg)
            }

        })
        homeModel?.getHomeData(object : ModelListener<ArticleResponse> {
            override fun onResponse(
                success: Boolean,
                data: ArticleResponse?,
                errorMsg: String?,
                ext: Throwable?
            ) {
                if (success)
                    mView?.showArticleList(data)
                else
                    mView?.showToast(errorMsg)
            }

        })
    }

    fun getArticles(page: Int) {
        homeModel?.getArticles(page, object : ModelListener<ArticleResponse> {
            override fun onResponse(
                success: Boolean,
                data: ArticleResponse?,
                errorMsg: String?,
                ext: Throwable?
            ) {
                if (success)
                    mView?.showArticleList(data)
                else
                    mView?.showToast(errorMsg)
            }

        })
    }
}