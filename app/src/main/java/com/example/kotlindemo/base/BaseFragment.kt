package com.example.kotlindemo.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ToastUtils

abstract class BaseFragment<V : IView, P: IPresenter<V>>: Fragment(), IView {

    var mPresenter: P? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
        initView()
        initData()
    }

    abstract fun initView()

    abstract fun initData()


    abstract fun getLayoutId(): Int
    abstract fun createPresenter(): P

    override fun showLoading(msg: String?) {

    }

    override fun hideLoading() {

    }

    override fun showToast(msg: String?) {
        ToastUtils.showShort(msg)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
        mPresenter = null
    }
}