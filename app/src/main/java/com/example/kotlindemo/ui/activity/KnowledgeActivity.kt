package com.example.kotlindemo.ui.activity

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.blankj.utilcode.util.StringUtils
import com.example.kotlindemo.KNOWLEDGE_LIST
import com.example.kotlindemo.R
import com.example.kotlindemo.TITLE
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.mvp.contract.EmptyContract
import com.example.kotlindemo.mvp.model.entity.Knowledge
import com.example.kotlindemo.mvp.model.entity.KnowledgeTree
import com.example.kotlindemo.mvp.presenter.EmptyPresenter
import com.example.kotlindemo.ui.fragment.ArticleFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_knowledge.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import java.io.Serializable

class KnowledgeActivity : BaseActivity<EmptyContract.IEmptyView, EmptyPresenter>() {

    private var toolbarTitle: String? = null
    private var knowledges = mutableListOf<Knowledge>()
    private val knowledgeAdapter: KnowledgeAdapter by lazy {
        KnowledgeAdapter(knowledges, supportFragmentManager)
    }

    companion object {
        fun start(context: Context, title: String, serializable: Serializable) {
            Intent(context, KnowledgeActivity::class.java).run {
                putExtra(TITLE, title)
                putExtra(KNOWLEDGE_LIST, serializable)
                context.startActivity(this)
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_knowledge

    override fun getPresenter(): EmptyPresenter = EmptyPresenter()

    override fun initView() {
        intent.extras?.let { it ->
            toolbarTitle = it.getString(TITLE, StringUtils.getString(R.string.app_name))
            it.getSerializable(KNOWLEDGE_LIST)?.let { it ->
                if (it is KnowledgeTree) {
                    it.children.let {
                        knowledges.addAll(it)
                    }
                }
            }
        }
        toolbar.run {
            title = toolbarTitle
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        tabLayout.run {
            setupWithViewPager(viewPager)
            addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
        }
        viewPager.run {
            adapter = knowledgeAdapter
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
            offscreenPageLimit = 2
        }

    }

    override fun initData() {

    }

    class KnowledgeAdapter(var list: List<Knowledge>, fragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(fragmentManager) {

        private val fragments = mutableListOf<Fragment>()

        init {
            fragments.clear()
            list.forEach {
                fragments.add(ArticleFragment.getInstance(it.id))
            }
        }

        override fun getItem(position: Int): Fragment = fragments[position]

        override fun getCount(): Int = list.size

        override fun getPageTitle(position: Int): CharSequence? = list[position].name

    }
}