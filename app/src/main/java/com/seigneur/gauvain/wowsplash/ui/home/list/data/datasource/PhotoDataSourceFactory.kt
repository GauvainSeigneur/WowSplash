package com.seigneur.gauvain.wowsplash.ui.home.list.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository

import io.reactivex.disposables.CompositeDisposable

/**
 * A simple data source factory which also provides a way to observe the last created data source.
 * This allows us to channel its network request status etc back to the UI. See the Listing creation
 * in the Repository class.
 */
class PhotoDataSourceFactory(
        private val compositeDisposable: CompositeDisposable,
        private val mPhotoRepository: PhotoRepository) : DataSource.Factory<Long, Photo>() {

    val usersDataSourceLiveData = MutableLiveData<PhotosDataSource>()

    override fun create(): DataSource<Long, Photo> {
        val shotsDataSource = PhotosDataSource(compositeDisposable, mPhotoRepository)
        usersDataSourceLiveData.postValue(shotsDataSource)
        return shotsDataSource
    }

}
