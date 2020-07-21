package com.example.kotlindemo.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.mvp.contract.ShareContract
import com.example.kotlindemo.mvp.presenter.SharePresenter
import kotlinx.android.synthetic.main.fragment_share.*

class ShareFragment: BaseFragment<ShareContract.IShareView, SharePresenter>() , ShareContract.IShareView{

    companion object{
        fun newInstance(): Fragment{
            val args = Bundle()

            val fragment = ShareFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_share

    override fun createPresenter(): SharePresenter = SharePresenter()

    override fun initView() {
        setHasOptionsMenu(true)
    }

    override fun initData() {
    }

    override fun showShareResult(success: Boolean) {
        if (success) {
            activity?.finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_share, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_share -> {
                mPresenter?.share(et_title.text.toString().trim(), et_link.text.toString().trim())
            }
        }
        return super.onOptionsItemSelected(item)
    }
}