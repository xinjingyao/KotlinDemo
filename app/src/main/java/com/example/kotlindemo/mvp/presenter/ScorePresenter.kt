package com.example.kotlindemo.mvp.presenter

import com.example.kotlindemo.base.BasePresenter
import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.contract.ScoreContract
import com.example.kotlindemo.mvp.model.ScoreModel
import com.example.kotlindemo.mvp.model.entity.BaseListResponse
import com.example.kotlindemo.mvp.model.entity.ScoreBean

class ScorePresenter : BasePresenter<ScoreContract.IScoreView>() {

    private var mModel: ScoreModel? = null

    init {
        mModel = ScoreModel()
    }

    fun getScoreList(page: Int) {
        val score = mModel?.getScoreList(page, object : ModelListener<BaseListResponse<ScoreBean>> {
            override fun onResponse(
                success: Boolean,
                data: BaseListResponse<ScoreBean>?,
                errorMsg: String?,
                ext: Throwable?
            ) {
                if (success)
                    mView?.showScoreList(data)
                else
                    mView?.showError(errorMsg)
            }
        })
        addSubscription(score)
    }
}