package com.example.kotlindemo.mvp

import com.example.kotlindemo.UserInfo
import com.example.kotlindemo.listener.ModelListener
import com.mg.axechen.wanandroid.network.RetrofitHelper
import com.mg.axechen.wanandroid.network.response.ResponseTransformer
import io.reactivex.disposables.Disposable
import network.schedules.SchedulerProvider

class LoginModel : LoginContract.ILoginModel {


    override fun login(
        account: String,
        pwd: String,
        listener: ModelListener<UserInfo>
    ): Disposable? {

        /**
         * 这里可以抽出来一个局部函数
         */
        fun check(value: String, errorMsg: String) {
            if (value.isEmpty()) {
                listener.onResponse(false, null, errorMsg, null)
                return
            }
        }

        check(account, "账号为空")
        check(pwd, "密码为空")
        return RetrofitHelper.getInstance().getRequest()
            ?.userLogin(account, pwd)
            ?.compose(SchedulerProvider.getInstatnce()?.applySchedulers())
            ?.compose(ResponseTransformer.handleResult())
            ?.subscribe(
                { t -> listener.onResponse(true, t, null, null) },
                { throwable -> listener.onResponse(false, null, throwable.message, throwable) }
            )
    }
}