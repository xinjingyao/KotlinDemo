package com.example.kotlindemo.util

import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.StringUtils
import com.example.kotlindemo.USER_ID

object MethodUtils {

    fun isLogin(): Boolean = SPUtils.getInstance().getInt(USER_ID) != 0
}