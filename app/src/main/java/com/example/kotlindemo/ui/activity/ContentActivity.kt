package com.example.kotlindemo.ui.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.blankj.utilcode.util.BusUtils
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.example.kotlindemo.*
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.mvp.contract.EmptyContract
import com.example.kotlindemo.mvp.presenter.EmptyPresenter
import com.google.android.material.appbar.AppBarLayout
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.NestedScrollAgentWebView
import com.just.agentweb.WebChromeClient
import kotlinx.android.synthetic.main.activity_content.*
import kotlinx.android.synthetic.main.toolbar.*

class ContentActivity : BaseActivity<EmptyContract.IEmptyView, EmptyPresenter>(),
    EmptyContract.IEmptyView {

    private var mAgentWeb: AgentWeb? = null

    private var articleId: Int = -1
    private var articleTitle: String = ""
    private var articleUrl: String = ""
    private var isCollect = false

    companion object {
        fun start(
            context: Context?,
            id: Int,
            title: String,
            url: String,
            isCollect: Boolean? = false,
            bundle: Bundle? = null
        ) {
            Intent(context, ContentActivity::class.java).run {
                putExtra(ARTICLE_ID, id)
                putExtra(ARTICLE_TITLE, title)
                putExtra(ARTICLE_URL, url)
                putExtra(ARTICLE_COLLECT, isCollect)
                context?.startActivity(this, bundle)
            }
        }

        fun start(context: Context?, url: String) {
            start(context, -1, "", url)
        }
    }

    override fun getPresenter(): EmptyPresenter =
        EmptyPresenter()

    override fun getLayoutId(): Int = R.layout.activity_content

    override fun initView() {
        super.initView()
        LogUtils.d("--initView")
        intent.extras?.let {
            articleId = it.getInt(ARTICLE_ID, -1)
            articleTitle = it.getString(ARTICLE_TITLE, "")
            articleUrl = it.getString(ARTICLE_URL, "")
            isCollect = it.getBoolean(ARTICLE_COLLECT)
        }
        initToolbar()
        initWebView()
    }

    override fun initData() {

    }

    private fun initToolbar() {
        toolbar.run {
            title = ""
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        tv_title.apply {
            text = StringUtils.getString(R.string.agentweb_loading)
            visibility = View.VISIBLE
        }
    }

    private fun initWebView() {
        val webView = NestedScrollAgentWebView(this)
        // 滑动web折叠toolbar
        val layoutParams = CoordinatorLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        layoutParams.behavior = AppBarLayout.ScrollingViewBehavior()
        mAgentWeb = AgentWeb.with(this)
            //传入AgentWeb 的父控件
            .setAgentWebParent(container, 1, layoutParams)
            // 使用默认进度条
            .useDefaultIndicator(ColorUtils.getColor(R.color.colorAccent), 2)
            .setWebView(webView)
            .setWebChromeClient(mWebChromeClient)
//            .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            //打开其他应用时，弹窗咨询用户是否前往其他应用
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
            .interceptUnkownUrl()
            .createAgentWeb()
            .ready()
            .go(articleUrl)
        mAgentWeb?.webCreator?.webView?.apply {
            overScrollMode = WebView.OVER_SCROLL_NEVER
            settings.domStorageEnabled = true
            settings.javaScriptEnabled = true
            settings.loadsImagesAutomatically = true
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
        }
    }

    private val mWebChromeClient = object : WebChromeClient() {
        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            tv_title.text = title
            tv_title.isSelected = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        LogUtils.d("--onCreateOptionsMenu")
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        val item = menu?.getItem(1)
        item?.title = if (isCollect) StringUtils.getString(R.string.uncollect) else StringUtils.getString(R.string.collect)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {
                Intent().run {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT, StringUtils.getString(
                            R.string.share_article_url,
                            StringUtils.getString(R.string.app_name), articleTitle, articleUrl
                        )
                    )
                    type = CONTENT_SHARE_TYPE
                    startActivity(
                        Intent.createChooser(
                            this,
                            StringUtils.getString(R.string.action_share)
                        )
                    )
                }
            }
            R.id.action_collect -> {
                item.title =
                    if (StringUtils.equals(StringUtils.getString(R.string.collect), item.title)) {
                        mPresenter?.collectArticle(articleId)
                        StringUtils.getString(R.string.uncollect)
                    } else {
                        mPresenter?.cancelCollectArticle(articleId)
                        StringUtils.getString(R.string.collect)
                    }
            }
            R.id.action_browser -> {
                Intent().run {
                    action = "android.intent.action.VIEW"
                    data = Uri.parse(articleUrl)
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showCollectResult(success: Boolean) {
        if (success) {
            showToast(StringUtils.getString(R.string.collect_success))
            // 2020/7/5  刷新主页收藏状态
            BusUtils.post(Event.COLLECT)
        } else {
            showToast(StringUtils.getString(R.string.collect_failed))
        }
    }

    override fun showCancelCollectResult(success: Boolean) {
        if (success) {
            showToast(StringUtils.getString(R.string.cancel_collect_success))
            // 2020/7/5  刷新主页收藏状态
            BusUtils.post(Event.UNCOLLECT)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        mAgentWeb?.handleKeyEvent(keyCode, event)
        return super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        mAgentWeb?.let {
            if (!it.back()) {
                super.onBackPressed()
            }
        }
    }

    override fun onResume() {
        mAgentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onPause() {
        mAgentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mAgentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
    }
}
