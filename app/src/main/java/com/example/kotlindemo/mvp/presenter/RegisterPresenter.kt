package com.example.kotlindemo.mvp.presenter

import com.example.kotlindemo.mvp.model.entity.UserInfo
import com.example.kotlindemo.base.BasePresenter
import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.contract.LoginContract
import com.example.kotlindemo.mvp.contract.RegisterContract
import com.example.kotlindemo.mvp.model.LoginModel
import com.example.kotlindemo.mvp.model.RegisterModel

class RegisterPresenter: BasePresenter<RegisterContract.IRegisterView>() {

    var registerModel: RegisterModel? = null
    init {
        registerModel = RegisterModel()
    }

    fun login(account: String, pwd: String, pwdAgain: String) {
        val disposable = registerModel?.register(account, pwd, pwdAgain, object : ModelListener<UserInfo> {
            override fun onResponse(
                success: Boolean,
                data: UserInfo?,
                errorMsg: String?,
                ext: Throwable?
            ) {
                if (success)
                    mView?.registerSuccess(data)
                else
                    mView?.showToast(errorMsg)
            }
        })
        addSubscription(disposable)
    }

}