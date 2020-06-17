package com.example.kotlindemo.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment<V : IView, P: IPresenter<V>>: Fragment(), IView {

    var mPresenter: P? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutId(), container, false)
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
        return view
    }


    abstract fun getLayoutId(): Int
    abstract fun createPresenter(): P

    override fun showLoading(msg: String?) {
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }

    override fun showToast(msg: String?) {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
        mPresenter = null
    }
}