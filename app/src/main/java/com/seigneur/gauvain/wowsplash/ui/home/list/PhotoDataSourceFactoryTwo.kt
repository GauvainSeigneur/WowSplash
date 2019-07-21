package com.seigneur.gauvain.wowsplash.ui.home.list

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.ui.base.pagingList.dataSource.BaseListDataSource
import com.seigneur.gauvain.wowsplash.ui.base.pagingList.dataSource.BaseListDataSourceFactory

import io.reactivex.disposables.CompositeDisposable

/**
 * A simple data source factory which also provides a way to observe the last created data source.
 * This allows us to channel its network request status etc back to the UI. See the Listing creation
 * in the Repository class.
 */
class PhotoDataSourceFactoryTwo(
    private val compositeDisposable: CompositeDisposable,
    private val mPhotoRepository: PhotoRepository,
    private val searchType :String?
) : DataSource.Factory<Long, Photo>() {

    val usersDataSourceLiveData = MutableLiveData<PhotosDataSource>()

    override fun create(): DataSource<Long, Photo> {
        val shotsDataSource = PhotosDataSource(compositeDisposable, mPhotoRepository, null)
        usersDataSourceLiveData.postValue(shotsDataSource)
        return shotsDataSource
    }

}

