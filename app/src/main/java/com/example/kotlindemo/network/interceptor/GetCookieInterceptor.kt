package com.mg.axechen.wanandroid.network.interceptor

import com.blankj.utilcode.util.SPUtils
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by AxeChen on 2018/4/21.
 * 登录时保存cookies
 */
class GetCookieInterceptor : Interceptor {

    private val SAVE_USER_LOGIN_KEY = "user/login"
    private val SET_COOKIE_KEY = "set-cookie"

    override fun intercept(chain: Interceptor.Chain?): Response {
        val request = chain?.request()
        val response = chain?.proceed(request)
        val requestUrl = request?.url().toString()
        val domain = request?.url()?.host()
        // 登录之后保存cookies
        if (requestUrl.contains(SAVE_USER_LOGIN_KEY) &&
                !response?.header(SET_COOKIE_KEY)?.isEmpty()!!) {
            val cookies = response.headers(SET_COOKIE_KEY)
            val cookie = encodeCookie(cookies)
            saveCookie(requestUrl, domain, cookie)
        }
        return response!!
    }

    /**
     *  构造cookies字符
     */
    private fun encodeCookie(cookies: List<String>): String {
        val sb = StringBuilder()
        val set = HashSet<String>()
        cookies
                .map { cookie ->
                    cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                }
                .forEach {
                    it.filterNot { set.contains(it) }.forEach { set.add(it) }
                }

        val ite = set.iterator()
        while (ite.hasNext()) {
            val cookie = ite.next()
            sb.append(cookie).append(";")
        }

        val last = sb.lastIndexOf(";")
        if (sb.length - 1 == last) {
            sb.deleteCharAt(last)
        }

        return sb.toString()
    }

    /**
     * 保存cookies字符
     */
    private fun saveCookie(url: String?, domain: String?, cookies: String) {
        url ?: return
        SPUtils.getInstance().put(url, cookies)
        domain ?: return
        SPUtils.getInstance().put(domain, cookies)
    }

}