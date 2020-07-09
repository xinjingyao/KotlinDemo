package com.example.kotlindemo.mvp.contract

import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.model.entity.ProjectListBean
import io.reactivex.disposables.Disposable

interface ProjectListContract {

    interface IProjectListView: CommonContract.ICommonView {

        fun showProjectList(projectListBean: ProjectListBean?)
    }

    interface IProjectListModel: CommonContract.ICommonModel{


        /**
         * 获取项目列表
         */
        fun getProjectListByCid(page: Int, cid: Int, listener: ModelListener<ProjectListBean>): Disposable?
    }
}