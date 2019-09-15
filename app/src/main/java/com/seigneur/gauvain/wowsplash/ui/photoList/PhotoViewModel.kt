package com.seigneur.gauvain.wowsplash.ui.photoList

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.seigneur.gauvain.wowsplash.business.interactor.PhotoInteractor
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.base.BaseDataSourceFactory
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.photo.PhotoDataSourceFactory
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.photo.PhotosDataSource
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.seigneur.gauvain.wowsplash.data.TemporaryDataProvider
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.di.PHOTO_DETAILS_TEMP_SCOPE_NAME
import com.seigneur.gauvain.wowsplash.di.PHOTO_DETAILS_TEMP_SCOPE_SESSION_ID
import com.seigneur.gauvain.wowsplash.ui.base.paging.viewModel.BasePagingListViewModel
import com.seigneur.gauvain.wowsplash.utils.PHOTO_LIST_HOME
import org.koin.core.KoinComponent
import org.koin.core.qualifier.named

class PhotoViewModel(private val mPhotoRepository: PhotoRepository) :
    BasePagingListViewModel<PhotosDataSource, Long, Photo>(), KoinComponent, PhotoPresenter {

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

    private val photoInteractor=PhotoInteractor(mDisposables, mPhotoRepository, this)

    override val dataSourceFactory: BaseDataSourceFactory<PhotosDataSource, Long, Photo>
        get() = photoDataSourceFactory

    override fun presentPhotoLiked(pos:Int) {
    }

    override fun presentGlobalError() {

    }

    /**
     * Start request data list
     */
    fun init(inOrderBy: String?) {

    }

    fun likePhoto(id: String?, pos:Int) {
        photoInteractor.likePhoto(id, pos)
    }

    fun setPhotoClicked(photo: Photo?) {
        photo?.let {
            val temporaryDataProviderSession = getKoin()
                .getOrCreateScope(PHOTO_DETAILS_TEMP_SCOPE_SESSION_ID, named(PHOTO_DETAILS_TEMP_SCOPE_NAME))
            val temporaryDataProvider = temporaryDataProviderSession.get<TemporaryDataProvider>()
            temporaryDataProvider.photoClicked.postValue(photo)
        }
    }

    companion object {
        private val pageSize = 15
    }

}
