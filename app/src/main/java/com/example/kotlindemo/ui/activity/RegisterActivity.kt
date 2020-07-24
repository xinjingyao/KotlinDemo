package com.example.kotlindemo.ui.activity

import android.content.Context
import android.content.Intent
import com.blankj.utilcode.util.BusUtils
import com.blankj.utilcode.util.LogUtils
import com.example.kotlindemo.Event
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.mvp.contract.RegisterContract
import com.example.kotlindemo.mvp.model.entity.UserInfo
import com.example.kotlindemo.mvp.presenter.RegisterPresenter
import kotlinx.android.synthetic.main.activity_login.et_account
import kotlinx.android.synthetic.main.activity_login.et_pwd
import kotlinx.android.synthetic.main.activity_login.iv_back
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity<RegisterContract.IRegisterView, RegisterPresenter>(),
    RegisterContract.IRegisterView {

    companion object {
        fun launch(context: Context) {
            context?.startActivity(Intent(context, RegisterActivity::class.java))
        }
    }

    override fun getPresenter(): RegisterPresenter = RegisterPresenter()

    override fun getLayoutId(): Int = R.layout.activity_register

    override fun initData() {
        iv_back.setOnClickListener { finish() }
        btn_register.setOnClickListener {
            mPresenter?.login(
                et_account.text.toString(),
                et_pwd.text.toString(),
                et_pwd_again.text.toString()
            )
        }
        tv_go_login.setOnClickListener {
            LoginActivity.launch(this)
            finish()
        }
    }

    override fun registerSuccess(userInfo: UserInfo?) {
        LogUtils.d("userInfo=$userInfo")
        showToast("注册成功了")
        finish()
        BusUtils.post(Event.SET_USER_INFO, userInfo)
    }

}