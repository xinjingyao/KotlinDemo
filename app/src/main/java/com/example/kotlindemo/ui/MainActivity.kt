package com.example.kotlindemo.ui

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import com.blankj.utilcode.util.BusUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.example.kotlindemo.EVENT_SET_USER_INFO
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.base.EmptyPresenter
import com.example.kotlindemo.base.IView
import com.example.kotlindemo.mvp.MainContract
import com.example.kotlindemo.mvp.model.entity.UserInfo
import com.example.kotlindemo.mvp.model.entity.UserScoreInfo
import com.example.kotlindemo.mvp.presenter.MainPresenter
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : BaseActivity<MainContract.IMainView, MainPresenter>(), MainContract.IMainView {

    private var tv_username: TextView? = null
    private var tv_id: TextView? = null
    private var tv_level_rank: TextView? = null
    private var iv_rank: ImageView? = null

    var userInfo: UserInfo? = null

    companion object{
        fun launch(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    override fun getPresenter(): MainPresenter = MainPresenter()

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        super.initView()
        BusUtils.register(this)
        initToolbar()
        initDrawerLayout()
        initNavView()
    }

    override fun initData() {
        LogUtils.d("--initData")
        mPresenter?.getUserScoreInfo()
    }

    /**
     * 初始化toolbar
     */
    private fun initToolbar() {
        toolbar.run {
            title = StringUtils.getString(R.string.app_name)
            setSupportActionBar(this)
        }
    }

    /**
     * 初始化drawerLayout
     */
    private fun initDrawerLayout() {
        drawer_layout.run {
            val actionBarDrawerToggle = ActionBarDrawerToggle(
                this@MainActivity,
                this,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )
            addDrawerListener(actionBarDrawerToggle)
            actionBarDrawerToggle.syncState()
        }
    }

    /**
     * 初始化navView
     */
    private fun initNavView() {
        nav_menu.run {
            setNavigationItemSelectedListener(onNavigationItemSelectedListener)
            val headerView = getHeaderView(0)
            iv_rank = headerView.findViewById(R.id.iv_rank)
            tv_username = headerView.findViewById(R.id.tv_username)
            tv_id = headerView.findViewById(R.id.tv_id)
            tv_level_rank = headerView.findViewById(R.id.tv_level_rank)
        }
        tv_username?.setOnClickListener { LoginActivity.launch(this) }
        iv_rank?.setOnClickListener { showToast("iv_rank") }
    }

    private val onNavigationItemSelectedListener = NavigationView.OnNavigationItemSelectedListener {
        when(it.itemId) {
            R.id.nav_score -> { showToast("nav_score") }
            R.id.nav_collect -> { showToast("nav_collect") }
            R.id.nav_share -> { showToast("nav_share")}
            R.id.nav_night_mode -> {showToast("nav_night_mode") }
            R.id.nav_setting -> { showToast("nav_setting")}
            R.id.nav_logout -> { showToast("nav_logout")}
        }
        true
    }

    @BusUtils.Bus(tag = EVENT_SET_USER_INFO, threadMode = BusUtils.ThreadMode.MAIN)
    fun receiveUserInfo(userInfo: UserInfo) {
        LogUtils.d("--receiveUserInfo")
        this.userInfo = userInfo
        tv_username?.text = userInfo.username
        tv_id?.text = StringUtils.getString(R.string.nav_id, userInfo.id)
        mPresenter?.getUserScoreInfo()
    }

    override fun showUserScore(userScoreInfo: UserScoreInfo?) {
        tv_level_rank?.text = StringUtils.getString(R.string.nav_level_rank, userScoreInfo?.level, userScoreInfo?.rank)
        tv_id?.text = StringUtils.getString(R.string.nav_id, userScoreInfo?.userId)
    }

    override fun onDestroy() {
        super.onDestroy()
        BusUtils.unregister(this)
    }
}