package com.seigneur.gauvain.wowsplash.ui.addToCollections

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.base.BaseDataSourceFactory
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.userCollections.UserCollectionsDataSource
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.userCollections.UserCollectionsDataSourceFactory
import com.seigneur.gauvain.wowsplash.data.model.network.NetworkState
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoCollection
import com.seigneur.gauvain.wowsplash.data.repository.CollectionsRepository
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel

class AddToCollectionsListViewModel(
    private val collectionsRepository: CollectionsRepository
) : BaseViewModel() {

    companion object {
        private const val pageSize = 50
    }

    var list: LiveData<PagedList<PhotoCollection>>? = null
    var networkState: LiveData<NetworkState>? = null
    var initialNetworkState: LiveData<NetworkState>? = null

    private var factory: BaseDataSourceFactory<UserCollectionsDataSource, Long, PhotoCollection>? =
        null

    fun initList(name: String) {
        factory = createDataSourceFactory(name)
        initDataSource()
        networkState = Transformations.switchMap(factory!!.dataSourceLiveData)
        { it.networkState }

        initialNetworkState = Transformations.switchMap(factory!!.dataSourceLiveData)
        { it.initialLoad }
    }

    fun retry() {
        if (factory?.dataSourceLiveData?.value != null)
            factory?.dataSourceLiveData?.value!!.retry()
    }

    private fun createDataSourceFactory(name: String):
            BaseDataSourceFactory<UserCollectionsDataSource, Long, PhotoCollection> {
        return UserCollectionsDataSourceFactory(
            name,
            mDisposables,
            collectionsRepository
        )
    }

    private fun initDataSource() {
        val conf = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(false)
            .build()
        list = LivePagedListBuilder(
            factory as BaseDataSourceFactory<UserCollectionsDataSource, Long, PhotoCollection>,
            conf
        ).build()
    }
}
