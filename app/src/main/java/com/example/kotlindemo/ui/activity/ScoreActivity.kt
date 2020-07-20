package com.example.kotlindemo.ui.activity

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.example.kotlindemo.MyApp
import com.example.kotlindemo.R
import com.example.kotlindemo.adaper.ScoreAdapter
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.mvp.contract.ScoreContract
import com.example.kotlindemo.mvp.model.entity.BaseListResponse
import com.example.kotlindemo.mvp.model.entity.ScoreBean
import com.example.kotlindemo.mvp.presenter.ScorePresenter
import com.example.kotlindemo.widget.LineItemDecoration
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_score.*

class ScoreActivity : BaseActivity<ScoreContract.IScoreView, ScorePresenter>(), ScoreContract.IScoreView {

    private var datas = mutableListOf<ScoreBean>()
    private val scoreAdapter:ScoreAdapter by lazy {ScoreAdapter(datas)}

    private var isRefresh = true
    private var curPage = 1

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, ScoreActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_score

    override fun getPresenter(): ScorePresenter = ScorePresenter()

    override fun initView() {
        super.initView()
        initToolbar()
        initSwipeRefreshLayout()
        initRecyclerView()
    }

    override fun initData() {
        mPresenter?.getScoreList(curPage)
    }

    private fun initToolbar() {
        toolbar.run {
            title = StringUtils.getString(R.string.score_total, MyApp.scoreInfo?.coinCount)
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun initSwipeRefreshLayout() {
        swipeRefreshLayout.run {
            setOnRefreshListener {
                // 下来刷新
                isRefresh = true
                curPage = 1
                scoreAdapter.loadMoreModule.isEnableLoadMore = false
                mPresenter?.getScoreList(curPage)
            }
        }
    }

    private fun initRecyclerView() {
        recyclerView.run {
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(LineItemDecoration(context))
            adapter = scoreAdapter
        }
        scoreAdapter.run {
            loadMoreModule.setOnLoadMoreListener {
                // 2020/7/20 加载更多
                isRefresh = false
                swipeRefreshLayout.isRefreshing = false
                mPresenter?.getScoreList(curPage + 1)
            }
        }
    }

    override fun showScoreList(scoreInfos: BaseListResponse<ScoreBean>?) {
        swipeRefreshLayout.isRefreshing = false
        scoreInfos?.datas?.let {
            scoreAdapter.run {
                if (isRefresh) {
                    setList(it)
                } else {
                    addData(it)
                }
                curPage = scoreInfos.curPage
                if (scoreInfos.over) {
                    loadMoreModule.loadMoreEnd(isRefresh)
                } else {
                    loadMoreModule.loadMoreComplete()
                }
            }
        }
        if (scoreAdapter.data.isEmpty()) {
            multiple_status_view?.showEmpty()
        } else {
            multiple_status_view?.showContent()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_help, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_help -> {
                val url = "https://www.wanandroid.com/blog/show/2653"
                ContentActivity.start(this, url)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}