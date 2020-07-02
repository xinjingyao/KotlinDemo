package com.example.kotlindemo.mvp.model

import com.blankj.utilcode.util.LogUtils
import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.HomeContract
import com.example.kotlindemo.mvp.model.entity.Article
import com.example.kotlindemo.mvp.model.entity.ArticleResponse
import com.example.kotlindemo.mvp.model.entity.Banner
import com.mg.axechen.wanandroid.network.RetrofitHelper
import com.mg.axechen.wanandroid.network.response.ResponseTransformer
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import network.response.Response
import network.schedules.SchedulerProvider

class HomeModel : HomeContract.IHomeModel {

    private fun getBannerApi(): Observable<Response<List<Banner>>>? {
        return RetrofitHelper.getInstance().getRequest()?.getBanner()
    }

    private fun getArticlesApi(page: Int): Observable<Response<ArticleResponse>>? {
        return RetrofitHelper.getInstance().getRequest()?.getArticles(page)
    }

    private fun getTopArticlesApi(): Observable<Response<MutableList<Article>>>? {
        return RetrofitHelper.getInstance().getRequest()?.getTopArticles()
    }

    override fun getBanner(listener: ModelListener<List<Banner>>): Disposable? {
        return getBannerApi()
            ?.compose(SchedulerProvider.getInstatnce()?.applySchedulers())
            ?.compose(ResponseTransformer.handleResult())
            ?.subscribe(
                { t -> listener.onResponse(true, t, null, null) },
                { throwable -> listener.onResponse(false, null, throwable.message, throwable) }
            )
    }

    override fun getArticles(page: Int, listener: ModelListener<ArticleResponse>): Disposable? {
        return getArticlesApi(page)
            ?.compose(SchedulerProvider.getInstatnce()?.applySchedulers())
            ?.compose(ResponseTransformer.handleResult())
            ?.subscribe(
                { t -> listener.onResponse(true, t, null, null) },
                { throwable -> listener.onResponse(false, null, throwable.message, throwable) }
            )
    }

    override fun getHomeData(listener: ModelListener<ArticleResponse>): Disposable? {
        LogUtils.d("--getHomeData")
        // zip 把两个接口请求的数据拼接成一个
         return Observable.zip(
            getTopArticlesApi(),
            getArticlesApi(0),
            BiFunction<Response<MutableList<Article>>, Response<ArticleResponse>, Response<ArticleResponse>> { t1, t2 ->
                t1.data?.forEach {
                    // 置顶数据手动增加一个tag
                    it.top = "1"
                }
                t2.data?.datas?.addAll(0, t1?.data!!)
                t2
            })
            ?.compose(SchedulerProvider.getInstatnce()?.applySchedulers())
            ?.compose(ResponseTransformer.handleResult())
            ?.subscribe(
                { t -> listener.onResponse(true, t, null, null) },
                { throwable -> listener.onResponse(false, null, throwable.message, throwable) }
            )

    }
}