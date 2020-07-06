package com.example.kotlindemo.adaper

import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.kotlindemo.R
import com.example.kotlindemo.mvp.model.entity.KnowledgeTree

class KnowledgeTreeAdapter(datas: MutableList<KnowledgeTree>?) :
    BaseQuickAdapter<KnowledgeTree, BaseViewHolder>(R.layout.item_knowledge_system, datas), LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: KnowledgeTree) {
        // jsonToString 把children的name转成json，用空格隔开
        holder.setText(R.id.tv_title, item.name)
            .setText(
                R.id.tv_desc,
                item.children.joinToString(
                    "   ",
                    transform = { child -> Html.fromHtml(child.name) })
            )
    }
}