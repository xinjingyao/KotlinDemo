package com.example.kotlindemo.ui.fragment

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.example.kotlindemo.R
import com.example.kotlindemo.adaper.CollectAdapter
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.mvp.contract.CollectContract
import com.example.kotlindemo.mvp.model.entity.BaseListResponse
import com.example.kotlindemo.mvp.model.entity.CollectArticle
import com.example.kotlindemo.mvp.presenter.CollectPresenter
import com.example.kotlindemo.ui.activity.ContentActivity
import com.example.kotlindemo.widget.LineItemDecoration
import kotlinx.android.synthetic.main.fragment_list_common.*

class CollectFragment : BaseFragment<CollectContract.ICollectView, CollectPresenter>(), CollectContract.ICollectView {

    private val datas = mutableListOf<CollectArticle>()
    private val homeAdapter: CollectAdapter by lazy {
        CollectAdapter(datas)
    }
    private var isRefresh: Boolean = true

    companion object {
        fun getInstance(): CollectFragment  = CollectFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_list_common

    override fun createPresenter(): CollectPresenter = CollectPresenter()

    override fun initView() {
        initSwipeRefreshLayout()
        initRecyclerView()
    }

    override fun initData() {
        swipeRefreshLayout.isRefreshing = true
        mPresenter?.getCollectArticles(0)
    }

    /**
     * 初始化下拉刷新
     */
    private fun initSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            //  下拉刷新
            isRefresh = true
            homeAdapter.loadMoreModule.isEnableLoadMore = false
            mPresenter?.getCollectArticles(0)
        }
    }

    /**
     * 初始化列表
     */
    private fun initRecyclerView() {
        recyclerView.run {
            layoutManager = LinearLayoutManager(context)
            adapter = homeAdapter
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(LineItemDecoration(context))
        }

        homeAdapter.run {
            loadMoreModule.setOnLoadMoreListener {
                // 加载更多
                isRefresh = false
                swipeRefreshLayout.isRefreshing = false
                val page = data.size / 20
                mPresenter?.getCollectArticles(page)
            }
            setOnItemClickListener { adapter, view, position ->
                //  点击item
                if (datas.isEmpty()) return@setOnItemClickListener
                val article = datas[position]
                LogUtils.d("item position=${position}")
                ContentActivity.start(context, article.id, article.title, article.link)
            }
            setOnItemChildClickListener { adapter, view, position ->
                //  点击item的某一项
                LogUtils.d("--onclick item child")
                if (datas.isEmpty()) return@setOnItemChildClickListener
                val article = datas[position]
                mPresenter?.removeCollectArticle(article.id, article.originId)
                adapter.removeAt(position)
            }
        }
    }

    override fun showCollectList(articleResponse: BaseListResponse<CollectArticle>?) {
        swipeRefreshLayout.isRefreshing = false
        articleResponse?.datas?.let {
            if (isRefresh)
                homeAdapter.setList(it)
            else
                homeAdapter.addData(it)
            if (it.size < articleResponse.size) {
                homeAdapter.loadMoreModule.loadMoreEnd(isRefresh)
            } else {
                homeAdapter.loadMoreModule.loadMoreComplete()
            }
        }
        if (homeAdapter.data.isEmpty()) {
            multiple_status_view?.showEmpty()
        } else {
            multiple_status_view?.showContent()
        }
    }

    override fun removeCollectResult(success: Boolean) {
        if (success) {
            showToast(StringUtils.getString(R.string.cancel_collect_success))
        }
    }

    override fun showError(msg: String?) {
        super.showError(msg)
        swipeRefreshLayout.isRefreshing = false
        multiple_status_view.showError()
    }
}