package com.example.kotlindemo.mvp.model.entity

data class UserScoreInfo(
    val coinCount: Int,
    val rank: Int,
    val userId: Int,
    val username: String,
    val level: Int
)