package com.seigneur.gauvain.wowsplash.ui.base.pagingList.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel
import com.seigneur.gauvain.wowsplash.data.model.network.NetworkState
import com.seigneur.gauvain.wowsplash.ui.base.pagingList.dataSource.BaseListDataSourceFactory
import timber.log.Timber

abstract class BasePagedListViewModel<Key, Value>(val pageSize:Int) : BaseViewModel() {

    var list: LiveData<PagedList<Value>>? =null
    private var config: PagedList.Config? = null

    abstract val dataSourceFactory: BaseListDataSourceFactory<Key, Value>

    val networkState: LiveData<NetworkState>
        get() =  Transformations.switchMap(dataSourceFactory.factoryListLiveData)
        { it.networkState }

    val refreshState: LiveData<NetworkState>
        get() = Transformations.switchMap(dataSourceFactory.factoryListLiveData) {
            Timber.d("refresh called ")
            it.initialLoad
        }

    fun init() {
        if (config == null && list == null) {
            config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize)
                .setEnablePlaceholders(false)
                .build()
            list = LivePagedListBuilder(dataSourceFactory, config!!).build()
        }
    }

    fun retry() {
        dataSourceFactory.factoryListLiveData.value?.retry()
    }

    fun refresh() {
        dataSourceFactory.factoryListLiveData.value?.invalidate()
    }


}
