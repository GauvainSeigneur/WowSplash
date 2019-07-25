package com.seigneur.gauvain.wowsplash.business.paginationInteractor.collection

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.seigneur.gauvain.wowsplash.data.model.PhotoCollection
import com.seigneur.gauvain.wowsplash.data.repository.CollectionsRepository

import io.reactivex.disposables.CompositeDisposable

/**
 * A simple data source factory which also provides a way to observe the last created data source.
 * This allows us to channel its network request status etc back to the UI. See the Listing creation
 * in the Repository class.
 */
class CollectionDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val collectionsRepository: CollectionsRepository,
    private val listType: Int
) : DataSource.Factory<Long, PhotoCollection>() {

    val collectionDataSourceLiveData = MutableLiveData<CollectionDataSource>()

    override fun create(): DataSource<Long, PhotoCollection> {
        val collectionDataSource =
            CollectionDataSource(
                compositeDisposable,
                collectionsRepository,
                listType
            )
        collectionDataSourceLiveData.postValue(collectionDataSource)
        return collectionDataSource
    }

}

