package com.seigneur.gauvain.wowsplash.ui.user

import com.seigneur.gauvain.wowsplash.data.model.user.User

interface UserPresenter {
    fun onMeFetchedFromAPI(me: User)
    fun onError(throwable: Throwable)
}