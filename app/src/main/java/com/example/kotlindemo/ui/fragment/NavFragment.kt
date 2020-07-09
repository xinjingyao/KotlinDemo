package com.example.kotlindemo.ui.fragment

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ColorUtils
import com.example.kotlindemo.R
import com.example.kotlindemo.adaper.NavAdapter
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.mvp.contract.NavContract
import com.example.kotlindemo.mvp.model.entity.NavBean
import com.example.kotlindemo.mvp.presenter.NavPresenter
import kotlinx.android.synthetic.main.fragment_nav.*
import q.rorbin.verticaltablayout.VerticalTabLayout
import q.rorbin.verticaltablayout.adapter.SimpleTabAdapter
import q.rorbin.verticaltablayout.widget.ITabView
import q.rorbin.verticaltablayout.widget.TabView

class NavFragment : BaseFragment<NavContract.INavView, NavPresenter>(), NavContract.INavView {

    private var datas = mutableListOf<NavBean>()

    private val navAdapter: NavAdapter by lazy {
        NavAdapter(datas)
    }

    override fun getLayoutId(): Int = R.layout.fragment_nav

    override fun createPresenter(): NavPresenter = NavPresenter()

    override fun initView() {
        initTabLayout()
        initRecyclerView()
    }

    override fun initData() {
        mPresenter?.getNavList()
    }

    private fun initTabLayout() {
        tabLayout.addOnTabSelectedListener(object : VerticalTabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabView?, position: Int) {

            }

            override fun onTabSelected(tab: TabView?, position: Int) {
                recyclerView.smoothScrollToPosition(position)
            }

        })
    }

    private fun initRecyclerView() {
        recyclerView.run {
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            adapter = navAdapter
            setHasFixedSize(true)
        }
    }

    override fun showNavList(navList: MutableList<NavBean>?) {
        if (navList.isNullOrEmpty()) {
            multiple_status_view.showEmpty()
            return
        }
        tabLayout.setTabAdapter(object : SimpleTabAdapter(){
            override fun getCount(): Int = navList.size

            override fun getTitle(position: Int): ITabView.TabTitle {
                return ITabView.TabTitle.Builder()
                    .setContent(navList[position].name)
                    .setTextColor(ColorUtils.getColor(R.color.colorAccent), ColorUtils.getColor(R.color.Grey500))
                    .build()
            }
        })

        navAdapter.run {
            setList(navList)
            loadMoreModule.loadMoreComplete()
            loadMoreModule.loadMoreEnd(true)
            loadMoreModule.isEnableLoadMore = false
        }
        if (navAdapter.data.isEmpty()) {
            multiple_status_view.showEmpty()
        } else {
            multiple_status_view.showContent()
        }
    }
}