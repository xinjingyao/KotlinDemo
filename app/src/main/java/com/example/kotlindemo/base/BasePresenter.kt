package com.example.kotlindemo.base

import com.blankj.utilcode.util.LogUtils
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<V: IView>: IPresenter<V> {

    var mView: V? = null

    var compositeDisposable = CompositeDisposable()

    override fun attachView(view: V) {
        mView = view
    }

    override fun detachView() {
        this.mView = null
        //保证activity结束时取消所有正在执行的订阅
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
            LogUtils.d("--compositeDisposable 网络已取消")
        }
    }

    override fun isDetach(): Boolean = mView == null

    fun addSubscription(disposable: Disposable?) {
        if (disposable != null) {
            compositeDisposable.add(disposable)
            LogUtils.d("--addSubscription 网络已添加")
        }
    }
}