package com.example.kotlindemo.mvp

import com.example.kotlindemo.UserInfo
import com.example.kotlindemo.base.BasePresenter
import com.example.kotlindemo.listener.ModelListener

class LoginPresenter: BasePresenter<LoginContract.ILoginView>() {

    var loginModel: LoginContract.ILoginModel? = null
    init {
        loginModel = LoginModel()
    }

    fun login(account: String, pwd: String) {
        val disposable = loginModel?.login(account, pwd, object : ModelListener<UserInfo> {
            override fun onResponse(
                success: Boolean,
                data: UserInfo?,
                errorMsg: String?,
                ext: Throwable?
            ) {
                if (success)
                    mView?.loginSuccess(data)
                else
                    mView?.showToast(errorMsg)
            }
        })
        addSubscription(disposable)

    }
}