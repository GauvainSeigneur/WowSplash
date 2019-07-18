package com.seigneur.gauvain.wowsplash.ui.base.pagingList.dataSource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

abstract  class BaseListDataSourceFactory<Key, Value> : DataSource.Factory<Key, Value>() {

    val factoryListLiveData=MutableLiveData<BaseListDataSource<Key, Value>>()

    abstract val dataSource : BaseListDataSource<Key, Value>

    override fun create(): DataSource<Key, Value> {
        factoryListLiveData.postValue(dataSource)
        return dataSource
    }

}
