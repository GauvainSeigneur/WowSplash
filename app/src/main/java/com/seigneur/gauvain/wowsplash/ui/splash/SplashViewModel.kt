package com.seigneur.gauvain.wowsplash.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.data.model.network.NetworkState
import com.seigneur.gauvain.wowsplash.data.repository.AuthRepository
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel
import com.seigneur.gauvain.wowsplash.ui.home.list.PhotoDataSourceFactory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class SplashViewModel(private val mAuthRepository: AuthRepository) :
    BaseViewModel() {

    var lol = MutableLiveData<String>()

    fun fetchTokenFromDB(){
        mDisposables.add(mAuthRepository.getAccessTokenFromDB()
            .subscribeBy(
                onSuccess = {
                    AuthRepository.accessToken = it.access_token
                    Timber.d("access token fetched ${AuthRepository.accessToken}")
                    lol.value = "lol"
                },
                onError = {
                    Timber.d("access token error ${it}")
                },
                onComplete = {
                    lol.value = "lol"
                    Timber.d("access token not found")
                }
            )
        )
    }

}
