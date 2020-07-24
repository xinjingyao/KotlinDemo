package com.example.kotlindemo.ui.activity

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.example.kotlindemo.Page
import com.example.kotlindemo.R
import com.example.kotlindemo.adaper.MyShareAdapter
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.mvp.contract.MyShareContract
import com.example.kotlindemo.mvp.model.entity.Article
import com.example.kotlindemo.mvp.model.entity.MyShareBean
import com.example.kotlindemo.mvp.presenter.MySharePresenter
import com.example.kotlindemo.widget.LineItemDecoration
import kotlinx.android.synthetic.main.fragment_list_common.*
import kotlinx.android.synthetic.main.toolbar.*

class MyShareActivity : BaseActivity<MyShareContract.IMyShareView, MySharePresenter>(), MyShareContract.IMyShareView {

    private var datas = mutableListOf<Article>()
    private val mAdapter: MyShareAdapter by lazy { MyShareAdapter(datas) }
    private var curPage: Int = 1
    private var isRefresh: Boolean = true

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, MyShareActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_my_share

    override fun getPresenter(): MySharePresenter = MySharePresenter()

    override fun initView() {
        initToolbar()
        initSwipeRefreshLayout()
        initRecyclerView()
    }

    override fun initData() {
        swipeRefreshLayout.isRefreshing = true
        mPresenter?.getArticles(curPage)
    }

    private fun initToolbar() {
        toolbar.run {
            title = StringUtils.getString(R.string.my_share)
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun initSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            // 2020/7/23 下拉刷新
            isRefresh = true
            mAdapter.loadMoreModule.isEnableLoadMore = false
            curPage = 1
            mPresenter?.getArticles(curPage)
        }
    }

    private fun initRecyclerView() {
        recyclerView.run {
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(LineItemDecoration(context))
            adapter = mAdapter
        }
        mAdapter.run {
            loadMoreModule.setOnLoadMoreListener {
                isRefresh = false
                swipeRefreshLayout.isRefreshing = false
                mPresenter?.getArticles(curPage + 1)
            }
            setOnItemClickListener { adapter, view, position ->
                LogUtils.d("--my share item click")
            }
            setOnItemChildClickListener { adapter, view, position ->
                LogUtils.d("--my share child item click")
                when(view.id) {
                    R.id.iv_like -> {
                        val article = datas[position]
                        val collect = article.collect
                        article.collect = !collect
                        setData(position, article)
                        if (collect) {
                            mPresenter?.cancelCollectArticle(article.id)
                        } else {
                            mPresenter?.collectArticle(article.id)
                        }
                    }
                    R.id.tv_delete -> {
                        val data = adapter.data[position]
                        adapter.remove(data)
                        if (data is Article) {
                            mPresenter?.delete(data.id)
                        }
                    }
                    R.id.content -> {
                        val data = adapter.data[position]
                        if (data is Article) {
                            ContentActivity.start(
                                this@MyShareActivity,
                                data.id,
                                data.title,
                                data.link,
                                data.collect
                            )
                        }
                    }
                }
            }
        }
    }

    override fun showShareArticleList(myShareBean: MyShareBean?) {
        swipeRefreshLayout.isRefreshing = false
        myShareBean?.shareArticles?.datas?.let {
            if (isRefresh) {
                mAdapter.setList(it)
            } else {
                mAdapter.addData(it)
            }
            curPage = myShareBean.shareArticles.curPage
            if (myShareBean.shareArticles.over) {
                mAdapter.loadMoreModule.loadMoreEnd(isRefresh)
            } else {
                mAdapter.loadMoreModule.loadMoreComplete()
            }
        }
        if (mAdapter.data.isEmpty()) {
            multiple_status_view.showEmpty()
        } else {
            multiple_status_view.showContent()
        }
    }

    override fun deleteSuccess() {
        showToast(StringUtils.getString(R.string.delete_my_share_article))
    }

    override fun showCollectResult(success: Boolean) {
        if (success) {
            showToast(StringUtils.getString(R.string.collect_success))
        }
    }

    override fun showCancelCollectResult(success: Boolean) {
        if (success) {
            showToast(StringUtils.getString(R.string.cancel_collect_success))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {
                CommonActivity.start(this, Page.SHARE_ARTICLE)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}