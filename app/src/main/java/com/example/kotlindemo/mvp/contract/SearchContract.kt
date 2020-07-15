package com.example.kotlindemo.mvp.contract

import com.example.kotlindemo.base.IModel
import com.example.kotlindemo.base.IView
import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.model.entity.HotSearchBean
import io.reactivex.disposables.Disposable

interface SearchContract {

    interface ISearchView : IView {
        fun showHotSearchTag(hotTags: MutableList<HotSearchBean>?)
    }

    interface ISearchModel : IModel {
        fun getHotSearchData(listener: ModelListener<MutableList<HotSearchBean>>): Disposable?
    }
}