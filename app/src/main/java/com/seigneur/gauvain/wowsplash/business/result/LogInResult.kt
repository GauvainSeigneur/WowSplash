package com.seigneur.gauvain.wowsplash.business.result

sealed class LogInResult {
    data class LogInSuccess(val accessToken: String?) : LogInResult()
    data class LogInError(val inError: Throwable? = null) : LogInResult()
}