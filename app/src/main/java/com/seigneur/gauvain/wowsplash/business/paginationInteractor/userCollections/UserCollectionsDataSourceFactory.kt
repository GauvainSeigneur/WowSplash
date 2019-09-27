package com.seigneur.gauvain.wowsplash.business.paginationInteractor.userCollections

import com.seigneur.gauvain.wowsplash.business.paginationInteractor.base.BaseDataSourceFactory
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.base.BaseListDataSource
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoCollection
import com.seigneur.gauvain.wowsplash.data.repository.CollectionsRepository

import io.reactivex.disposables.CompositeDisposable

/**
 * A simple data source factory which also provides a way to observe the last created data source.
 * This allows us to channel its network request status etc back to the UI. See the Listing creation
 * in the Repository class.
 */
class UserCollectionsDataSourceFactory(
    private val userName:String,
    private val compositeDisposable: CompositeDisposable,
    private val collectionsRepository: CollectionsRepository
) : BaseDataSourceFactory<UserCollectionsDataSource, Long, PhotoCollection>() {

    override fun createDataSource(): BaseListDataSource<UserCollectionsDataSource, Long, PhotoCollection> {
        return UserCollectionsDataSource(
            userName,
            compositeDisposable,
            collectionsRepository
        ) as BaseListDataSource<UserCollectionsDataSource, Long, PhotoCollection>
    }


}

