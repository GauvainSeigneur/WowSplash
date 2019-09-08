package com.seigneur.gauvain.wowsplash.business.paginationInteractor.search.collection

import com.seigneur.gauvain.wowsplash.business.paginationInteractor.base.BaseDataSourceFactory
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.base.BaseListDataSource
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoCollection
import com.seigneur.gauvain.wowsplash.data.repository.SearchRepository

import io.reactivex.disposables.CompositeDisposable

/**
 * A simple data source factory which also provides a way to observe the last created data source.
 * This allows us to channel its network request status etc back to the UI. See the Listing creation
 * in the Repository class.
 */
class SearchCollectionDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val mSearchRepository: SearchRepository,
    private val query: String
) : BaseDataSourceFactory<SearchCollectionDataSource, Long, PhotoCollection>() {

    override fun createDataSource(): BaseListDataSource<SearchCollectionDataSource, Long, PhotoCollection> {
        return SearchCollectionDataSource(
            compositeDisposable,
            mSearchRepository,
            query
        ) as BaseListDataSource<SearchCollectionDataSource, Long, PhotoCollection>
    }

}

