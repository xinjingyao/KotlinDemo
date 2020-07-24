package com.example.kotlindemo

const val USER_ID = "user_id"
const val USER_NAME = "user_name"
const val USER_EMAIL = "user_email"
const val ARTICLE_ID = "article_id"
const val ARTICLE_TITLE = "article_title"
const val ARTICLE_URL = "article_url"
const val CONTENT_SHARE_TYPE = "text/plain"
const val ARTICLE_CID = "article_cid"
const val ARTICLE_COLLECT = "article_collect"

const val TITLE = "title"
const val KEY = "key"
const val KNOWLEDGE_LIST = "knowledge_list"

const val PAGE_TYPE = "page_type"
object Page {
    const val COLLECT = "collect"
    const val SEARCH_LIST = "search_list"
    const val SHARE_ARTICLE = "share_article"
}

object Event {
    const val SET_USER_INFO = "set_user_info"
    const val COLLECT = "collect"
    const val UNCOLLECT = "uncollect"
}