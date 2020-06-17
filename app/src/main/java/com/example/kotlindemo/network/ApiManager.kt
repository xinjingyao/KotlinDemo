package com.mg.axechen.wanandroid.network

import com.example.kotlindemo.network.converter.GsonConverterFactory
import com.mg.axechen.wanandroid.network.interceptor.AddCookieInterceptor
import com.mg.axechen.wanandroid.network.interceptor.GetCookieInterceptor
import network.request.Request
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

/**
 * Created by AxeChen on 2018/3/19.
 *
 * 网络请求管理类
 */
class ApiManager private constructor() {

    companion object {
        var mManger: ApiManager? = null
        var mRetrofit: retrofit2.Retrofit? = null
        var mRequest: Request? = null

        // 初始化NetWorkManager单例
        fun getInstance(): ApiManager {
            if (mManger == null) {
                synchronized(ApiManager::class) {
                    if (mManger == null) {
                        mManger = ApiManager()
                    }
                }
            }
            return mManger!!
        }
    }

    fun getRequest(): Request? {
        if (mRequest == null) {
            synchronized(Request::class) {
                mRequest = mRetrofit?.create(Request::class.java)
            }
        }
        return mRequest
    }

    fun init() {

        // 初始化OKHTTP
        val client: okhttp3.OkHttpClient.Builder = okhttp3.OkHttpClient.Builder().apply {
            addInterceptor(okhttp3.logging.HttpLoggingInterceptor())
            addInterceptor(AddCookieInterceptor())
            addInterceptor(GetCookieInterceptor())
        }

        // 初始化Retrofit
        mRetrofit = retrofit2.Retrofit.Builder().apply {
            client(client.build())
            baseUrl(Request.HOST)
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            addConverterFactory(GsonConverterFactory.create())


        }.build()
    }


}