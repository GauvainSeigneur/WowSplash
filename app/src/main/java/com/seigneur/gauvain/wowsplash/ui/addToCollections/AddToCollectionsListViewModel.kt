package com.seigneur.gauvain.wowsplash.ui.addToCollections

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.seigneur.gauvain.wowsplash.business.interactor.photoList.PhotoListInteractor
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.base.BaseDataSourceFactory
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.photo.PhotoDataSourceFactory
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.photo.PhotosDataSource
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.userCollections.UserCollectionsDataSource
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.userCollections.UserCollectionsDataSourceFactory
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoCollection
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoItem
import com.seigneur.gauvain.wowsplash.data.repository.CollectionsRepository
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.data.repository.TempDataRepository
import com.seigneur.gauvain.wowsplash.data.repository.UserRepository
import com.seigneur.gauvain.wowsplash.ui.base.paging.viewModel.BasePagingListViewModel
import com.seigneur.gauvain.wowsplash.utils.PHOTO_LIST_HOME
import com.seigneur.gauvain.wowsplash.utils.event.Event
import io.reactivex.rxkotlin.subscribeBy
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope

class AddToCollectionsListViewModel(
    private val collectionsRepository: CollectionsRepository
) : BasePagingListViewModel<UserCollectionsDataSource, Long, PhotoCollection>() {

    companion object {
        private val pageSize = 50
    }

    /**************************************************************************
    * Paged List
    **************************************************************************/
    var list: LiveData<PagedList<PhotoCollection>>? = null

    private var userName = ""
    private var config: PagedList.Config? = null
    private val userCollectionsDataSourceFactory: UserCollectionsDataSourceFactory by lazy {
        UserCollectionsDataSourceFactory(
            userName,
            mDisposables,
            collectionsRepository
        )
    }

    override val dataSourceFactory: BaseDataSourceFactory<UserCollectionsDataSource, Long, PhotoCollection>
        get() = userCollectionsDataSourceFactory

    /**
     * Start request data list
     */
    fun init() {
        userName ="gauvains"
        initList()
    }

    private fun initList() {
        if (config == null) {
            config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize)
                .setEnablePlaceholders(false)
                .build()
            config?.let {
                list = LivePagedListBuilder(userCollectionsDataSourceFactory, it).build()
            }
        }
    }
}
