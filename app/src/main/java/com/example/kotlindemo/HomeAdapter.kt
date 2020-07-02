package com.example.kotlindemo

import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.kotlindemo.mvp.model.entity.Article

class HomeAdapter(datas: MutableList<Article>?) :
    BaseQuickAdapter<Article, BaseViewHolder>(R.layout.item_article, datas) {

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

        holder.setVisible(R.id.tv_tag_top, item.top == "1")
            .setVisible(R.id.tv_tag_fresh, item.fresh)
            .setVisible(R.id.tv_tag_issue, item.tags.size > 0)
            .setText(R.id.tv_tag_issue, if (item.tags.size > 0) item.tags[0].name else "")
    }
}