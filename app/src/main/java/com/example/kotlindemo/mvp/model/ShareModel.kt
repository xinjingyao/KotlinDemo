package com.example.kotlindemo.mvp.model

import com.blankj.utilcode.util.RegexUtils
import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.contract.ShareContract
import com.mg.axechen.wanandroid.network.RetrofitHelper
import com.mg.axechen.wanandroid.network.response.ResponseTransformer
import io.reactivex.disposables.Disposable
import network.schedules.SchedulerProvider

class ShareModel : ShareContract.IShareModel {

    override fun shareArticle(
        title: String,
        link: String,
        listener: ModelListener<Any>
    ): Disposable? {
        if (title.isEmpty()) {
            listener.onResponse(false, null, "title不能为空", null)
            return null
        }
        if (link.isEmpty()) {
            listener.onResponse(false, null, "link不能为空", null)
            return null
        }
        if (title.length > 100) {
            listener.onResponse(false, null, "title不能超过100字", null)
            return null
        }
        if (!RegexUtils.isURL(link)) {
            listener.onResponse(false, null, "文章链接url格式不正确", null)
            return null
        }
        return RetrofitHelper.getInstance().getRequest()?.shareArticle(title, link)
            ?.compose(SchedulerProvider.getInstatnce()?.applySchedulers())
            ?.compose(ResponseTransformer.handleResult())
            ?.subscribe(
                { t -> listener.onResponse(true, t, null, null) },
                { throwable -> listener.onResponse(false, null, throwable.message, throwable) }
            )

    }
}