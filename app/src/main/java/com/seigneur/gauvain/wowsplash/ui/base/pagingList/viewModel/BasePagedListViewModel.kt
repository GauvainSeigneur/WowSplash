package com.seigneur.gauvain.wowsplash.ui.base.pagingList.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel
import com.seigneur.gauvain.wowsplash.data.model.network.NetworkState
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.base.BaseListDataSourceFactory
import timber.log.Timber

abstract class BasePagedListViewModel<T, Key, Value>(val pageSize:Int) : BaseViewModel() {

    var list: LiveData<PagedList<Value>>? =null
    var config: PagedList.Config? = null

    abstract val dataSourceFactory: BaseListDataSourceFactory<T, Key, Value>

    val networkState: LiveData<NetworkState>
        get() =  Transformations.switchMap(dataSourceFactory.factoryListLiveData)
        { it.networkState }

    val refreshState: LiveData<NetworkState>
        get() = Transformations.switchMap(dataSourceFactory.factoryListLiveData) {
            Timber.d("refresh called ")
            it.initialLoad
        }

    abstract fun init()

    fun retry() {
        dataSourceFactory.factoryListLiveData.value?.retry()
    }

    fun refresh() {
        dataSourceFactory.factoryListLiveData.value?.invalidate()
    }


}
