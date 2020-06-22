package com.example.kotlindemo.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

abstract class BaseActivity<V : IView, P : BasePresenter<V>> : AppCompatActivity(), IView {

    protected var mPresenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        mPresenter = getPresenter()
        mPresenter?.attachView(this as V)
        initView()
        initData()
    }


    abstract fun getPresenter(): P

    abstract fun getLayoutId(): Int

    open fun initView() {

    }
    abstract fun initData()

    override fun showLoading(msg: String?) {
    }

    override fun hideLoading() {
    }

    override fun showToast(msg: String?) {
        runOnUiThread { Toast.makeText(this, msg, Toast.LENGTH_SHORT).show() }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
        mPresenter = null
    }
}
