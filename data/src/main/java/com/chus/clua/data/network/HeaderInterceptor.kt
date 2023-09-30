package com.chus.clua.data.network

import com.chus.clua.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeaderInterceptor @Inject constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        chain.request().newBuilder().apply {
            addHeader(API_KEY_HEADER, BuildConfig.API_KEY)
        } .run {
            return chain.proceed(this.build())
        }
    }
}

private const val API_KEY_HEADER = "X-Api-Key"