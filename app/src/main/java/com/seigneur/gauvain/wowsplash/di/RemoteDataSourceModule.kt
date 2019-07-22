package com.seigneur.gauvain.wowsplash.di

import com.seigneur.gauvain.wowsplash.data.api.ClientIdInterceptor
import com.seigneur.gauvain.wowsplash.data.api.HeaderAccessTokenInterceptor
import com.seigneur.gauvain.wowsplash.di.DatasourceProperties.SERVER_URL
import com.seigneur.gauvain.wowsplash.data.api.UnSplashService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

val remoteDataSourceModule = module {

    factory<Interceptor> {
        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            Timber.tag("UNSPLASH API").d(it)
        }
        )
            .setLevel(HttpLoggingInterceptor.Level.HEADERS)
    }

    factory {
        OkHttpClient.Builder()
            .addInterceptor(get())
            .addNetworkInterceptor(ClientIdInterceptor())
            .addNetworkInterceptor(HeaderAccessTokenInterceptor())
            .connectTimeout(30L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    factory { get<Retrofit>().create(UnSplashService::class.java) }
}

object DatasourceProperties {

    const val SERVER_URL = "https://api.unsplash.com/"
}




