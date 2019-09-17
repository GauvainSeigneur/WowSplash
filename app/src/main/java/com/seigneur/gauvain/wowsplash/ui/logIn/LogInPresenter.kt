package com.seigneur.gauvain.wowsplash.ui.logIn

interface LogInPresenter {
    fun onAuthSuccess()
    fun onAuthFailed(throwable: Throwable)
}