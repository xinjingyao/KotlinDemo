package com.example.kotlindemo.mvp.presenter

import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.contract.KnowledgeTreeContract
import com.example.kotlindemo.mvp.model.KnowledgeTreeModel
import com.example.kotlindemo.mvp.model.entity.KnowledgeTree

class KnowledgeTreePresenter : CommonPresenter<KnowledgeTreeContract.IKnowledgeTreeView>() {

    private var knowledgeTreeModel: KnowledgeTreeModel? = null

    init {
        knowledgeTreeModel = KnowledgeTreeModel()
    }

    fun getKnowledgeTreeList() {
        val knowledgeTree = knowledgeTreeModel?.getKnowledgeTreeList(object : ModelListener<List<KnowledgeTree>> {

            override fun onResponse(
                success: Boolean,
                data: List<KnowledgeTree>?,
                errorMsg: String?,
                ext: Throwable?
            ) {
                if (success)
                    mView?.showKnowledgeTree(data)
                else
                    mView?.showError(errorMsg)
            }
        })
        addSubscription(knowledgeTree)
    }
}