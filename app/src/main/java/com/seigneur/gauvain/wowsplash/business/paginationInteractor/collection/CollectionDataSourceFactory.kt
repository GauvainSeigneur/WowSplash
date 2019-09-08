package com.seigneur.gauvain.wowsplash.business.paginationInteractor.collection

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.base.BaseDataSourceFactory
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.base.BaseListDataSource
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.photo.PhotosDataSource
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoCollection
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
) : BaseDataSourceFactory<CollectionDataSource, Long, PhotoCollection>() {

    override fun createDataSource(): BaseListDataSource<CollectionDataSource, Long, PhotoCollection> {
        return CollectionDataSource(
            compositeDisposable,
            collectionsRepository,
            listType
        ) as BaseListDataSource<CollectionDataSource, Long, PhotoCollection>
    }


}

