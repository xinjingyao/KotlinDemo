package com.example.kotlindemo.mvp.contract

import com.example.kotlindemo.base.IModel
import com.example.kotlindemo.base.IView
import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.model.entity.BaseListResponse
import com.example.kotlindemo.mvp.model.entity.ScoreBean
import com.example.kotlindemo.mvp.model.entity.UserScoreInfo
import io.reactivex.disposables.Disposable

/**
 * Score contract
 */
interface ScoreContract {

    interface IScoreView : IView {
        /**
         * 显示积分列表
         */
        fun showScoreList(scoreInfos: BaseListResponse<ScoreBean>?)

    }

    interface IScoreModel : IModel {

        /**
         * 获取积分列表
         */
        fun getScoreList(page: Int, listener: ModelListener<BaseListResponse<ScoreBean>>): Disposable?

    }
}