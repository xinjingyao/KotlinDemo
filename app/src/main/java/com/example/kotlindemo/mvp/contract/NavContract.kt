package com.example.kotlindemo.mvp.contract

import com.example.kotlindemo.base.IModel
import com.example.kotlindemo.base.IView
import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.model.entity.NavBean
import io.reactivex.disposables.Disposable

interface NavContract {

    interface INavView: IView {

        fun showNavList(navList: MutableList<NavBean>?)
    }

    interface INavModel: IModel{


        /**
         * 获取导航列表
         */
        fun getNavList(listener: ModelListener<MutableList<NavBean>>): Disposable?
    }
}