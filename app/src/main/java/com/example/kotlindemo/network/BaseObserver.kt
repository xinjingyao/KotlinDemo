package com.example.kotlindemo.network

import io.reactivex.observers.DisposableObserver
import network.response.Response

abstract class BaseObserver<T> : DisposableObserver<Response<T>>() {
    override fun onComplete() {

    }

    override fun onNext(t: Response<T>) {
        when (t.errorCode) {
            0 -> t.data?.let { onSuccess(it) }
            -1001 -> t.errorMsg?.let { onFailure(t.errorMsg, null) }
            else -> onFailure("未知错误", null)
        }
    }

    override fun onError(e: Throwable) {
        onFailure("", e)
    }

    abstract fun onSuccess(t: T)

    abstract fun onFailure(errorMsg: String, e: Throwable?)
}