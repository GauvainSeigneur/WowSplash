package com.seigneur.gauvain.wowsplash.business.paginationInteractor.search.user

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.seigneur.gauvain.wowsplash.data.model.user.User
import com.seigneur.gauvain.wowsplash.data.repository.UserRepository

import io.reactivex.disposables.CompositeDisposable

/**
 * A simple data source factory which also provides a way to observe the last created data source.
 * This allows us to channel its network request status etc back to the UI. See the Listing creation
 * in the Repository class.
 */
class SearchUserDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val mUserRepository: UserRepository,
    private val query: String
) : DataSource.Factory<Long, User>() {

    val userDataSourceLiveData = MutableLiveData<SearchUserDataSource>()

    override fun create(): DataSource<Long, User> {
        val userDataSource = SearchUserDataSource(
            compositeDisposable,
            mUserRepository,
            query
        )
        userDataSourceLiveData.postValue(userDataSource)
        return userDataSource
    }

}

