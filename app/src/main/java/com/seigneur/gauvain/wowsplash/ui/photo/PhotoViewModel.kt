package com.seigneur.gauvain.wowsplash.ui.photo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.seigneur.gauvain.wowsplash.business.interactor.photo.PhotoInteractor
import com.seigneur.gauvain.wowsplash.business.interactor.photo.PhotoInteractorImpl
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.base.BaseDataSourceFactory
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.photo.PhotoDataSourceFactory
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.photo.PhotosDataSource
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.ui.base.paging.viewModel.BasePagingListViewModel
import com.seigneur.gauvain.wowsplash.utils.PHOTO_LIST_HOME
import com.seigneur.gauvain.wowsplash.utils.event.Event
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class PhotoViewModel(private val photoRepository: PhotoRepository) :
    BasePagingListViewModel<PhotosDataSource, Long, Photo>(), KoinComponent, PhotoPresenter {

    companion object {
        private val pageSize = 15
    }

    private val interactor by inject<PhotoInteractor> { parametersOf(this) }

    //LiveData to be listen
    var goToDetailsEvent = MutableLiveData<Event<Int>>()

    override fun presentGlobalError() {

    }

    override fun presentPhotoDetails(position: Int) {
        goToDetailsEvent.postValue(Event(position))
    }

    override fun presentPhotoLiked(position: Int) {

    }

    fun likePhoto(id: String?, pos: Int) {
        interactor.likePhoto(mDisposables, id, pos)
    }

    fun setPhotoClicked(photo: Photo?, position: Int) {
        interactor.onPhotoClicked(photo, position)
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
            config?.let{
                list = LivePagedListBuilder(photoDataSourceFactory, it).build()
            }
        }
    }
}
