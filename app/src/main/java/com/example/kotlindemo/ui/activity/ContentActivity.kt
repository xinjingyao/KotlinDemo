package com.example.kotlindemo.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.LinearLayout
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.StringUtils
import com.example.kotlindemo.ARTICLE_ID
import com.example.kotlindemo.ARTICLE_TITLE
import com.example.kotlindemo.ARTICLE_URL
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.base.EmptyPresenter
import com.example.kotlindemo.base.IView
import com.just.agentweb.*
import kotlinx.android.synthetic.main.activity_content.*
import kotlinx.android.synthetic.main.toolbar.*

class ContentActivity : BaseActivity<IView, EmptyPresenter>() {

    private var mAgentWeb: AgentWeb? = null

    private var articleId: Int = -1
    private var articleTitle: String = ""
    private var articleUrl: String = ""

    companion object {
        fun start(context: Context?, id: Int, title: String, url: String, bundle: Bundle? = null) {
            Intent(context, ContentActivity::class.java).run {
                putExtra(ARTICLE_ID, id)
                putExtra(ARTICLE_TITLE, title)
                putExtra(ARTICLE_URL, url)
                context?.startActivity(this, bundle)
            }
        }

        fun start(context: Context?, url: String) {
            start(context, -1, "", url)
        }
    }

    override fun getPresenter(): EmptyPresenter = EmptyPresenter()

    override fun getLayoutId(): Int = R.layout.activity_content

    override fun initView() {
        super.initView()
        intent.extras?.let {
            articleId = it.getInt(ARTICLE_ID, -1)
            articleTitle = it.getString(ARTICLE_TITLE, "")
            articleUrl = it.getString(ARTICLE_URL, "")
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
        val layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
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
        }
    }
}
