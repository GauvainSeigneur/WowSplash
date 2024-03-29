package com.seigneur.gauvain.wowsplash.business.paginationInteractor.search.photo

import com.seigneur.gauvain.wowsplash.business.paginationInteractor.search.BaseSearchDataSource
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.seigneur.gauvain.wowsplash.data.model.search.SearchResponse
import com.seigneur.gauvain.wowsplash.data.repository.SearchRepository

import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable

class SearchPhotoDataSource
internal constructor(
    compositeDisposable: CompositeDisposable,
    private val mSearchRepository: SearchRepository,
    private val mQuery:String
) : BaseSearchDataSource<Photo>(compositeDisposable) {

    override fun loadInitialRequest(keyPage: Long, pageSize: Int): Flowable<SearchResponse<Photo>> {
        return mSearchRepository.searchPhotos(keyPage, pageSize,mQuery).toFlowable()
    }

    override fun loadAfterRequest(keyPage: Long, pageSize: Int): Flowable<SearchResponse<Photo>> {
        return mSearchRepository.searchPhotos(keyPage, pageSize,mQuery).toFlowable()
    }

}
