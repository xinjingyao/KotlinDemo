package com.example.kotlindemo.mvp

import android.text.TextUtils
import com.example.kotlindemo.UserInfo
import com.example.kotlindemo.listener.ModelListener
import com.mg.axechen.wanandroid.network.ApiManager
import com.mg.axechen.wanandroid.network.response.ResponseTransformer
import io.reactivex.disposables.Disposable
import network.schedules.SchedulerProvider

class LoginModel : LoginContract.ILoginModel {


    override fun login(account: String, pwd: String, listener: ModelListener<UserInfo>): Disposable? {
        if (TextUtils.isEmpty(account)) {
            listener.onResponse(false, null, "账号为空", null)
            return null
        }
        if (TextUtils.isEmpty(pwd)) {
            listener.onResponse(false, null, "密码为空", null)
            return null
        }
        return ApiManager.getInstance().getRequest()
            ?.userLogin(account, pwd)
            ?.compose(SchedulerProvider.getInstatnce()?.applySchedulers())
            ?.compose(ResponseTransformer.handleResult())
            ?.subscribe(
                { t -> listener.onResponse(true, t, null, null) },
                { throwable -> listener.onResponse(false, null, throwable.message, throwable) }
            )
    }
}