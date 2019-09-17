package com.seigneur.gauvain.wowsplash.business.interactor.user

/**
 *  Must be used only by ViewModel which inject all dependencies
 */
interface UserInteractor {

    fun getMe()
    fun close()

}

