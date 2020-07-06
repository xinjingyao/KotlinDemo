package com.example.kotlindemo.mvp.presenter

import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.contract.HomeContract
import com.example.kotlindemo.mvp.contract.SquareContract
import com.example.kotlindemo.mvp.model.HomeModel
import com.example.kotlindemo.mvp.model.SquareModel
import com.example.kotlindemo.mvp.model.entity.ArticleResponse
import com.example.kotlindemo.mvp.model.entity.Banner
import io.reactivex.Observable

class SquarePresenter : CommonPresenter<SquareContract.ISquareView>() {

    private var squareModel: SquareModel? = null

    init {
        squareModel = SquareModel()
    }

    fun getSquareList(page: Int) {
        val articles = squareModel?.getSquareList(page, object : ModelListener<ArticleResponse> {
            override fun onResponse(
                success: Boolean,
                data: ArticleResponse?,
                errorMsg: String?,
                ext: Throwable?
            ) {
                if (success)
                    mView?.showArticleList(data)
                else
                    mView?.showError(errorMsg)
            }
        })
        addSubscription(articles)
    }
}