package com.example.kotlindemo.adaper

import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.kotlindemo.R
import com.example.kotlindemo.mvp.model.entity.Article
import com.example.kotlindemo.util.ImageLoader

class ProjectListAdapter(datas: MutableList<Article>?) :
    BaseQuickAdapter<Article, BaseViewHolder>(R.layout.item_project, datas), LoadMoreModule {

    init {
        // 点击事件必须放到构造函数中
        // 收藏可以点击
        addChildClickViewIds(R.id.iv_like)
    }

    override fun convert(holder: BaseViewHolder, item: Article) {

        val authorStr = if (item.author.isNotEmpty()) item.author else item.shareUser

        holder.setText(R.id.tv_title, Html.fromHtml(item.title))
            .setText(R.id.tv_author, authorStr)
            .setText(R.id.tv_date, item.niceDate)
            .setText(R.id.tv_desc, item.desc)
            .setImageResource(
                R.id.iv_like,
                if (item.collect) R.drawable.ic_like else R.drawable.ic_like_not
            )

        // 显示图片
        ImageLoader.load(context, item.envelopePic, holder.getView(R.id.iv_project))
    }
}