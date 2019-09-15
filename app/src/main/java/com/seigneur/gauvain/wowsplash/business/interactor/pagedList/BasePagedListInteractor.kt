package com.seigneur.gauvain.wowsplash.business.interactor.pagedList

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.base.BaseDataSourceFactory
import com.seigneur.gauvain.wowsplash.data.model.network.NetworkState
import timber.log.Timber

/**
 * dataSourceFactory must be initialized before subscribe networkState and refreshState
 * livedata
 */
abstract class BasePagedListInteractor<DataSource, key, Value> {

    abstract val dataSourceFactory : BaseDataSourceFactory<DataSource, key, Value>

    val networkState: LiveData<NetworkState>
        get() = Transformations.switchMap(dataSourceFactory.dataSourceLiveData)
        { it.networkState }

    val refreshState: LiveData<NetworkState>
        get() = Transformations.switchMap(dataSourceFactory.dataSourceLiveData) {
            Timber.d("refresh called ")
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

