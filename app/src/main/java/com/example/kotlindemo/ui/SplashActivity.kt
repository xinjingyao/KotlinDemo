package com.example.kotlindemo.ui

import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.base.BasePresenter
import com.example.kotlindemo.base.EmptyPresenter
import com.example.kotlindemo.base.IView
import kotlinx.android.synthetic.main.activity_splash.*
import kotlin.math.log

class SplashActivity : BaseActivity<IView, EmptyPresenter>() {
    override fun getPresenter(): EmptyPresenter {
        return EmptyPresenter()
    }

    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun initData() {
        // 做一个圆形bitmap
        val bitmap = ImageUtils.getBitmap(R.mipmap.ic_launcher)
        val logo = ImageUtils.toRound(bitmap, true)
        iv_launcher.setImageBitmap(logo)

        // 渐变两秒
        val alphaAnimation = AlphaAnimation(0.5f, 1f)
        alphaAnimation.run {
            duration = 2000
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {

                }

                override fun onAnimationEnd(animation: Animation?) {
                    MainActivity.launch(this@SplashActivity)
                    finish()
                }

                override fun onAnimationStart(animation: Animation?) {

                }
            })
        }
        cl_root.startAnimation(alphaAnimation)
    }


}