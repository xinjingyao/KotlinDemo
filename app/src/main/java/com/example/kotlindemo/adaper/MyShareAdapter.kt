package com.example.kotlindemo.adaper

import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.kotlindemo.R
import com.example.kotlindemo.mvp.model.entity.Article
class MyShareAdapter(datas: MutableList<Article>?) :
    BaseQuickAdapter<Article, BaseViewHolder>(R.layout.item_article_swipe, datas), LoadMoreModule {

    init {
        // 点击事件必须放到构造函数中
        // 收藏可以点击
        addChildClickViewIds(R.id.iv_like)
        addChildClickViewIds(R.id.tv_delete)
        addChildClickViewIds(R.id.content)
    }

    override fun convert(holder: BaseViewHolder, item: Article) {

        val authorStr = if (item.author.isNotEmpty()) item.author else item.shareUser

        holder.setText(R.id.tv_title, Html.fromHtml(item.title))
            .setText(R.id.tv_author, authorStr)
            .setText(R.id.tv_date, item.niceDate)
            .setImageResource(
                R.id.iv_like,
                if (item.collect) R.drawable.ic_like else R.drawable.ic_like_not
            )

        // 类别
        val chapterName = when {
            item.superChapterName.isNotEmpty() and item.chapterName.isNotEmpty() -> "${item.superChapterName} / ${item.chapterName}"
            item.superChapterName.isNotEmpty() -> "${item.superChapterName}"
            item.chapterName.isNotEmpty() -> "${item.chapterName}"
            else -> ""
        }
        holder.setText(R.id.tv_chapter, chapterName)

        holder.setGone(R.id.tv_tag_top, item.top != "1")
            .setGone(R.id.tv_tag_fresh, !item.fresh)
            .setGone(R.id.tv_tag_issue, item.tags.size <= 0)
            .setText(R.id.tv_tag_issue, if (item.tags.size > 0) item.tags[0].name else "")

    }
}

