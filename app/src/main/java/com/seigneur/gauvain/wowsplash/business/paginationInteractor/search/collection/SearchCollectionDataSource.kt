package com.seigneur.gauvain.wowsplash.business.paginationInteractor.search.collection

import com.seigneur.gauvain.wowsplash.business.paginationInteractor.search.BaseSearchDataSource
import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.data.model.PhotoCollection
import com.seigneur.gauvain.wowsplash.data.model.SearchResponse
import com.seigneur.gauvain.wowsplash.data.model.user.User
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.data.repository.SearchRepository
import com.seigneur.gauvain.wowsplash.data.repository.UserRepository

import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable

class SearchCollectionDataSource
internal constructor(
    compositeDisposable: CompositeDisposable,
    private val mSearchRepository: SearchRepository,
    private val mQuery:String
) : BaseSearchDataSource<PhotoCollection>(compositeDisposable) {

    override fun loadInitialRequest(keyPage: Long, pageSize: Int): Flowable<SearchResponse<PhotoCollection>> {
        return mSearchRepository.searchCollection(mQuery, keyPage, pageSize).toFlowable()
    }

    override fun loadAfterRequest(keyPage: Long, pageSize: Int): Flowable<SearchResponse<PhotoCollection>> {
        return mSearchRepository.searchCollection(mQuery, keyPage, pageSize).toFlowable()
    }

}
