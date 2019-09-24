package com.seigneur.gauvain.wowsplash.ui.photo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.seigneur.gauvain.wowsplash.business.interactor.photoList.PhotoListInteractor
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

class PhotoListViewModel(
    private val photoRepository: PhotoRepository
) : BasePagingListViewModel<PhotosDataSource, Long, Photo>(), PhotoListPresenter, KoinComponent {


    private val interactor by inject<PhotoListInteractor> { parametersOf(this) }

    var goToDetailsEvent = MutableLiveData<Event<Int>>()

    companion object {
        private val pageSize = 15
    }

    override fun presentPhotoDetails(position: Int) {
        goToDetailsEvent.postValue(Event(position))
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
            config?.let {
                list = LivePagedListBuilder(photoDataSourceFactory, it).build()
            }
        }
    }
}
