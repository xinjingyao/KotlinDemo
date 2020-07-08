package com.example.kotlindemo.mvp.presenter

import com.example.kotlindemo.base.BasePresenter
import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.contract.NavContract
import com.example.kotlindemo.mvp.model.NavModel
import com.example.kotlindemo.mvp.model.entity.NavBean

class NavPresenter : BasePresenter<NavContract.INavView>() {

    private var navModel: NavModel? = null

    init {
        navModel = NavModel()
    }

    fun getNavList() {
        navModel?.getNavList(object : ModelListener<MutableList<NavBean>>{
            override fun onResponse(
                success: Boolean,
                data: MutableList<NavBean>?,
                errorMsg: String?,
                ext: Throwable?
            ) {
                if (success) {
                    mView?.showNavList(data)
                } else {
                    mView?.showError(errorMsg)
                }
            }

        })
    }
}