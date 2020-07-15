package com.example.kotlindemo.ui.activity

import android.content.Context
import android.content.Intent
import com.blankj.utilcode.util.StringUtils
import com.example.kotlindemo.PAGE_TYPE
import com.example.kotlindemo.Page
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.mvp.contract.EmptyContract
import com.example.kotlindemo.mvp.presenter.EmptyPresenter
import com.example.kotlindemo.ui.fragment.ArticleFragment
import com.example.kotlindemo.ui.fragment.CollectFragment
import kotlinx.android.synthetic.main.toolbar.*

class CommonActivity: BaseActivity<EmptyContract.IEmptyView, EmptyPresenter>() {

    companion object {
        fun start(context: Context, pageType:String) {
            Intent(context, CommonActivity::class.java).run {
                putExtra(PAGE_TYPE, pageType)
                context.startActivity(this)
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_common

    override fun getPresenter(): EmptyPresenter = EmptyPresenter()

    override fun initData() {
        val extras = intent.extras
        val pageType = extras?.getString(PAGE_TYPE)

        toolbar.run {
            title = StringUtils.getString(R.string.app_name)
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        val fragment = when(pageType) {
            Page.COLLECT -> {
                toolbar.title = StringUtils.getString(R.string.collect)
                CollectFragment.getInstance()
            }
            else -> null
        }
        fragment ?: return
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_container, fragment, Page.COLLECT)
            .commit()
    }
}