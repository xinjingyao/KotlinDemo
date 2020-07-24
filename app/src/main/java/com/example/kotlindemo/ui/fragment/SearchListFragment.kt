package com.example.kotlindemo.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.example.kotlindemo.ARTICLE_CID
import com.example.kotlindemo.KEY
import com.example.kotlindemo.R
import com.example.kotlindemo.adaper.HomeAdapter
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.mvp.contract.ArticleContract
import com.example.kotlindemo.mvp.contract.SearchListContract
import com.example.kotlindemo.mvp.model.entity.Article
import com.example.kotlindemo.mvp.model.entity.ArticleResponse
import com.example.kotlindemo.mvp.presenter.ArticlePresenter
import com.example.kotlindemo.mvp.presenter.SearchListPresenter
import com.example.kotlindemo.ui.activity.ContentActivity
import com.example.kotlindemo.ui.activity.LoginActivity
import com.example.kotlindemo.util.MethodUtils
import com.example.kotlindemo.widget.LineItemDecoration
import kotlinx.android.synthetic.main.fragment_list_common.*

class SearchListFragment : BaseFragment<SearchListContract.ISearchListView, SearchListPresenter>(),
    SearchListContract.ISearchListView {

    private val datas = mutableListOf<Article>()
    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(datas)
    }
    private var isRefresh: Boolean = true
    private var key = ""

    companion object {
        fun getInstance(key: String): SearchListFragment {
            val fragment = SearchListFragment()
            val bundle = Bundle()
            bundle.putString(KEY, key)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_list_common

    override fun createPresenter(): SearchListPresenter = SearchListPresenter()

    override fun initView() {
        key = arguments?.getString(KEY) ?: ""
        initSwipeRefreshLayout()
        initRecyclerView()
    }

    override fun initData() {
        swipeRefreshLayout.isRefreshing = true
        mPresenter?.getArticles(0, key)
    }

    /**
     * 初始化下拉刷新
     */
    private fun initSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            //  下拉刷新
            isRefresh = true
            homeAdapter.loadMoreModule.isEnableLoadMore = false
            mPresenter?.getArticles(0, key)
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
                mPresenter?.getArticles(page, key)
            }
            setOnItemClickListener { adapter, view, position ->
                //  点击item
                if (datas.isEmpty()) return@setOnItemClickListener
                val article = datas[position]
                ContentActivity.start(context, article.id, article.title, article.link, article.collect)
            }
            setOnItemChildClickListener { adapter, view, position ->
                //  点击item的某一项
                LogUtils.d("--onclick item child")
                if (datas.isEmpty()) return@setOnItemChildClickListener
                // 没有登录先去登录
                if (!MethodUtils.isLogin()) {
                    activity?.let { LoginActivity.launch(it) }
                    return@setOnItemChildClickListener
                }
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
        }
    }

    override fun showArticleList(articleResponse: ArticleResponse?) {
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

    override fun showError(msg: String?) {
        super.showError(msg)
        swipeRefreshLayout.isRefreshing = false
        multiple_status_view.showError()
    }
}