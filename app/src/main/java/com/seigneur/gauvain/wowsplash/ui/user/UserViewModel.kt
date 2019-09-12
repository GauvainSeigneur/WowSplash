package com.seigneur.gauvain.wowsplash.ui.user

import androidx.lifecycle.MutableLiveData
import com.seigneur.gauvain.wowsplash.business.interactor.UserInteractor
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
        mUserResult.postValue(UserResult.UserFetched(me))
    }

    override fun onError(throwable: Throwable) {
        if (throwable.message.equals("NO TOKEN AVAILABLE")) {
            Timber.d("token not available")
            mUserResult.postValue(UserResult.UserError(throwable))
        } else {
            Timber.d("oups")
            mUserResult.postValue(UserResult.UserError(throwable))
        }
    }
}

sealed class UserResult {
    data class UserFetched(val user: User) : UserResult()
    data class UserError(val inError: Throwable? = null) : UserResult()
}