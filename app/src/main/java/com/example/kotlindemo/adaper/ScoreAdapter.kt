package com.example.kotlindemo.adaper

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.kotlindemo.R
import com.example.kotlindemo.mvp.model.entity.ScoreBean

class ScoreAdapter(datas: MutableList<ScoreBean>?) :
    BaseQuickAdapter<ScoreBean, BaseViewHolder>(R.layout.item_score, datas), LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: ScoreBean) {
        holder.setText(R.id.tv_title, item.reason)
            .setText(R.id.tv_desc, item.desc)
            .setText(R.id.tv_score, "+${item.coinCount}")
    }
}