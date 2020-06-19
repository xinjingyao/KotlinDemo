package com.example.kotlindemo.ui

import android.content.Context
import android.content.Intent
import com.blankj.utilcode.util.LogUtils
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.base.EmptyPresenter
import com.example.kotlindemo.base.IView

class MainActivity : BaseActivity<IView, EmptyPresenter>() {

    companion object{
        fun launch(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    override fun getPresenter(): EmptyPresenter = EmptyPresenter()

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initData() {
        LogUtils.d("--initData")
    }
}