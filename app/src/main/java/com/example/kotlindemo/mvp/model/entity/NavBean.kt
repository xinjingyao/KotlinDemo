package com.example.kotlindemo.mvp.model.entity

data class NavBean(
    val articles: List<Article>,
    val cid: Int,
    val name: String
)