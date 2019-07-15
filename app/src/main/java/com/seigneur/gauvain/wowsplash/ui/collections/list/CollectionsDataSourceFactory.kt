package com.seigneur.gauvain.wowsplash.ui.collections.list

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
class CollectionsDataSourceFactory(
        private val compositeDisposable: CompositeDisposable,
        private val mCollectionsRepository: CollectionsRepository) : DataSource.Factory<Long, PhotoCollection>() {

    val collectionLiveData = MutableLiveData<CollectionsDataSource>()

    override fun create(): DataSource<Long, PhotoCollection> {
        val collectionDataSource =
            CollectionsDataSource(compositeDisposable, mCollectionsRepository)
        collectionLiveData.postValue(collectionDataSource)
        return collectionDataSource
    }

}
