package com.seigneur.gauvain.wowsplash.business.paginationInteractor.collection

import com.seigneur.gauvain.wowsplash.business.paginationInteractor.base.BaseListDataSource
import com.seigneur.gauvain.wowsplash.data.model.PhotoCollection
import com.seigneur.gauvain.wowsplash.data.repository.CollectionsRepository
import com.seigneur.gauvain.wowsplash.utils.COLLECTION_LIST_ALL
import com.seigneur.gauvain.wowsplash.utils.COLLECTION_LIST_FEATURED

import io.reactivex.Flowable

import io.reactivex.disposables.CompositeDisposable

class CollectionDataSource
internal constructor(
    private val compositeDisposable: CompositeDisposable,
    private val mCollectionsRepository: CollectionsRepository,
    private val listType: Int
) : BaseListDataSource<List<PhotoCollection>, Long, PhotoCollection>(compositeDisposable) {

    override fun handleCallback(expectedResponse: List<PhotoCollection>): List<PhotoCollection> {
        return expectedResponse
    }

    override val firstPageKey: Long
        get() = 1L

    override val firstNextPageKey: Long
        get() = 2L

    override fun nextKey(currentKey: Long): Long {
        return currentKey+1L
    }

    override fun loadInitialRequest(page: Long, size:Int): Flowable<List<PhotoCollection>> {
        var requestFlowable = mCollectionsRepository.getCollections(page, size)
        when (listType) {
            COLLECTION_LIST_ALL -> {
                requestFlowable =   mCollectionsRepository.getCollections(page, size)
            }
            COLLECTION_LIST_FEATURED -> {
                requestFlowable = mCollectionsRepository.getFeaturedCollections(page, size)
            }
        }
        return requestFlowable
    }

    override fun loadAfterRequest(page:Long,size:Int): Flowable<List<PhotoCollection>> {
        var requestFlowable = mCollectionsRepository.getCollections(page, size)
        when (listType) {
            COLLECTION_LIST_ALL -> {
                requestFlowable =   mCollectionsRepository.getCollections(page, size)
            }
            COLLECTION_LIST_FEATURED -> {
                requestFlowable = mCollectionsRepository.getFeaturedCollections(page, size)
            }
        }
        return requestFlowable
    }

}
