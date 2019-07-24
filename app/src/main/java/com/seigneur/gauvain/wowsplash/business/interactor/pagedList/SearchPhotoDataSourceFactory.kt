package com.seigneur.gauvain.wowsplash.business.interactor.pagedList

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.seigneur.gauvain.wowsplash.data.model.Photo
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
    private val searchType :String
) : DataSource.Factory<Long, Photo>() {

    val photoDataSourceLiveData = MutableLiveData<SearchPhotosDataSource>()

    override fun create(): DataSource<Long, Photo> {
        val shotsDataSource = SearchPhotosDataSource(
            compositeDisposable,
            mSearchRepository,
            searchType
        )
        photoDataSourceLiveData.postValue(shotsDataSource)
        return shotsDataSource
    }

}

