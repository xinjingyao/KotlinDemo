package com.example.kotlindemo

import android.app.Application
import com.blankj.utilcode.util.Utils
import com.mg.axechen.wanandroid.network.ApiManager

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        ApiManager.getInstance().init()
    }
}