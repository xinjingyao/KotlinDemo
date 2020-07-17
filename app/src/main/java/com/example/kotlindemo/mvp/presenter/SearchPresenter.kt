package com.example.kotlindemo.mvp.presenter

import com.example.kotlindemo.base.BasePresenter
import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.contract.SearchContract
import com.example.kotlindemo.mvp.model.SearchModel
import com.example.kotlindemo.mvp.model.entity.HotSearchBean
import com.example.kotlindemo.mvp.model.entity.SearchHistoryBean

class SearchPresenter : BasePresenter<SearchContract.ISearchView>() {

    private var searchModel: SearchModel? = null

    init {
        searchModel = SearchModel()
    }

    fun getHotTags() {
        searchModel?.getHotSearchData(object : ModelListener<MutableList<HotSearchBean>> {
            override fun onResponse(
                success: Boolean,
                data: MutableList<HotSearchBean>?,
                errorMsg: String?,
                ext: Throwable?
            ) {
                if (success) {
                    mView?.showHotSearchTag(data)
                } else {
                    mView?.showError(errorMsg)
                }
            }

        })
    }

    fun add(key: String) {
        searchModel?.add(key)
    }

    fun deleteById(id: Long) {
        searchModel?.deleteById(id)
    }

    fun queryHistory() {
        val query = searchModel?.query(object : ModelListener<MutableList<SearchHistoryBean>> {
            override fun onResponse(
                success: Boolean,
                data: MutableList<SearchHistoryBean>?,
                errorMsg: String?,
                ext: Throwable?
            ) {
                if (success)
                    mView?.showHistory(data)
            }
        })
        addSubscription(query)
    }

    fun clearAll() {
        searchModel?.clearAll()
    }
}