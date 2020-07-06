package com.example.kotlindemo.mvp.contract

import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.model.entity.KnowledgeTree
import io.reactivex.disposables.Disposable

interface KnowledgeTreeContract {

    interface IKnowledgeTreeView: CommonContract.ICommonView {

        fun showKnowledgeTree(knowledgeTreeList: List<KnowledgeTree>?)
    }

    interface IKnowledgeTreeModel: CommonContract.ICommonModel{


        /**
         * 获取知识树
         */
        fun getKnowledgeTreeList(listener: ModelListener<List<KnowledgeTree>>): Disposable?
    }
}