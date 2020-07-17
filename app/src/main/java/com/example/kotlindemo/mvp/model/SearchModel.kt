package com.example.kotlindemo.mvp.model

import com.blankj.utilcode.util.LogUtils
import com.example.kotlindemo.listener.ModelListener
import com.example.kotlindemo.mvp.contract.SearchContract
import com.example.kotlindemo.mvp.model.entity.HotSearchBean
import com.example.kotlindemo.mvp.model.entity.SearchHistoryBean
import com.mg.axechen.wanandroid.network.RetrofitHelper
import com.mg.axechen.wanandroid.network.response.ResponseTransformer
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import network.schedules.SchedulerProvider
import org.litepal.LitePal
import org.litepal.extension.delete
import org.litepal.extension.deleteAll
import org.litepal.extension.find
import org.litepal.extension.findAll

class SearchModel : SearchContract.ISearchModel {

    override fun getHotSearchData(listener: ModelListener<MutableList<HotSearchBean>>): Disposable? {
        return RetrofitHelper.getInstance().getRequest()
            ?.getHotSearchTag()
            ?.compose(SchedulerProvider.getInstatnce()?.applySchedulers())
            ?.compose(ResponseTransformer.handleResult())
            ?.subscribe(
                { t -> listener.onResponse(true, t, null, null) },
                { throwable -> listener.onResponse(false, null, throwable.message, throwable) }
            )
    }

    override fun add(key: String) {
        val subscribe = Observable.create<SearchHistoryBean> {
            val finds = LitePal.where("key = '${key.trim()}'")
                .find<SearchHistoryBean>()
            val history = SearchHistoryBean(key.trim())
            if (finds.isNotEmpty()) {
                deleteById(finds[0].id)
            }
            history.save()
            it.onNext(history)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t ->
                LogUtils.d("--add history success")

            }
    }

    override fun query(listener: ModelListener<MutableList<SearchHistoryBean>>): Disposable? {
        return Observable.create<MutableList<SearchHistoryBean>> {
            LogUtils.d("--thread=${Thread.currentThread().name}")
            val findAll = LitePal.findAll<SearchHistoryBean>()
            // 倒叙
            findAll.reverse()
            it.onNext(findAll)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t ->
                LogUtils.d("--query success t.size=${t.size}")
                listener.onResponse(true, t, null, null)
            }

    }

    override fun deleteById(id: Long) {
        LitePal.delete<SearchHistoryBean>(id)
    }

    override fun clearAll() {
        val subscribe = Observable.create<String> {
            LitePal.deleteAll<SearchHistoryBean>()
            it.onNext("complete")
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t ->
                LogUtils.d("--deleteAll history success")
            }
    }
}