package com.seigneur.gauvain.wowsplash.ui.base.paging.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.base.BaseDataSourceFactory
import com.seigneur.gauvain.wowsplash.data.model.network.NetworkState
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel
import timber.log.Timber

abstract class BasePagingListViewModel<DataSource, key, Value> : BaseViewModel() {

    /**
     * dataSourceFactory must be initialized before subscribe networkState and refreshState
     * livedata
     */
    abstract val dataSourceFactory : BaseDataSourceFactory<DataSource, key, Value>

    val networkState: LiveData<NetworkState>
        get() = Transformations.switchMap(dataSourceFactory.dataSourceLiveData)
        { it.networkState }

    val refreshState: LiveData<NetworkState>
        get() = Transformations.switchMap(dataSourceFactory.dataSourceLiveData) {
            it.initialLoad
        }

    fun retry() {
        if (dataSourceFactory.dataSourceLiveData.value != null)
            dataSourceFactory.dataSourceLiveData.value!!.retry()
    }

    fun refresh() {
        if (dataSourceFactory.dataSourceLiveData.value != null)
            dataSourceFactory.dataSourceLiveData.value!!.invalidate()
    }

}
