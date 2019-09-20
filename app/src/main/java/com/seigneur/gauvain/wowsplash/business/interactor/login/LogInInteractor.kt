package com.seigneur.gauvain.wowsplash.business.interactor.login

import android.net.Uri


interface LogInInteractor {

    fun checkAuthUrl(url: Uri)
    fun close()
}

