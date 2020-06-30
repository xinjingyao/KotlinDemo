package com.example.kotlindemo.ui.fragment

import android.widget.ImageView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import cn.bingoogolapple.bgabanner.BGABanner
import com.example.kotlindemo.HomeAdapter
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.base.EmptyPresenter
import com.example.kotlindemo.base.IView
import com.example.kotlindemo.mvp.model.entity.Article
import com.example.kotlindemo.widget.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_list_common.*

class HomeFragment : BaseFragment<IView, EmptyPresenter>() {

    private var bannerView: BGABanner? = null

    private val datas = mutableListOf<Article>()
    private val homeAdapter: HomeAdapter by lazy { HomeAdapter(context, datas) }
    private var bannerAdapter: BGABanner.Adapter<ImageView, String>? = null

    override fun getLayoutId(): Int = R.layout.fragment_list_common

    override fun createPresenter(): EmptyPresenter = EmptyPresenter()

    override fun initView() {
        initSwipeRefreshLayout()
        initBanner()
        initRecyclerView()
    }


    override fun initData() {

    }

    /**
     * 初始化下拉刷新
     */
    private fun initSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            // TODO: 2020/6/30 下拉刷新
        }

    }

    private fun initBanner() {
        bannerView = layoutInflater.inflate(R.layout.item_home_banner, null) as BGABanner?
        bannerAdapter = BGABanner.Adapter{ bgaBanner: BGABanner, imageView: ImageView, feedImageUrl: String?, position: Int ->
            // TODO: 2020/6/30 加载banner
        }
        bannerView?.run {
            setDelegate { banner, itemView, model, position ->
                // TODO: 2020/6/30 banner的监听
            }
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
            addItemDecoration(SpaceItemDecoration(context))
        }

        homeAdapter.run {
            bannerView?.let { addHeaderView(it) }
            loadMoreModule.setOnLoadMoreListener {
                // TODO: 2020/6/30 加载更多
            }
            setOnItemClickListener { adapter, view, position ->
                // TODO: 2020/6/30 点击item
            }
            setOnItemChildClickListener{adapter, view, position ->
                // TODO: 2020/6/30 点击item的某一项
            }
        }

    }


}