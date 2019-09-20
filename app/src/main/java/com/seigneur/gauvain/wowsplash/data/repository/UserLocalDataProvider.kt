package com.seigneur.gauvain.wowsplash.data.repository

import androidx.lifecycle.Observer
import com.seigneur.gauvain.wowsplash.data.local.WowSplashDataBase
import com.seigneur.gauvain.wowsplash.data.model.user.User

class UserLocalDataProvider(private val database: WowSplashDataBase) {

    var isConnected = false

    init {
        listenUserData()
    }

    private fun listenUserData() {
        database.mUserDao().getUserLive.observeForever(Observer<User> {
            isConnected = it!=null
        })
    }


}
