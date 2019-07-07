package com.seigneur.gauvain.wowsplash.data.api

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("Accept-Version", "v1")
        return chain.proceed(builder.build())
    }
}
