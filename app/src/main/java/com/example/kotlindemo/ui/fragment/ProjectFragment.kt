package com.example.kotlindemo.ui.fragment

import android.text.Html
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.blankj.utilcode.util.LogUtils
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.mvp.contract.ProjectContract
import com.example.kotlindemo.mvp.model.entity.ProjectBean
import com.example.kotlindemo.mvp.presenter.ProjectPresenter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_wechat.*

class ProjectFragment: BaseFragment<ProjectContract.IProjectView, ProjectPresenter>(), ProjectContract.IProjectView {

    private var datas = mutableListOf<ProjectBean>()

    private val projectAdapter: ProjectAdapter by lazy { ProjectAdapter(datas, childFragmentManager) }

    companion object {
        fun getInstance() = ProjectFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_wechat

    override fun createPresenter(): ProjectPresenter = ProjectPresenter()

    override fun initView() {
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
    }

    override fun initData() {
        mPresenter?.getProjectTree()
    }

    override fun showProjectTree(projectTree: List<ProjectBean>?) {
        LogUtils.d("--showProjectTree=${projectTree?.get(0)?.name}")
        if (projectTree.isNullOrEmpty()) {
            multiple_status_view.showEmpty()
        } else {
            multiple_status_view.showContent()
            datas.addAll(projectTree)
            viewPager.run {
                adapter = projectAdapter
                offscreenPageLimit = 2
            }
        }
    }


    class ProjectAdapter(var projectTree: MutableList<ProjectBean>, fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager) {

        private val fragments = mutableListOf<Fragment>()

        init {
            fragments.clear()
            projectTree.forEach {
                fragments.add(ProjectListFragment.getInstance(it.id))
            }
        }

        override fun getItem(position: Int): Fragment = fragments[position]

        override fun getCount(): Int = projectTree.size

        override fun getPageTitle(position: Int): CharSequence? = Html.fromHtml(projectTree[position].name)
    }
}