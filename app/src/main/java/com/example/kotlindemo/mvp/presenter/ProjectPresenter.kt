package com.example.kotlindemo.mvp.presenter

import com.example.kotlindemo.base.BasePresenter
import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.contract.ProjectContract
import com.example.kotlindemo.mvp.model.ProjectModel
import com.example.kotlindemo.mvp.model.entity.ProjectBean

class ProjectPresenter : BasePresenter<ProjectContract.IProjectView>() {

    private var projectModel: ProjectModel? = null

    init {
        projectModel = ProjectModel()
    }

    fun getProjectTree() {
        val projectTree =
            projectModel?.getProjectTree(object : ModelListener<List<ProjectBean>> {
                override fun onResponse(
                    success: Boolean,
                    data: List<ProjectBean>?,
                    errorMsg: String?,
                    ext: Throwable?
                ) {
                    if (success)
                        mView?.showProjectTree(data)
                    else
                        mView?.showError(errorMsg)
                }

            })
        addSubscription(projectTree)
    }
}