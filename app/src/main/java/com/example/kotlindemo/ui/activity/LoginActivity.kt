package com.example.kotlindemo.ui.activity

import android.content.Context
import android.content.Intent
import com.blankj.utilcode.util.BusUtils
import com.blankj.utilcode.util.LogUtils
import com.example.kotlindemo.EVENT_SET_USER_INFO
import com.example.kotlindemo.R
import com.example.kotlindemo.mvp.model.entity.UserInfo
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.mvp.LoginContract
import com.example.kotlindemo.mvp.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<LoginContract.ILoginView, LoginPresenter>(), LoginContract.ILoginView{

    companion object {
        fun launch(context: Context) {
            context?.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    override fun getPresenter(): LoginPresenter =
        LoginPresenter()

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun initData() {
        iv_back.setOnClickListener { finish() }
        btn_login.setOnClickListener{mPresenter?.login(et_account.text.toString(), et_pwd.text.toString())}
        tv_register.setOnClickListener { showToast("register") }
    }


    override fun loginSuccess(userInfo: UserInfo?) {
        LogUtils.d("userInfo=$userInfo")
        showToast("登陆成功了")
        BusUtils.post(EVENT_SET_USER_INFO, userInfo)
    }

}