package com.seigneur.gauvain.wowsplash.ui.user

import androidx.lifecycle.MutableLiveData
import com.seigneur.gauvain.wowsplash.business.interactor.user.UserInteractor
import com.seigneur.gauvain.wowsplash.data.model.user.User
import com.seigneur.gauvain.wowsplash.data.repository.UserRepository
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class UserViewModel(userRepository: UserRepository) : BaseViewModel(), UserPresenter, KoinComponent{

    val mUserResult = MutableLiveData<UserResult>()

    private val interactor by inject<UserInteractor>{ parametersOf(this) }


    override fun onCleared() {
        interactor.close()
        super.onCleared()
    }

    fun getMe() {
        interactor.getMe()
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