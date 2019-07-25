package com.seigneur.gauvain.wowsplash.business.paginationInteractor.base

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

abstract  class BaseListDataSourceFactory<T, Key, Value> : DataSource.Factory< Key, Value>() {

    val factoryListLiveData=MutableLiveData<BaseListDataSource<T, Key, Value>>()

    abstract val dataSource : BaseListDataSource<T, Key, Value>

    override fun create(): DataSource<Key, Value> {
        factoryListLiveData.postValue(dataSource)
        return dataSource
    }

}
