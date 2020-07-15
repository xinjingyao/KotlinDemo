package com.example.kotlindemo.adaper

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.kotlindemo.R
import com.example.kotlindemo.mvp.model.entity.HotSearchBean
import com.example.kotlindemo.util.MethodUtils

class TagAdapter(datas: MutableList<HotSearchBean>?) :
    BaseQuickAdapter<HotSearchBean, BaseViewHolder>(R.layout.item_tag, datas), LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: HotSearchBean) {
        holder.setText(R.id.tv_tag, item.name)
            .setTextColor(R.id.tv_tag, MethodUtils.randomColor())
    }
}