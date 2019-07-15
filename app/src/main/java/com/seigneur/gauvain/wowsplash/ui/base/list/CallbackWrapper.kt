package com.seigneur.gauvain.wowsplash.ui.base.list

import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException

import java.io.IOException
import java.net.SocketTimeoutException


/**
 * From: https://medium.com/mindorks/rxjava2-and-retrofit2-error-handling-on-a-single-place-8daf720d42d6
 */
abstract class CallbackWrapper<T : Any> : DisposableObserver<T>() {

    protected abstract fun onSuccess(t: T)

    override fun onNext(t: T) {
        //You can return StatusCodes of different cases from your API and handle it here. I usually include these cases on BaseResponse and iherit it from every Response
        onSuccess(t)
    }

    override fun onError(e: Throwable) {
        if (e is HttpException) {
            val responseBody = e.response().errorBody()
        } else if (e is SocketTimeoutException) {
        } else if (e is IOException) {
        } else {
        }
    }

    override fun onComplete() {

    }

}