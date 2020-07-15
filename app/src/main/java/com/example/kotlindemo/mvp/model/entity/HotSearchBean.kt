package com.example.kotlindemo.mvp.model.entity

data class HotSearchBean(
    val id: Int,
    val link: String,
    val name: String,
    val order: Int,
    val visible: Int
)