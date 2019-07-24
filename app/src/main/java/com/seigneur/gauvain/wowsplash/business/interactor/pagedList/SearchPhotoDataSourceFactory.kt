package com.seigneur.gauvain.wowsplash.business.interactor.pagedList

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.seigneur.gauvain.wowsplash.business.result.SearchResult
import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.data.model.SearchResponse
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.data.repository.SearchRepository

import io.reactivex.disposables.CompositeDisposable

/**
 * A simple data source factory which also provides a way to observe the last created data source.
 * This allows us to channel its network request status etc back to the UI. See the Listing creation
 * in the Repository class.
 */
class SearchPhotoDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val mSearchRepository: SearchRepository,
    private val query :String,
    private val type: Any
) : DataSource.Factory<Long, Any>() {

    val searchDataSourceLiveData = MutableLiveData<SearchPhotosDataSource>()

    override fun create(): DataSource<Long,Any> {
        val shotsDataSource = SearchPhotosDataSource(
            compositeDisposable,
            mSearchRepository,
            query,
            type
        )
        searchDataSourceLiveData.postValue(shotsDataSource)
        return shotsDataSource
    }

}

