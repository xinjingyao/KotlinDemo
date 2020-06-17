package com.example.kotlindemo.mvp

import android.text.TextUtils
import com.example.kotlindemo.listener.ModelListener

class LoginModel: LoginContract.ILoginModel {


    override fun login(account: String?, pwd: String?, listener: ModelListener<String>) {
        if (TextUtils.isEmpty(account)) {
            listener.onResponse(false, "", "账号为空", null)
            return
        }
        if (TextUtils.isEmpty(pwd)) {
            listener.onResponse(false, "", "密码为空", null)
            return
        }
        listener.onResponse(true, "登陆成功了", "", null)
    }
}