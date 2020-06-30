package com.example.kotlindemo.mvp.model.entity

data class ArticleResponse(
    val curPage: Int,
    val datas: MutableList<Article>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)