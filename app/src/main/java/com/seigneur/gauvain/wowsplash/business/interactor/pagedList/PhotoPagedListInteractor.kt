package com.seigneur.gauvain.wowsplash.business.interactor.pagedList

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.seigneur.gauvain.wowsplash.business.interactor.PhotoInteractor
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.base.BaseDataSourceFactory
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.photo.PhotoDataSourceFactory
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.photo.PhotosDataSource
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.utils.PHOTO_LIST_HOME
import io.reactivex.disposables.CompositeDisposable

/**
 *  Must be used only by ViewModel which inject all dependencies
 */
class PhotoPagedListInteractor(
    private val compositeDisposable: CompositeDisposable,
    private val photoRepository: PhotoRepository,
    private var list: LiveData<PagedList<Photo>>? = null
) : BasePagedListInteractor<PhotosDataSource, Long, Photo>() {

    private var orderBy: String? = null
    private var config: PagedList.Config? = null
    private val photoDataSourceFactory: PhotoDataSourceFactory by lazy {
        PhotoDataSourceFactory(
            compositeDisposable,
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
    fun initList(inOrderBy: String?) {
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

