package com.seigneur.gauvain.wowsplash.business.paginationInteractor.base

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

/**
 * Base class which extends DataSource.Factory
 * Use this class as base for paging list data factory
 * It allows to use dataSourceLiveData as unique entry point.
 * Thanks to this class, we can me make a basePagingViewModel and make paging list in several view with less code
 */
abstract class BaseDataSourceFactory<T, Key, Value> : DataSource.Factory<Key, Value>() {

    val dataSourceLiveData = MutableLiveData<BaseListDataSource<T, Key, Value>>()

    override fun create(): BaseListDataSource<T, Key, Value> {
        val dataSource = createDataSource()
        dataSourceLiveData.postValue(dataSource)
        return dataSource
    }

    abstract fun createDataSource() : BaseListDataSource<T,Key, Value>

}

