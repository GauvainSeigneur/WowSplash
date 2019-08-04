package com.seigneur.gauvain.wowsplash.business.paginationInteractor.photo

import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.base.BaseListDataSource

import io.reactivex.Flowable

import io.reactivex.disposables.CompositeDisposable

class PhotosDataSource
internal constructor(
    private val compositeDisposable: CompositeDisposable,
    private val mPhotoRepository: PhotoRepository,
    private val listType: Int,
    private val orderBy: String?,
    private val query: String?
) : BaseListDataSource<List<Photo>, Long, Photo>(compositeDisposable) {

    override fun handleCallback(expectedResponse: List<Photo>): List<Photo> {
        return expectedResponse
    }

    override val firstPageKey: Long
        get() = 1L

    override val firstNextPageKey: Long
        get() = 2L

    override fun nextKey(currentKey: Long): Long {
        return currentKey+1L
    }

    override fun loadInitialRequest(page: Long, size:Int): Flowable<List<Photo>> {
        return mPhotoRepository.getPhotos(page, size, orderBy)
    }

    override fun loadAfterRequest(page:Long,size:Int): Flowable<List<Photo>> {
        return mPhotoRepository.getPhotos(page, size, orderBy)
    }

}
