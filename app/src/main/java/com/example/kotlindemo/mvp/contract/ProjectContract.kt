package com.example.kotlindemo.mvp.contract

import com.example.kotlindemo.base.IModel
import com.example.kotlindemo.base.IView
import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.model.entity.ProjectBean
import io.reactivex.disposables.Disposable

interface ProjectContract {

    interface IProjectView: IView {

        fun showProjectTree(projectTree: List<ProjectBean>?)
    }

    interface IProjectModel: IModel{


        /**
         * 获取项目列表
         */
        fun getProjectTree(listener: ModelListener<List<ProjectBean>>): Disposable?
    }
}