package com.example.kotlindemo.ui.fragment

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.example.kotlindemo.R
import com.example.kotlindemo.adaper.KnowledgeTreeAdapter
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.mvp.contract.KnowledgeTreeContract
import com.example.kotlindemo.mvp.model.entity.KnowledgeTree
import com.example.kotlindemo.mvp.presenter.KnowledgeTreePresenter
import com.example.kotlindemo.ui.activity.ContentActivity
import com.example.kotlindemo.ui.activity.KnowledgeActivity
import com.example.kotlindemo.widget.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_list_common.*

class KnowledgeTreeFragment : BaseFragment<KnowledgeTreeContract.IKnowledgeTreeView, KnowledgeTreePresenter>(), KnowledgeTreeContract.IKnowledgeTreeView {

    private val datas = mutableListOf<KnowledgeTree>()
    private val mAdapter: KnowledgeTreeAdapter by lazy {
        KnowledgeTreeAdapter(datas)
    }
    private var isRefresh: Boolean = true

    companion object {
        fun getInstance(): KnowledgeTreeFragment = KnowledgeTreeFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_list_common

    override fun createPresenter(): KnowledgeTreePresenter = KnowledgeTreePresenter()

    override fun initView() {
        initSwipeRefreshLayout()
        initRecyclerView()
    }


    override fun initData() {
        swipeRefreshLayout.isRefreshing = true
        mPresenter?.getKnowledgeTreeList()
    }

    /**
     * 初始化下拉刷新
     */
    private fun initSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            //  下拉刷新
            isRefresh = true
            mPresenter?.getKnowledgeTreeList()
        }
    }

    /**
     * 初始化列表
     */
    private fun initRecyclerView() {
        recyclerView.run {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(SpaceItemDecoration(context))
        }

        mAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                //  点击item
                if (datas.isEmpty()) return@setOnItemClickListener
                val knowledgeTree = datas[position].children
                activity?.let { KnowledgeActivity.start(it, datas[position].name, datas[position]) }
            }
        }
    }

    override fun showKnowledgeTree(knowledgeTreeList: List<KnowledgeTree>?) {
        swipeRefreshLayout.isRefreshing = false
        knowledgeTreeList?.let {
            if (isRefresh)
                mAdapter.setList(it)
            else
                mAdapter.addData(it)
            if (it.size < knowledgeTreeList.size) {
                mAdapter.loadMoreModule.loadMoreEnd(isRefresh)
            } else {
                mAdapter.loadMoreModule.loadMoreComplete()
            }
        }
        if (mAdapter.data.isEmpty()) {
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
        LogUtils.d("---git")
    }
}