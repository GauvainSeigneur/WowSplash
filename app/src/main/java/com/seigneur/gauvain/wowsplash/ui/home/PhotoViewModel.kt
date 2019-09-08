package com.seigneur.gauvain.wowsplash.ui.home

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.base.BaseDataSourceFactory
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.photo.PhotoDataSourceFactory
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.photo.PhotosDataSource
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.ui.base.paging.viewModel.BasePagingListViewModel
import com.seigneur.gauvain.wowsplash.utils.PHOTO_LIST_HOME
import com.seigneur.gauvain.wowsplash.utils.event.Event
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class PhotoViewModel(private val mPhotoRepository: PhotoRepository) :
    BasePagingListViewModel<PhotosDataSource, Long, Photo>() {

    override val dataSourceFactory: BaseDataSourceFactory<PhotosDataSource, Long, Photo>
        get() = photoDataSourceFactory

    var list: LiveData<PagedList<Photo>>? = null
    private var orderBy: String? = null

    private var config: PagedList.Config? = null
    private val photoDataSourceFactory: PhotoDataSourceFactory by lazy {
        PhotoDataSourceFactory(
            mDisposables,
            mPhotoRepository,
            PHOTO_LIST_HOME,
            orderBy,
            null
        )
    }

    fun init(inOrderBy: String?) {
        orderBy = inOrderBy
        if (config == null) {
            config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize)
                .setEnablePlaceholders(false)
                .build()
            list = LivePagedListBuilder(photoDataSourceFactory, config!!).build()
        }
    }

    fun likePhoto(id: String?) {
        id?.let {
            mDisposables.add(
                mPhotoRepository.likePhoto(id)
                    .subscribeBy(  // named arguments for lambda Subscribers
                        onSuccess = { Timber.d("photo liked") },
                        onError = { Timber.d("error on photo liked $it") }
                    )
            )
        }
    }

    fun setPhotoClicked(photo: Photo?) {
        photo?.let {
            mPhotoRepository.photoClicked.postValue(Event(photo))
        }
    }

    companion object {
        private val pageSize = 15
    }

}
