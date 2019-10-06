package com.seigneur.gauvain.wowsplash.business.paginationInteractor.userCollections

import com.seigneur.gauvain.wowsplash.business.paginationInteractor.base.BaseListDataSource
import com.seigneur.gauvain.wowsplash.data.model.photo.CollectionItem
import com.seigneur.gauvain.wowsplash.data.repository.CollectionsRepository

import io.reactivex.Flowable

import io.reactivex.disposables.CompositeDisposable

class UserCollectionsDataSource
internal constructor(
    private val userName:String,
    private val compositeDisposable: CompositeDisposable,
    private val mCollectionsRepository: CollectionsRepository
) : BaseListDataSource<List<CollectionItem>, Long, CollectionItem>(compositeDisposable) {

    override fun handleCallback(expectedResponse: List<CollectionItem>): List<CollectionItem> {
        return expectedResponse
    }

    override val firstPageKey: Long
        get() = 1L

    override val firstNextPageKey: Long
        get() = 2L

    override fun nextKey(currentKey: Long): Long {
        return currentKey+1L
    }

    override fun loadInitialRequest(page: Long, size:Int): Flowable<List<CollectionItem>> {
        var requestFlowable = mCollectionsRepository.getUserCollections(userName, page, size)
            .flatMapIterable {it }
            .map { item -> CollectionItem(item, false) }
            .toList()

        val newItemFlowable = requestFlowable.toFlowable()

        return newItemFlowable
    }

    override fun loadAfterRequest(page:Long,size:Int): Flowable<List<CollectionItem>> {
        var requestFlowable = mCollectionsRepository.getUserCollections(userName, page, size)
            .flatMapIterable {it }
            .map { item -> CollectionItem(item, false) }
            .toList()

        val newItemFlowable = requestFlowable.toFlowable()
        return newItemFlowable
    }

}
