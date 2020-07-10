package com.example.kotlindemo.mvp.model

import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.StringUtils
import com.example.kotlindemo.USER_EMAIL
import com.example.kotlindemo.USER_ID
import com.example.kotlindemo.USER_NAME
import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.contract.RegisterContract
import com.example.kotlindemo.mvp.model.entity.UserInfo
import com.mg.axechen.wanandroid.network.RetrofitHelper
import com.mg.axechen.wanandroid.network.response.ResponseTransformer
import io.reactivex.disposables.Disposable
import network.schedules.SchedulerProvider

class RegisterModel : RegisterContract.IRegisterModel {


    override fun register(
        account: String,
        pwd: String,
        pwdAgain: String,
        listener: ModelListener<UserInfo>
    ): Disposable? {

        /**
         * 这里可以抽出来一个局部函数
         */
        fun check(value: String, errorMsg: String): Boolean {
            if (value.isEmpty()) {
                listener.onResponse(false, null, errorMsg, null)
                return true
            }
            return false
        }

        if (check(account, "账号为空")) return null
        if (check(pwd, "密码为空")) return null
        if (check(pwdAgain, "确认密码为空")) return null
        if (!StringUtils.equals(pwd, pwdAgain)) {
            listener.onResponse(false, null, "两次密码输入不一样", null)
            return null
        }
        return RetrofitHelper.getInstance().getRequest()
            ?.userRegister(account, pwd, pwdAgain)
            ?.compose(SchedulerProvider.getInstatnce()?.applySchedulers())
            ?.compose(ResponseTransformer.handleResult())
            ?.subscribe(
                { t ->
                    SPUtils.getInstance().put(USER_ID, t.id)//保存用户的姓名
                    SPUtils.getInstance().put(USER_NAME, t.username!!)
                    //保存用户的邮箱
                    SPUtils.getInstance().put(USER_EMAIL, t.email!!)
                    listener.onResponse(true, t, null, null)
                },
                { throwable -> listener.onResponse(false, null, throwable.message, throwable) }
            )
    }


}