package com.example.kotlindemo.ui.fragment

import com.example.kotlindemo.R
import com.example.kotlindemo.adaper.WeChatAdapter
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.mvp.contract.WeChatContract
import com.example.kotlindemo.mvp.model.entity.WXChapterBean
import com.example.kotlindemo.mvp.presenter.WeChatPresenter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_wechat.*

class WeChatFragment : BaseFragment<WeChatContract.IWeChatView, WeChatPresenter>(), WeChatContract.IWeChatView {

    companion object {
        fun getInstance() = WeChatFragment()
    }

    private var datas = mutableListOf<WXChapterBean>()
    private val viewPagerAdapter: WeChatAdapter by lazy {
        WeChatAdapter(datas, childFragmentManager)
    }

    override fun getLayoutId(): Int = R.layout.fragment_wechat

    override fun createPresenter(): WeChatPresenter = WeChatPresenter()

    override fun initView() {
        initTabLayout()
        initViewPager()
    }

    private fun initTabLayout() {
        tabLayout.run {
            setupWithViewPager(viewPager)
            addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
        }
    }

    private fun initViewPager() {
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

    }

    override fun initData() {
        mPresenter?.getWXChapters()
    }

    override fun showWXChapters(wxChapters: MutableList<WXChapterBean>?) {
        wxChapters?.let {
            datas.addAll(it)
            viewPager.run {
                adapter = viewPagerAdapter
                offscreenPageLimit = datas.size
            }
        }
        if (viewPagerAdapter.list.isEmpty()) {
            multiple_status_view.showEmpty()
        } else {
            multiple_status_view.showContent()
        }
    }

    override fun showError(msg: String?) {
        super.showError(msg)
        multiple_status_view.showError()
    }

    override fun showCollectResult(success: Boolean) {

    }

    override fun showCancelCollectResult(success: Boolean) {

    }
}