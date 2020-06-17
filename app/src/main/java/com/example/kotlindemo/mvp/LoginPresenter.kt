package com.example.kotlindemo.mvp

import com.example.kotlindemo.base.BasePresenter
import com.example.kotlindemo.listener.ModelListener
import java.util.*

class LoginPresenter: BasePresenter<LoginContract.ILoginView>() {

    var loginModel: LoginContract.ILoginModel? = null
    init {
        loginModel = LoginModel()
    }

    fun login(account: String, pwd: String) {
        loginModel?.login(account, pwd, object : ModelListener<String> {
            override fun onResponse(
                success: Boolean,
                data: String,
                errorMsg: String,
                ext: Objects?
            ) {
                if (success)
                    mView?.loginSuccess()
                else
                    mView?.showToast(errorMsg)
            }
        })
    }
}