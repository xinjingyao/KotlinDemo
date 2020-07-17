package com.example.kotlindemo.mvp.contract

import com.example.kotlindemo.base.IModel
import com.example.kotlindemo.base.IView
import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.model.entity.HotSearchBean
import com.example.kotlindemo.mvp.model.entity.SearchHistoryBean
import io.reactivex.disposables.Disposable

interface SearchContract {

    interface ISearchView : IView {
        fun showHotSearchTag(hotTags: MutableList<HotSearchBean>?)
        fun showHistory(historys: MutableList<SearchHistoryBean>?)
    }

    interface ISearchModel : IModel {
        fun getHotSearchData(listener: ModelListener<MutableList<HotSearchBean>>): Disposable?
        fun add(key: String)
        fun query(listener: ModelListener<MutableList<SearchHistoryBean>>): Disposable?
        fun deleteById(id: Long)
        fun clearAll()
    }
}