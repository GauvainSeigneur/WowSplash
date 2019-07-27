package com.seigneur.gauvain.wowsplash.ui.search

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.search.photo.SearchPhotoDataSourceFactory
import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.data.repository.SearchRepository
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel

class SearchViewModel(private val searchRepository: SearchRepository) : BaseViewModel() {

    var list: LiveData<PagedList<Photo>>? = null
    fun search(query: String) {


        val f = SearchPhotoDataSourceFactory(
            mDisposables,
            searchRepository,
            query
        )

        val conf = PagedList.Config.Builder()
            .setPageSize(15)
            .setInitialLoadSizeHint(15)
            .setEnablePlaceholders(false)
            .build()
        list = LivePagedListBuilder(f, conf).build()
    }
}
