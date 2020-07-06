package com.example.kotlindemo.ui.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.blankj.utilcode.util.StringUtils
import com.example.kotlindemo.R
import com.example.kotlindemo.adaper.WeChatAdapter
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.mvp.contract.EmptyContract
import com.example.kotlindemo.mvp.contract.WeChatContract
import com.example.kotlindemo.mvp.model.entity.WXChapterBean
import com.example.kotlindemo.mvp.presenter.EmptyPresenter
import com.example.kotlindemo.mvp.presenter.WeChatPresenter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_wechat.*

/**
 * 体系
 */
class SystemFragment : BaseFragment<EmptyContract.IEmptyView, EmptyPresenter>(),
    EmptyContract.IEmptyView {

    companion object {
        fun getInstance() = SystemFragment()
    }

    private val titleList = mutableListOf(
        StringUtils.getString(R.string.knowledge_system),
        StringUtils.getString(R.string.navigation)
    )
    private val fragmentList = mutableListOf<Fragment>()
    private val systemAdapter: SystemAdapter by lazy {
        SystemAdapter(childFragmentManager, titleList, fragmentList)
    }

    override fun getLayoutId(): Int = R.layout.fragment_wechat

    override fun createPresenter(): EmptyPresenter = EmptyPresenter()

    override fun initView() {
        // TODO: 2020/7/6 初始化fragmentList
        fragmentList.add(KnowledgeTreeFragment())
        fragmentList.add(Fragment())
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
        viewPager.adapter = systemAdapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
    }

    override fun initData() {
    }

    override fun showCollectResult(success: Boolean) {

    }

    override fun showCancelCollectResult(success: Boolean) {

    }

    class SystemAdapter(
        fragmentManager: FragmentManager,
        private val titleList: MutableList<String>,
        private val fragmentList: MutableList<Fragment>
    ) : FragmentPagerAdapter(fragmentManager) {
        override fun getItem(position: Int): Fragment = fragmentList[position]

        override fun getCount(): Int = fragmentList.size

        override fun getPageTitle(position: Int): CharSequence? = titleList[position]
    }
}