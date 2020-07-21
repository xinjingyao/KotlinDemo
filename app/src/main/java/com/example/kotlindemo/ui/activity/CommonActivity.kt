package com.example.kotlindemo.ui.activity

import android.content.Context
import android.content.Intent
import com.blankj.utilcode.util.StringUtils
import com.example.kotlindemo.KEY
import com.example.kotlindemo.PAGE_TYPE
import com.example.kotlindemo.Page
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.mvp.contract.EmptyContract
import com.example.kotlindemo.mvp.presenter.EmptyPresenter
import com.example.kotlindemo.ui.fragment.CollectFragment
import com.example.kotlindemo.ui.fragment.SearchListFragment
import com.example.kotlindemo.ui.fragment.ShareFragment
import kotlinx.android.synthetic.main.toolbar.*

class CommonActivity : BaseActivity<EmptyContract.IEmptyView, EmptyPresenter>() {

    companion object {
        fun start(context: Context, pageType: String, hotKey: String = "") {
            Intent(context, CommonActivity::class.java).run {
                putExtra(PAGE_TYPE, pageType)
                putExtra(KEY, hotKey)
                context.startActivity(this)
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_common

    override fun getPresenter(): EmptyPresenter = EmptyPresenter()

    @Suppress("IMPLICIT_CAST_TO_ANY")
    override fun initData() {
        val extras = intent.extras
        val pageType = extras?.getString(PAGE_TYPE)

        toolbar.run {
            title = StringUtils.getString(R.string.app_name)
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        val fragment = when (pageType) {
            Page.COLLECT -> {
                toolbar.title = StringUtils.getString(R.string.collect)
                CollectFragment.getInstance()
            }
            Page.SEARCH_LIST -> {
                val hotKey = extras.getString(KEY)
                toolbar.title = hotKey
                hotKey?.let { SearchListFragment.getInstance(it) }
            }
            Page.SHARE_ARTICLE -> {
                toolbar.title = StringUtils.getString(R.string.share_article)
                ShareFragment.newInstance()
            }
            else -> null
        }
        fragment ?: return
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_container, fragment, Page.COLLECT)
            .commit()
    }
}