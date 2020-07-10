package com.example.kotlindemo.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.blankj.utilcode.util.ToastUtils

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


    abstract fun getLayoutId(): Int

    abstract fun getPresenter(): P

    open fun initView() {

    }
    abstract fun initData()

    override fun showLoading(msg: String?) {
    }

    override fun hideLoading() {
    }

    override fun showToast(msg: String?) {
        runOnUiThread { ToastUtils.showShort(msg) }
    }

    override fun showError(msg: String?) {
        runOnUiThread { ToastUtils.showShort(msg) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> { onBackPressed() }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
        mPresenter = null
    }
}
