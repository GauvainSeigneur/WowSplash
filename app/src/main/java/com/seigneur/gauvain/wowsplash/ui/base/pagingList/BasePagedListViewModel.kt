package com.seigneur.gauvain.wowsplash.ui.base.pagingList

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel
import com.seigneur.gauvain.wowsplash.data.model.network.NetworkState
import timber.log.Timber

abstract class BasePagedListViewModel<Key, Value>(val pageSize:Int) : BaseViewModel() {

    abstract var list: LiveData<PagedList<Value>>?
    private var config: PagedList.Config? = null

    abstract val dataSourceFactory: DataSource.Factory<Key, Value>

    abstract val refreshState: LiveData<NetworkState>

    abstract val networkState: LiveData<NetworkState>

    abstract fun retry()

    abstract fun refresh()


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

}
