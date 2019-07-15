package com.seigneur.gauvain.wowsplash.ui.base.list

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

abstract  class BaseListDataSourceFactory<Key, Value> : DataSource.Factory<Key, Value>() {

    abstract fun getDataSource():BaseListDataSource<Key, Value>

    abstract fun getPagedListLiveData():MutableLiveData<BaseListDataSource<Key, Value>>

    override fun create(): DataSource<Key, Value> {
        getPagedListLiveData().postValue(getDataSource())
        return getDataSource()
    }

}
