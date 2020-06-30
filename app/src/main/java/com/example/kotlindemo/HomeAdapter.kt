package com.example.kotlindemo

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.kotlindemo.mvp.model.entity.Article

class HomeAdapter(context: Context?, datas: MutableList<Article>) : BaseQuickAdapter<Article, BaseViewHolder>(R.layout.item_blog, datas) {

    override fun convert(holder: BaseViewHolder, item: Article) {

    }
}