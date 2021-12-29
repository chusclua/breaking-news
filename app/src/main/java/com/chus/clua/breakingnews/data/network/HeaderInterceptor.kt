package com.chus.clua.breakingnews.data.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeaderInterceptor @Inject constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        chain.request().newBuilder().apply {
            addHeader(API_KEY_HEADER, API_KEY)
        } .run {
            return chain.proceed(this.build())
        }
    }
}

private const val API_KEY_HEADER = "X-Api-Key"
private const val API_KEY = "ac553098fd8c4cc4ac23e980e2056923"