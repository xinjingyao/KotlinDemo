package com.example.kotlindemo.mvp.presenter

import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.contract.WeChatContract
import com.example.kotlindemo.mvp.model.WeChatModel
import com.example.kotlindemo.mvp.model.entity.WXChapterBean

class WeChatPresenter : CommonPresenter<WeChatContract.IWeChatView>() {

    private var weChatModel: WeChatModel? = null

    init {
        weChatModel = WeChatModel()
    }

    fun getWXChapters() {
        weChatModel?.getWXChapters(object : ModelListener<MutableList<WXChapterBean>>{
            override fun onResponse(
                success: Boolean,
                data: MutableList<WXChapterBean>?,
                errorMsg: String?,
                ext: Throwable?
            ) {
                if (success)
                    mView?.showWXChapters(data)
                else
                    mView?.showError(errorMsg)
            }

        })
    }
}