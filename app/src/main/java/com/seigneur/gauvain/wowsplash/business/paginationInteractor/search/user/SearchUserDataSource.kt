package com.seigneur.gauvain.wowsplash.business.paginationInteractor.search.user

import com.seigneur.gauvain.wowsplash.business.paginationInteractor.search.BaseSearchDataSource
import com.seigneur.gauvain.wowsplash.data.model.search.SearchResponse
import com.seigneur.gauvain.wowsplash.data.model.user.User
import com.seigneur.gauvain.wowsplash.data.repository.UserRepository

import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable

class SearchUserDataSource
internal constructor(
    compositeDisposable: CompositeDisposable,
    private val mUserRepository: UserRepository,
    private val mQuery:String
) : BaseSearchDataSource<User>(compositeDisposable) {

    override fun loadInitialRequest(keyPage: Long, pageSize: Int): Flowable<SearchResponse<User>> {
        return mUserRepository.searchUser(mQuery, keyPage, pageSize).toFlowable()
    }

    override fun loadAfterRequest(keyPage: Long, pageSize: Int): Flowable<SearchResponse<User>> {
        return mUserRepository.searchUser(mQuery, keyPage, pageSize).toFlowable()
    }

}
