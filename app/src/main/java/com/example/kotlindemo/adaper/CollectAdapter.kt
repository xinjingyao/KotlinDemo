package com.example.kotlindemo.adaper

import android.text.Html
import com.blankj.utilcode.util.StringUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.kotlindemo.R
import com.example.kotlindemo.mvp.model.entity.CollectArticle

class CollectAdapter(datas: MutableList<CollectArticle>?) :
    BaseQuickAdapter<CollectArticle, BaseViewHolder>(R.layout.item_article, datas), LoadMoreModule {

    init {
        // 收藏可以点击
        addChildClickViewIds(R.id.iv_like)
    }

    override fun convert(holder: BaseViewHolder, item: CollectArticle) {

        val authorStr = if (item.author.isNotEmpty()) item.author else StringUtils.getString(R.string.anonymous)

        holder.setText(R.id.tv_title, Html.fromHtml(item.title))
            .setText(R.id.tv_author, authorStr)
            .setText(R.id.tv_date, item.niceDate)
            .setImageResource(R.id.iv_like, R.drawable.ic_like)

        // 类别
        holder.setText(R.id.tv_chapter, item.chapterName)
        holder.setGone(R.id.tv_tag_top, true)
            .setGone(R.id.tv_tag_issue, true)
            .setGone(R.id.tv_tag_fresh, true)


    }
}