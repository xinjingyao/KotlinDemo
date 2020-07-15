package com.example.kotlindemo.ui.activity

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.widget.SearchView
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.StringUtils
import com.example.kotlindemo.R
import com.example.kotlindemo.adaper.TagAdapter
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.mvp.contract.SearchContract
import com.example.kotlindemo.mvp.model.entity.HotSearchBean
import com.example.kotlindemo.mvp.presenter.SearchPresenter
import com.example.kotlindemo.widget.SpaceItemDecoration
import com.google.android.flexbox.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.toolbar_search.*

class SearchActivity : BaseActivity<SearchContract.ISearchView, SearchPresenter>(),
    SearchContract.ISearchView {


    private var tags = mutableListOf<HotSearchBean>()
    private val tagAdapter: TagAdapter by lazy { TagAdapter(tags) }

    companion object {
        fun start(context: Context) {
            Intent(context, SearchActivity::class.java).run {
                context.startActivity(this)
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_search

    override fun getPresenter(): SearchPresenter = SearchPresenter()

    override fun initView() {
        toolbar.run {
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        initTagRecyclerView()
    }

    private fun initTagRecyclerView() {
        val flexManager = FlexboxLayoutManager(this)
        flexManager.flexWrap = FlexWrap.WRAP
        flexManager.flexDirection = FlexDirection.ROW
        flexManager.alignItems = AlignItems.STRETCH
        flexManager.justifyContent = JustifyContent.FLEX_START
        rv_tag.run {
            adapter = tagAdapter
            layoutManager = flexManager
            addItemDecoration(SpaceItemDecoration(SizeUtils.dp2px(6f)))
        }
        tagAdapter.setOnItemClickListener { _, _, position ->
            showToast("position=${position}")
        }
    }

    override fun initData() {
        mPresenter?.getHotTags()
    }

    override fun showHotSearchTag(hotTags: MutableList<HotSearchBean>?) {
        hotTags ?: return
        tags = hotTags
        tagAdapter.setList(hotTags)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        var searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        // 默认展开搜索框
        searchView.onActionViewExpanded()
        searchView.queryHint = StringUtils.getString(R.string.search_hint)
        // 隐藏提交按钮
        searchView.isSubmitButtonEnabled = false
        return super.onCreateOptionsMenu(menu)
    }
}