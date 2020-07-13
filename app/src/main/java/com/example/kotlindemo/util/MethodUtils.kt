package com.example.kotlindemo.util

import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.StringUtils
import com.example.kotlindemo.USER_ID

object MethodUtils {

    fun isLogin(): Boolean = !StringUtils.isEmpty(SPUtils.getInstance().getString(USER_ID))
}