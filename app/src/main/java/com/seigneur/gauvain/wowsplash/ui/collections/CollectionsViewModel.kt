package com.seigneur.gauvain.wowsplash.ui.collections

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.seigneur.gauvain.wowsplash.data.model.PhotoCollection
import com.seigneur.gauvain.wowsplash.data.repository.CollectionsRepository
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel
import com.seigneur.gauvain.wowsplash.ui.base.list.NetworkState
import com.seigneur.gauvain.wowsplash.ui.home.list.data.datasource.CollectionsDataSourceFactory
import timber.log.Timber

class CollectionsViewModel(private val mCollectionsRepository: CollectionsRepository) : BaseViewModel() {

    var shotList: LiveData<PagedList<PhotoCollection>>? = null
    private var config: PagedList.Config? = null

    private val collectionsDataSourceFactory: CollectionsDataSourceFactory by lazy {
        CollectionsDataSourceFactory(mDisposables, mCollectionsRepository)
    }

    val refreshState: LiveData<NetworkState>
        get() = Transformations.switchMap(collectionsDataSourceFactory.collectionLiveData) {
            Timber.d("refresh called ")
            it.initialLoad
        }

    val networkState: LiveData<NetworkState>
        get() =  Transformations.switchMap(collectionsDataSourceFactory.collectionLiveData)
        { it.networkState }




    fun init() {
        if (config == null && shotList == null) {
            config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize)
                .setEnablePlaceholders(false)
                .build()
            shotList = LivePagedListBuilder(collectionsDataSourceFactory, config!!).build()
        }

    }

    companion object {
        private val pageSize = 15
    }

}
