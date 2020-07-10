package com.example.kotlindemo.mvp.model.entity

import java.io.Serializable

data class KnowledgeTree(
    val children: List<Knowledge>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
) : Serializable