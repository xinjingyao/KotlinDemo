package com.example.kotlindemo.ui

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.blankj.utilcode.util.LogUtils
import com.example.kotlindemo.R
import com.example.kotlindemo.UserInfo
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.mvp.LoginContract
import com.example.kotlindemo.mvp.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<LoginContract.ILoginView, LoginPresenter>(), View.OnClickListener , LoginContract.ILoginView{

    companion object {
        fun launch(context: Context) {
            context?.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    override fun getPresenter(): LoginPresenter = LoginPresenter()

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun initData() {
        btn_login.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        mPresenter?.login(et_account.text.toString(), et_pwd.text.toString())
    }

    override fun loginSuccess(userInfo: UserInfo?) {
        LogUtils.d("userInfo=$userInfo")
        showToast("登陆成功了")
    }

}