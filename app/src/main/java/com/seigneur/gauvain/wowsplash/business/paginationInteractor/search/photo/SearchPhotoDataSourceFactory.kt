package com.seigneur.gauvain.wowsplash.business.paginationInteractor.search.photo

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.seigneur.gauvain.wowsplash.data.model.Photo
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
    private val query: String
) : DataSource.Factory<Long, Photo>() {

    val userDataSourceLiveData = MutableLiveData<SearchPhotoDataSource>()

    override fun create(): DataSource<Long, Photo> {
        val userDataSource =
            SearchPhotoDataSource(
                compositeDisposable,
                mSearchRepository,
                query
            )
        userDataSourceLiveData.postValue(userDataSource)
        return userDataSource
    }

}

