package com.seigneur.gauvain.wowsplash.data.api

import com.seigneur.gauvain.wowsplash.data.repository.TokenRepository
import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class HeaderAccessTokenInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("Accept-Version", "v1")
        val tokenValue = TokenRepository.accessToken
        if (!tokenValue.isNullOrEmpty()) {
            Timber.d("is not empty ${TokenRepository.accessToken}")
            builder.addHeader("Authorization", "Bearer " + TokenRepository.accessToken)
        } else{
            Timber.d("is empty")
        }
        return chain.proceed(builder.build())
    }
}
