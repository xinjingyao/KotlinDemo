package com.example.kotlindemo.mvp.presenter

import com.example.kotlindemo.base.BasePresenter
import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.contract.CommonContract
import com.example.kotlindemo.mvp.model.CommonModel
import org.json.JSONObject

/**
 * 通用presenter
 */
open class CommonPresenter<V : CommonContract.ICommonView>: BasePresenter<V>() {

    protected var commonModel: CommonModel? = null

    init {
        commonModel = CommonModel()
    }

    fun collectArticle(id: Int) {
        commonModel?.collectArticle(id, object: ModelListener<JSONObject> {
            override fun onResponse(
                success: Boolean,
                data: JSONObject?,
                errorMsg: String?,
                ext: Throwable?
            ) {
                if (success)
                    mView?.showCollectResult(true)
                else {
                    mView?.showCollectResult(false)
                }
            }
        })
    }

    fun cancelCollectArticle(id: Int) {
        commonModel?.cancelCollectArticle(id, object: ModelListener<JSONObject> {
            override fun onResponse(
                success: Boolean,
                data: JSONObject?,
                errorMsg: String?,
                ext: Throwable?
            ) {
                if (success)
                    mView?.showCancelCollectResult(true)
                else {
                    mView?.showCancelCollectResult(false)
                }
            }
        })
    }
}