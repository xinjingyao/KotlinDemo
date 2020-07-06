package com.example.kotlindemo.ui.activity

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.FragmentTransaction
import com.blankj.utilcode.util.BusUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.example.kotlindemo.EVENT_SET_USER_INFO
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.mvp.contract.MainContract
import com.example.kotlindemo.mvp.model.entity.UserInfo
import com.example.kotlindemo.mvp.model.entity.UserScoreInfo
import com.example.kotlindemo.mvp.presenter.MainPresenter
import com.example.kotlindemo.ui.fragment.HomeFragment
import com.example.kotlindemo.ui.fragment.SquareFragment
import com.example.kotlindemo.ui.fragment.SystemFragment
import com.example.kotlindemo.ui.fragment.WeChatFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : BaseActivity<MainContract.IMainView, MainPresenter>(), MainContract.IMainView {

    private var tv_username: TextView? = null
    private var tv_id: TextView? = null
    private var tv_level_rank: TextView? = null
    private var iv_rank: ImageView? = null
    private var nav_score: TextView? = null

    var userInfo: UserInfo? = null
    private val FRAGMENT_HOME = 0x01
    private val FRAGMENT_SQUARE = 0x02
    private val FRAGMENT_WECHAT = 0x03
    private val FRAGMENT_SYSTEM = 0x04
    private val FRAGMENT_PROJECT = 0x05
    private var mHomeFragment: HomeFragment? = null
    private var mSquareFragment: SquareFragment? = null
    private var mWeChatFragment: WeChatFragment? = null
    private var mSystemFragment: SystemFragment? = null
    var mIndex: Int = FRAGMENT_HOME

    companion object {
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
        initBottomNav()
        initDrawerLayout()
        initNavView()
        showFragment(mIndex)
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
     * 初始化底部导航栏
     */
    private fun initBottomNav() {
        bottom_nav.run {
            setOnNavigationItemSelectedListener(onBottomNavigationItemSelectedListener)
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
            nav_score =
                MenuItemCompat.getActionView(nav_menu.menu.findItem(R.id.nav_score)) as TextView
        }
        tv_username?.setOnClickListener { LoginActivity.launch(this) }
        iv_rank?.setOnClickListener { showToast("iv_rank") }
    }

    private val onNavigationItemSelectedListener = NavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.nav_score -> {
                showToast("nav_score")
            }
            R.id.nav_collect -> {
                showToast("nav_collect")
            }
            R.id.nav_share -> {
                showToast("nav_share")
            }
            R.id.nav_night_mode -> {
                showToast("nav_night_mode")
            }
            R.id.nav_setting -> {
                showToast("nav_setting")
            }
            R.id.nav_logout -> {
                showToast("nav_logout")
            }
        }
        true
    }

    private val onBottomNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> {
                    showFragment(FRAGMENT_HOME)
                    true
                }
                R.id.action_square -> {
                    showFragment(FRAGMENT_SQUARE)
                    true
                }
                R.id.action_wechat -> {
                    showFragment(FRAGMENT_WECHAT)
                    true
                }
                R.id.action_system -> {
                    showFragment(FRAGMENT_SYSTEM)
                    true
                }
                R.id.action_project -> {
                    true
                }
                else -> false
            }
        }

    private fun showFragment(index: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragment(transaction)
        mIndex = index
        when (index) {
            FRAGMENT_HOME -> {
                toolbar.title = StringUtils.getString(R.string.app_name)
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.getInstance()
                    transaction.add(R.id.fl_container, mHomeFragment!!, "home")
                } else {
                    transaction.show(mHomeFragment!!)
                }
            }
            FRAGMENT_SQUARE -> {
                toolbar.title = StringUtils.getString(R.string.square)
                if (mSquareFragment == null) {
                    mSquareFragment = SquareFragment.getInstance()
                    transaction.add(R.id.fl_container, mSquareFragment!!, "square")
                } else {
                    transaction.show(mSquareFragment!!)
                }
            }
            FRAGMENT_WECHAT -> {
                toolbar.title = StringUtils.getString(R.string.wechat)
                if (mWeChatFragment == null) {
                    mWeChatFragment = WeChatFragment.getInstance()
                    transaction.add(R.id.fl_container, mWeChatFragment!!, "wechat")
                } else {
                    transaction.show(mWeChatFragment!!)
                }
            }
            FRAGMENT_SYSTEM -> {
                toolbar.title = StringUtils.getString(R.string.knowledge_system)
                if (mSystemFragment == null) {
                    mSystemFragment = SystemFragment.getInstance()
                    transaction.add(R.id.fl_container, mSystemFragment!!, "system")
                } else {
                    transaction.show(mSystemFragment!!)
                }
            }
            FRAGMENT_PROJECT -> {
            }
        }
        transaction.commit()
    }


    private fun hideFragment(transaction: FragmentTransaction) {
        mHomeFragment?.let { transaction.hide(it) }
        mSquareFragment?.let { transaction.hide(it) }
        mWeChatFragment?.let { transaction.hide(it) }
        mSystemFragment?.let { transaction.hide(it) }
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
        tv_level_rank?.text = StringUtils.getString(
            R.string.nav_level_rank,
            userScoreInfo?.level,
            userScoreInfo?.rank
        )
        tv_id?.text = StringUtils.getString(R.string.nav_id, userScoreInfo?.userId)
        tv_username?.text = userScoreInfo?.username
        nav_score?.text = userScoreInfo?.coinCount.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        BusUtils.unregister(this)
    }
}