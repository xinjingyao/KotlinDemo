package com.example.kotlindemo.mvp.presenter

import com.example.kotlindemo.base.BasePresenter
import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.contract.SearchContract
import com.example.kotlindemo.mvp.model.SearchModel
import com.example.kotlindemo.mvp.model.entity.HotSearchBean

class SearchPresenter : BasePresenter<SearchContract.ISearchView>() {

    private var searchModel: SearchModel? = null

    init {
        searchModel = SearchModel()
    }

    fun getHotTags() {
        searchModel?.getHotSearchData(object : ModelListener<MutableList<HotSearchBean>>{
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
}