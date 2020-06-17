package com.example.kotlindemo.base

abstract class BasePresenter<V: IView>: IPresenter<V> {

    var mView: V? = null

    override fun attachView(view: V) {
        mView = view
    }

    override fun detachView() {
        this.mView = null
    }

    override fun isDetach(): Boolean = mView == null

}