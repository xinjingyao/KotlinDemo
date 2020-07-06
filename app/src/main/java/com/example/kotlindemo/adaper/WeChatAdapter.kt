package com.example.kotlindemo.adaper

import android.text.Html
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.kotlindemo.mvp.model.entity.WXChapterBean
import com.example.kotlindemo.ui.fragment.ArticleFragment

class WeChatAdapter(val list: MutableList<WXChapterBean>, fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager) {

    private val fragments = mutableListOf<Fragment>()

    init {
        fragments.clear()
        list.forEach {
            fragments.add(ArticleFragment.getInstance(it.id))
        }
    }

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = list.size

    override fun getPageTitle(position: Int): CharSequence? = Html.fromHtml(list[position].name)


}