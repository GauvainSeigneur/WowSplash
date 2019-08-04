package com.seigneur.gauvain.wowsplash.business.paginationInteractor.photo

import com.seigneur.gauvain.wowsplash.business.paginationInteractor.base.BaseDataSourceFactory
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.base.BaseListDataSource
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository

import io.reactivex.disposables.CompositeDisposable

/**
 * A simple data source factory which also provides a way to observe the last created data source.
 * This allows us to channel its network request status etc back to the UI. See the Listing creation
 * in the Repository class.
 */
class PhotoDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val mPhotoRepository: PhotoRepository,
    private val listType: Int,
    private val orderBy: String?,
    private val query: String?
) : BaseDataSourceFactory<PhotosDataSource, Long, Photo>() {

    override fun createDataSource(): BaseListDataSource<PhotosDataSource, Long, Photo> {
        return PhotosDataSource(
            compositeDisposable,
            mPhotoRepository,
            listType,
            orderBy,
            query
        ) as BaseListDataSource<PhotosDataSource, Long, Photo>
    }

}

