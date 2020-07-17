package com.example.kotlindemo.ui.activity

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.widget.SearchView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.StringUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.kotlindemo.R
import com.example.kotlindemo.adaper.TagAdapter
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.mvp.contract.SearchContract
import com.example.kotlindemo.mvp.model.entity.HotSearchBean
import com.example.kotlindemo.mvp.model.entity.SearchHistoryBean
import com.example.kotlindemo.mvp.presenter.SearchPresenter
import com.example.kotlindemo.widget.SpaceItemDecoration
import com.google.android.flexbox.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.toolbar_search.*

class SearchActivity : BaseActivity<SearchContract.ISearchView, SearchPresenter>(),
    SearchContract.ISearchView {


    private var tags = mutableListOf<HotSearchBean>()
    private var historys = mutableListOf<SearchHistoryBean>()
    private val tagAdapter: TagAdapter by lazy { TagAdapter(tags) }
    private val historyAdapter: SearchAdapter by lazy { SearchAdapter(historys) }

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
        initHistoryRecyclerView()
    }

    override fun initData() {
        mPresenter?.getHotTags()
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

    private fun initHistoryRecyclerView() {
        rv_history.run {
            adapter = historyAdapter
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(SpaceItemDecoration(SizeUtils.dp2px(2f)))
        }
        historyAdapter.setOnItemClickListener { adapter, view, position ->
            var search = adapter.data[position] as SearchHistoryBean
            goSearchResultPage(search.key)
        }
        historyAdapter.setOnItemChildClickListener { adapter, view, position ->
            var search = adapter.data[position] as SearchHistoryBean
            when(view.id) {
                R.id.iv_close -> {
                    mPresenter?.deleteById(search.id)
                    historyAdapter.remove(search)
                }
            }
        }
        tv_clear.setOnClickListener {
            historys.clear()
            historyAdapter.setList(historys)
            mPresenter?.clearAll()
        }
    }

    override fun onResume() {
        super.onResume()
        mPresenter?.queryHistory()
    }

    override fun showHotSearchTag(hotTags: MutableList<HotSearchBean>?) {
        hotTags ?: return
        tags = hotTags
        tagAdapter.setList(hotTags)
    }

    override fun showHistory(historys: MutableList<SearchHistoryBean>?) {
        historys ?: return
        this.historys = historys
        historyAdapter.setList(historys)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        var searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        // 默认展开搜索框
        searchView.onActionViewExpanded()
        searchView.queryHint = StringUtils.getString(R.string.search_hint)
        // 隐藏提交按钮
        searchView.isSubmitButtonEnabled = false
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                goSearchResultPage(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    fun goSearchResultPage(key: String?) {
        key?.let { mPresenter?.add(it) }
        showToast(key)
    }

    class SearchAdapter(datas: MutableList<SearchHistoryBean>) :
        BaseQuickAdapter<SearchHistoryBean, BaseViewHolder>(R.layout.item_history, datas) {
        init {
            addChildClickViewIds(R.id.iv_close)
        }

        override fun convert(holder: BaseViewHolder, item: SearchHistoryBean) {
            holder.setText(R.id.tv_key, item.key)
        }

    }
}