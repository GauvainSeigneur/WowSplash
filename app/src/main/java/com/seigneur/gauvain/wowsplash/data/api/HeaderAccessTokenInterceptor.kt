package com.seigneur.gauvain.wowsplash.data.api

import com.seigneur.gauvain.wowsplash.data.repository.AuthRepository
import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class HeaderAccessTokenInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("Accept-Version", "v1")
        val tokenValue = AuthRepository.constAccessToken
        if (!tokenValue.isNullOrEmpty()) {
            Timber.d("is not empty ${AuthRepository.constAccessToken}")
            builder.addHeader("Authorization", "Bearer " + AuthRepository.constAccessToken)
        } else{
            Timber.d("is empty")
        }

        return chain.proceed(builder.build())
    }
}
