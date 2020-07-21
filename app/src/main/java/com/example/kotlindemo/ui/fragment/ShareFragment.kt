package com.example.kotlindemo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.mvp.contract.EmptyContract
import com.example.kotlindemo.mvp.presenter.EmptyPresenter

class ShareFragment: BaseFragment<EmptyContract.IEmptyView, EmptyPresenter>() {

    companion object{
        fun newInstance(): Fragment{
            val args = Bundle()

            val fragment = ShareFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_share

    override fun createPresenter(): EmptyPresenter = EmptyPresenter()

    override fun initView() {
    }

    override fun initData() {
    }
}