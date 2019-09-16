package com.seigneur.gauvain.wowsplash.ui.photoList

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.seigneur.gauvain.wowsplash.business.interactor.photo.PhotoInteractorImpl
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.base.BaseDataSourceFactory
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.photo.PhotoDataSourceFactory
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.photo.PhotosDataSource
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.ui.base.paging.viewModel.BasePagingListViewModel
import com.seigneur.gauvain.wowsplash.utils.PHOTO_LIST_HOME
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class PhotoViewModel(private val photoRepository: PhotoRepository) :
    BasePagingListViewModel<PhotosDataSource, Long, Photo>(), KoinComponent, PhotoPresenter {

    private val interactor by inject<PhotoInteractorImpl>()

    override fun presentGlobalError() {

    }

    override fun presentPhotoDetails() {
        Timber.d("presentPhotoDetails")
    }

    override fun presentPhotoLiked(position: Int) {

    }

    fun likePhoto(id: String?, pos: Int) {
        interactor.likePhoto(mDisposables, id, pos)
    }

    fun setPhotoClicked(photo: Photo?) {
        interactor.onPhotoClicked(photo)
    }

    /**************************************************************************
     * Paged List
     *************************************************************************/
    var list: LiveData<PagedList<Photo>>? = null
    private var orderBy: String? = null
    private var config: PagedList.Config? = null
    private val photoDataSourceFactory: PhotoDataSourceFactory by lazy {
        PhotoDataSourceFactory(
            mDisposables,
            photoRepository,
            PHOTO_LIST_HOME,
            orderBy,
            null
        )
    }

    override val dataSourceFactory: BaseDataSourceFactory<PhotosDataSource, Long, Photo>
        get() = photoDataSourceFactory

    /**
     * Start request data list
     */
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

    companion object {
        private val pageSize = 15
    }

}
