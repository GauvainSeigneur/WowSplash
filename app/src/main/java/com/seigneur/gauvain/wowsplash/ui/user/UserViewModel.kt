package com.seigneur.gauvain.wowsplash.ui.user

import androidx.lifecycle.MutableLiveData
import com.seigneur.gauvain.wowsplash.business.interactor.UserInteractor
import com.seigneur.gauvain.wowsplash.business.result.UserResult
import com.seigneur.gauvain.wowsplash.data.model.user.User
import com.seigneur.gauvain.wowsplash.data.repository.UserRepository
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel
import timber.log.Timber

class UserViewModel(userRepository: UserRepository) : BaseViewModel(), UserPresenter {

    val mUserResult = MutableLiveData<UserResult>()

    val mUserInteractor by lazy {
        UserInteractor(userRepository, mDisposables, this)
    }

    fun getMe() {
        mUserInteractor.getMe()
    }

    override fun onMeFetchedFromAPI(me: User) {
        Timber.d("me fetched $me")
    }

    override fun onError(throwable: Throwable) {
        if (throwable.message.equals("NO TOKEN AVAILABLE")) {
            Timber.d("token not available")
        } else {
            Timber.d("oups")
        }
    }

}
