package com.seigneur.gauvain.wowsplash.ui.base.paging.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.base.BaseDataSourceFactory
import com.seigneur.gauvain.wowsplash.data.model.network.NetworkState
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel

abstract class BaseUserCollectionPagingListViewModel<DataSource, Key, Value> : BaseViewModel() {

    var list: LiveData<PagedList<Value>>? = null

    var factory: BaseDataSourceFactory<DataSource, Key, Value>? = null

    var networkState: LiveData<NetworkState>? = null

    var initialNetworkState: LiveData<NetworkState>? = null

    abstract fun createDataSourceFactory(name: String): BaseDataSourceFactory<DataSource, Key, Value>

    abstract fun initDataSource()

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

}
